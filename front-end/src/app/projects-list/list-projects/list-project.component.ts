import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {Project} from '../project.model';
import {ProjectService} from '../project.service';
import {range, Subscription} from 'rxjs';
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
  listProjectsForm: FormGroup;
  criteria = '';
  status = '';
  statuses = ['', 'New', 'Inprogress', 'Finished', 'Planned'];
  deleteList = [];
  sizeProject = [];
  indexPage = 0;

  constructor(private projectService: ProjectService,
              private http: HttpClient,
              private translate: TranslateService) {
  }

  ngOnInit(): void {
    this.initForm();
    this.setCriteriaAndStatus();
    this.pagination(this.indexPage);
    this.getSizeProjects();
  }

  setCriteriaAndStatus(): void {
    const param = this.projectService.getCriteriaAndStatus();
    this.criteria = param.criteria;
    this.status = param.status;
  }

  getSizeProjects(): void {
    this.projectService.getSizeProjects().subscribe(
      value => {
        this.sizeProject = Array( Math.round(value / 5 + 0.49)).fill(1).map((x, i) => i + 1);
      });
  }

  onReset(): void {
    this.criteria = '';
    this.status = '';
    this.projectService.setCriteriaAndStatus(this.criteria, this.status);
    this.deleteList = [];
    this.pagination(this.indexPage);
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
          this.pagination(this.indexPage);
          this.getSizeProjects();
        }, reject => {
        }
      );
    }

  }

  deleteListProjects(): void {
    const message = this.translate.instant('alert-delete-project-list');
    if (confirm(message)) {
      this.projectService.deleteProjectList(this.deleteList).subscribe(
        response => {
          this.pagination(this.indexPage);
          this.getSizeProjects();
          this.deleteList = [];
        }, rej => {
          if (rej.error.errorName === 'DeleteProjectException') {
            alert(rej.error.message);
          }
        }
      );
    }
  }

  onSubmit(): void {
    if (this.status == null) {
      this.status = '';
    }
    this.projectService.setCriteriaAndStatus(this.criteria, this.status);
    this.pagination(this.indexPage);
  }

  pagination(index): void {
    this.indexPage = index;
    // this.deleteList = [];
    this.projectService.searchProjects(this.criteria, this.status, index)
      .subscribe(
        value => {
          this.projects = value;
          // this.sizeProject = Array( Math.round(value.length / 5 + 0.5)).fill(1).map((x, i) => i + 1);
        }
      );
  }

  ngOnDestroy(): void {
    // this.subscription.unsubscribe();
  }
}
