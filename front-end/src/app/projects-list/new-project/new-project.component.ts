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

@Component({
  selector: 'app-new-project',
  templateUrl: './new-project.component.html',
  styleUrls: ['./new-project.component.css'],
  providers: [ProjectNumberValidator]
})
export class NewProjectComponent implements OnInit, OnDestroy {

  editMode = false;
  projectForm: FormGroup;
  isAlert = false;
  paramsSubscription: Subscription;
  queryParamsSubscription: Subscription;
  id: number;
  project: Project = {
    projectNumber: null, endDate: '', startDate: '', status: 'New', members: '', group: {id: null},
    customer: '', name: ''
  };
  error: string;
  groups: Group[];

  statuses = ['New', 'Inprogress', 'Finished', 'Planned'];

  constructor(private route: ActivatedRoute,
              private projectService: ProjectService,
              private router: Router,
              private http: HttpClient,
              private  checkProjectNumber: ProjectNumberValidator
  ) {
  }

  ngOnInit(): void {
    this.initGroups();
    this.paramsSubscription = this.route.params
      .subscribe(
        (params: Params) => {
          this.id = params['id'];

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
    if (this.editMode) {
      // console.log(this.projectForm);
      console.log(this.project);
      this.projectForm.markAllAsTouched();
      this.updateProject();
    } else {
      if (!this.projectForm.valid) {
        this.isAlert = true;
      } else {
        this.isAlert = false;
        // console.log(this.project);
        this.projectForm.markAllAsTouched();
        this.addNewProject();
      }
    }
  }

  addNewProject(): void {
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
    this.projectService.updateProject(this.id, this.project)
      .subscribe(
        res => {
          this.projectForm.reset();
          this.router.navigate(['projects/projects-list']);
        }, rej => {
          this.error = rej;
        }
      );
  }

  onCancelAlert(): void {
    this.isAlert = !this.isAlert;
  }

  private initGroups(): void {
    this.projectService.getGroups()
      .subscribe(resGroups => {
        this.groups = resGroups;
      });
  }

  private initForm(): void {
    if (this.editMode) {
      this.projectService.getProject(this.id).subscribe(
        res => {
          this.project = res;
          console.log(res);
        });
    }
    this.projectForm = new FormGroup({
      number: new FormControl(null, {validators: [Validators.required],
       asyncValidators: [this.checkProjectNumber.projectNumberValidator(this.editMode)], updateOn: 'blur'}),
      name: new FormControl(null, Validators.required),
      customer: new FormControl(null, Validators.required),
      group: new FormControl(null, Validators.required),
      members: new FormControl(null),
      status: new FormControl(null, Validators.required),
      startDate: new FormControl(null, Validators.required),
      endDate: new FormControl(null)
    });
  }

  ngOnDestroy(): void {
    this.paramsSubscription.unsubscribe();
    this.queryParamsSubscription.unsubscribe();
  }
}
