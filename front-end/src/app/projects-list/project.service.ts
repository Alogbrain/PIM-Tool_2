import {Injectable} from '@angular/core';

import {Observable, Subject} from 'rxjs';
import {Project} from './project.model';
import {map} from 'rxjs/operators';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Group} from './group.model';

@Injectable()
export class ProjectService {
  projectsChanged = new Subject<Project[]>();

  // private projects: Project[] = [
  //   {
  //     projectNumber: 1, name: 'John', customer: 'Anna', group: {id: 1}, members: ['Athur', 'Ben'], status: 'New',
  //     startDate: '', endDate: ''
  //   },
  //   {
  //     projectNumber: 2, name: 'Green', customer: 'Black', group: {id: 2}, members: ['Blue', 'Yellow'], status: 'New',
  //     startDate: '', endDate: ''
  //   },
  // ];

  private projects: Project[] = [];

  constructor(private http: HttpClient) {
  }

  getProjects(): Observable<Project[]> {
    const url = 'api/projects/query';
    // this.projectsChanged.next(this.projects);
    return this.http.get<Project[]>(url)
      .pipe(map(responseData => {
          const projectArray: Project[] = [];
          for (const key of responseData) {
            projectArray.push(key);
          }
          return projectArray;
        })
      );
  }
  searchProjects(criteria: string, status: string): Observable<Project[]> {
    const url = 'api/projects/search';
    let params = new HttpParams();
    params = params.append('criteria', criteria);
    params = params.append('status', status);
    return this.http.post<Project[]>(url, params);
  }
  getProject(index: number): Observable<Project> {
    // return this.projects[index];
    const url = 'api/projects/queryById/' + index;
    return this.http.get<Project>(url);
  }

  getGroups(): Observable<Group[]> {
    const url = 'api/projects/groups';
    return this.http.get<Group[]>(url);
  }

  addProject(project: Project): Observable<any> {
    // this.projects.push(project);
    // this.projectsChanged.next(this.projects.slice());
    const url = 'api/projects/create-project';
    return  this.http.post(url, project);
  }
  deleteProject(index): Observable<any>{
    const url = 'api/projects/delete-project';
    let params = new HttpParams().append('id', index );
    return  this.http.post(url, params);
  }
  updateProject(index: number, newProject: Project): Observable<any> {
    // this.projects[index] = newProject;
    // this.projectsChanged.next(this.projects.slice());
    const url = 'api/projects/update-project/' + index;
    return  this.http.post(url,  newProject);
  }
}
