import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {ProjectService} from '../project.service';
import {Subscription} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {formatDate} from '@angular/common';
import {Project} from '../project.model';
import {Group} from '../group.model';
import {ProjectNumberValidator} from '../../shared/validator/ProjectNumber.validator';
import {MembersValidator} from '../../shared/validator/Members.validator';

@Component({
  selector: 'app-new-project',
  templateUrl: './new-project.component.html',
  styleUrls: ['./new-project.component.css'],
  providers: [ProjectNumberValidator, MembersValidator]
})
export class NewProjectComponent implements OnInit, OnDestroy {

  editMode = false;
  projectForm: FormGroup;
  isAlert = false;
  paramsSubscription: Subscription;
  queryParamsSubscription: Subscription;
  id: number;
  project: Project = {};
  error: string;
  groups: Group[];

  statuses = ['New', 'Inprogress', 'Finished', 'Planned'];

  constructor(private route: ActivatedRoute,
              private projectService: ProjectService,
              private router: Router,
              private http: HttpClient,
              private  checkProjectNumber: ProjectNumberValidator,
              private checkMembers: MembersValidator
  ) {
  }

  ngOnInit(): void {
    this.initGroups();
    this.paramsSubscription = this.route.params
      .subscribe(
        (params: Params) => {
          this.id = params['id'];
          this.initForm();
        }
      );
    this.queryParamsSubscription = this.route.queryParams
      .subscribe(
        (queryParams: Params) => {
          this.editMode = queryParams['allowEdit'] === '1' ? true : false;
        }
      );

    this.initForm();
  }

  onCancel(): void {
    if (this.editMode) {
      this.router.navigate(['projects/projects-list']);
    } else {
      this.router.navigate(['projects/projects-list']);
    }
    // console.log(this.projectService.getProjects());
  }

  onSubmit(): void {
    this.projectForm.markAllAsTouched();
    if (this.editMode) {
      // console.log(this.project);
      if (!this.projectForm.valid) {
        this.isAlert = true;
      }else{
        this.isAlert = false;
        this.updateProject();
      }
    } else {
      if (!this.projectForm.valid) {
        this.isAlert = true;
      } else {
        this.isAlert = false;

        this.projectForm.markAllAsTouched();
        this.addNewProject();
      }
    }
    // if (this.projectForm.touched && this.projectForm.valid){
    //   console.log('ssssaaaaa');
    //   if (this.editMode){
    //     console.log("SSS");
    //   }else{
    //     console.log("crate");
    //   }
    // }
  }

  addNewProject(): void {
    this.project = this.getProject();
    console.log(this.project);
    this.projectService.addProject(this.project)
      .subscribe(
        res => {
          this.projectForm.reset();
          this.router.navigate(['projects/projects-list']);
        }, rej => {
          this.error = rej;
        }
      );
  }

  updateProject(): void {
    this.project = this.getProject();
    console.log(this.project);
    this.projectService.updateProject(this.id, this.project)
      .subscribe(
        res => {
          this.projectForm.reset();
          this.router.navigate(['projects/projects-list']);
        }, rej => {
          if (rej.errorName === 'DateException') {
            this.error = rej.message;
          }else if (rej.errorName === 'ConcurrentException'){
            alert(rej.message);
          }
          if (rej.status === 500) {
            this.router.navigate(['error-page']);
          }
        }
      );
  }

  onCancelAlert(): void {
    this.isAlert = !this.isAlert;
    this.projectForm.markAsUntouched();
  }

  private initGroups(): void {
    this.projectService.getGroups()
      .subscribe(resGroups => {
        this.groups = resGroups;
      });
  }

  private getProject(): Project {
    let crrgroup: Group = {id: Number(this.projectForm.get('group').value)};
    let crrProject: Project = {
      projectNumber: this.projectForm.get('number').value,
      name: this.projectForm.get('name').value,
      customer: this.projectForm.get('customer').value,
      group: crrgroup,
      members: this.projectForm.get('members').value,
      status: this.projectForm.get('status').value,
      startDate: this.projectForm.get('startDate').value,
      endDate: this.projectForm.get('endDate').value,
      version: this.project.version
    };
    return crrProject;
  }

  private initForm(): void {
    if (this.editMode) {
      this.projectService.getProject(this.id).subscribe(
        res => {
          this.project = res;
          this.projectForm.patchValue({
            'number': this.project.projectNumber,
            'name': this.project.name,
            'customer': this.project.customer,
            'group': this.project.group.id,
            'members': this.project.members,
            'status': this.project.status,
            'startDate': this.project.startDate,
            'endDate': this.project.endDate,
          });
        });
    }
    this.projectForm = new FormGroup({
      number: new FormControl(null, {
        validators: [Validators.required, Validators.min(0), Validators.max(9999)],
        asyncValidators: [this.checkProjectNumber.projectNumberValidator(this.editMode)], updateOn: 'blur'
      }),
      name: new FormControl(null, [Validators.required, Validators.maxLength(50)]),
      customer: new FormControl(null, [Validators.required, Validators.maxLength(50)]),
      group: new FormControl(null, Validators.required),
      members: new FormControl( this.project.members, {
        asyncValidators: [this.checkMembers.membersValidator()], updateOn: 'blur'
      }),
      status: new FormControl('New', Validators.required),
      startDate: new FormControl(null, Validators.required),
      endDate: new FormControl(null)
    });
  }

  ngOnDestroy(): void {
    this.paramsSubscription.unsubscribe();
    this.queryParamsSubscription.unsubscribe();
  }
}
