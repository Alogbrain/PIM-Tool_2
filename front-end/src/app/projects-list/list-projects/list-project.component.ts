import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {Project} from '../project.model';
import {ProjectService} from '../project.service';
import {Subscription} from 'rxjs';
import {map} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {FormControl, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-detail-project',
  templateUrl: './list-project.component.html',
  styleUrls: ['./list-project.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ListProjectComponent implements OnInit, OnDestroy {

  projects: Project[] = [];
  subscription: Subscription;
  listProjectsForm: FormGroup;
  criteria = '';
  status = '';
  statuses = ['New', 'Inprogress', 'Finished', 'Planned'];

  constructor(private projectService: ProjectService,
              private http: HttpClient) {
  }

  ngOnInit(): void {
    this.subscription = this.projectService.projectsChanged.subscribe(
      (projects: Project[]) => {
        this.projects = projects;
      }
    );
    this.initForm();
    this.fetchData();
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
  }

  initForm(): void {
    this.listProjectsForm = new FormGroup({
      search: new FormControl(null),
      status: new FormControl(null)
    });
  }

  deleteProject(id: number): void {
    console.log(id);
    this.projectService.deleteProject(id).subscribe(
      res => {
        console.log('SUCCESS');
      }, rej => {
        console.log('FAIL');
      }
    );
  }

  onSubmit(): void {
    if (this.status == null) {
      this.status = '';
    }
    this.projectService.searchProjects(this.criteria, this.status)
      .subscribe(
        value => {
          this.projects = value;
        }
      );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
