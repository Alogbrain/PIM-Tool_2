import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {Project} from '../project.model';
import {ProjectService} from '../project.service';
import {Subscription} from 'rxjs';
import {map} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {FormControl, FormGroup} from '@angular/forms';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-detail-project',
  templateUrl: './list-project.component.html',
  styleUrls: ['./list-project.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ListProjectComponent implements OnInit, OnDestroy {

  projects: Project[] = [];
  // subscription: Subscription;
  listProjectsForm: FormGroup;
  criteria = '';
  status = '';
  statuses = ['', 'New', 'Inprogress', 'Finished', 'Planned'];
  deleteList = [];

  constructor(private projectService: ProjectService,
              private http: HttpClient,
              private translate: TranslateService) {
  }

  ngOnInit(): void {
    // this.subscription = this.projectService.projectsChanged.subscribe(
    //   (projects: Project[]) => {
    //     this.projects = projects;
    //   }
    // );
    this.initForm();
    // this.fetchData();
    this.setCriteriaAndStatus();
    this.projectService.searchProjects(this.criteria, this.status)
      .subscribe(
        value => {
          this.projects = value;
        }
      );
  }

  setCriteriaAndStatus(): void {
    const param = this.projectService.getCriteriaAndStatus();
    this.criteria = param.criteria;
    this.status = param.status;
  }

  fetchData(): void {
    this.projectService.getProjects().subscribe(
      value => {
        this.projects = value;
      });
  }

  onReset(): void {
    this.criteria = '';
    this.status = '';
    this.fetchData();
  }

  initForm(): void {
    this.listProjectsForm = new FormGroup({
      search: new FormControl(null),
      status: new FormControl(null)
    });
  }

  onChangeItemDelete(e, project): void {
    if (e.target.checked) {
      this.deleteList.push({projectNumber: project.projectNumber, status: project.status});
    } else {
      let index = this.deleteList.findIndex(i => i.projectNumber === project.projectNumber);
      this.deleteList.splice(index, 1);
    }
  }

  deleteProject(id: number): void {
    const message = this.translate.instant('alert-delete-project');
    if (confirm(message + id + '?')) {
      this.projectService.deleteProject(id).subscribe(
        response => {
          this.fetchData();
        }, response => {
          console.log('FAIL');
        }
      );
    }

  }

  deleteListProjects(): void {
    const message = this.translate.instant('alert-delete-project-list')
    if (confirm(message)) {
      this.projectService.deleteProjectList(this.deleteList).subscribe(
        response => {
          this.fetchData();
          this.deleteList = [];
        }, rej => {
          if (rej.error.errorName === 'DeleteProjectException') {
            alert(rej.error.message);
          }
        }
      );
    }
    // this.deleteList = [];
  }

  onSubmit(): void {
    if (this.status == null) {
      this.status = '';
    }
    this.projectService.setCriteriaAndStatus(this.criteria, this.status);
    this.projectService.searchProjects(this.criteria, this.status)
      .subscribe(
        value => {
          this.projects = value;
        }
      );
  }

  ngOnDestroy(): void {
    // this.subscription.unsubscribe();
  }
}
