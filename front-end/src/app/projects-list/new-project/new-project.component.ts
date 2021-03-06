import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {ProjectService} from '../project.service';
import {Subscription} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Project} from '../project.model';
import {Group} from '../group.model';
import {ProjectNumberValidator} from '../../shared/validator/ProjectNumber.validator';
import {MembersValidator} from '../../shared/validator/Members.validator';
import {TranslateService} from '@ngx-translate/core';

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
              private checkProjectNumber: ProjectNumberValidator,
              private checkMembers: MembersValidator,
              private translate: TranslateService
  ) {}
  ngOnInit(): void {
    this.initGroups();
    this.paramsSubscription = this.route.params
      .subscribe(
        (params: Params) => {
          this.id = params.id;
          this.initForm();
        }
      );
    this.queryParamsSubscription = this.route.queryParams
      .subscribe(
        (queryParams: Params) => {
          this.editMode = queryParams.allowEdit === '1';
        }
      );
    this.initForm();
    window.addEventListener('beforeunload',  (e) => {
      e.returnValue = 'Your data will be lost!';
      return e;
    });
  }

  onCancel(): void {
    if (this.projectForm.touched) {
      const message = this.translate.instant('alert-cancel');
      if (confirm(message)) {
        this.router.navigate(['projects/projects-list']);
      }
    } else {
      this.router.navigate(['projects/projects-list']);
    }
    // console.log(this.projectService.getProjects());
  }

  // checkInputRequired(): boolean {
  //   const form: FormGroup  = this.projectForm;
  //   form.get('number').valid
  // }

  onSubmit(): void {
    this.projectForm.markAllAsTouched();
    console.log(this.projectForm);
    if (this.projectForm.invalid) {
      this.isAlert = true;
      console.log('A');
    } else {
      console.log('B');
      this.isAlert = false;
      if (this.editMode) {
        this.updateProject();
      } else {
        this.addNewProject();
      }
    }
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
    this.projectService.updateProject(this.project)
      .subscribe(
        res => {
          this.projectForm.reset();
          this.router.navigate(['projects/projects-list']);
        }, rej => {
          if (rej.errorName === 'DateException') {
            this.error = rej.message;
          } else if (rej.errorName === 'ConcurrentException') {
            // this.router.navigate(['error-page']);
            alert(rej.message);
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
    const group: Group = {id: Number(this.projectForm.get('group').value)};
    const project: Project = {
      id: this.project.id,
      projectNumber: this.projectForm.get('number').value,
      name: this.projectForm.get('name').value,
      customer: this.projectForm.get('customer').value,
      group: group,
      members: this.projectForm.get('members').value,
      status: this.projectForm.get('status').value,
      startDate: this.projectForm.get('startDate').value,
      endDate: this.projectForm.get('endDate').value,
      version: this.project.version
    };
    return project;
  }

  private initForm(): void {
    if (this.editMode) {
      this.projectService.getProject(this.id).subscribe(
        res => {
          this.project = res;
          this.projectForm.patchValue({
            number: this.project.projectNumber,
            name: this.project.name,
            customer: this.project.customer,
            group: this.project.group.id,
            members: this.project.members,
            status: this.project.status,
            startDate: this.project.startDate,
            endDate: this.project.endDate,
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
      members: new FormControl(this.project.members, {
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
