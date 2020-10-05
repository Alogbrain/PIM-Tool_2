import {Injectable} from '@angular/core';

import {Observable, Subject, throwError} from 'rxjs';
import {Project} from './project.model';
import {catchError, map} from 'rxjs/operators';
import {HttpClient, HttpErrorResponse, HttpParams, HttpResponse} from '@angular/common/http';
import {Group} from './group.model';

@Injectable()
export class ProjectService {
  projectsChanged = new Subject<Project[]>();
  criteria = '';
  status = '';
  private projects: Project[] = [];

  constructor(private http: HttpClient) {
  }

  setCriteriaAndStatus(criteria, status): void {
    this.criteria = criteria;
    this.status = status;
  }

  getCriteriaAndStatus(): any {
    return {criteria: this.criteria, status: this.status};
  }

  getSizeProjects(): Observable<number> {
    const url = 'api/projects/get-size-projects';
    return this.http.get<number>(url);
  }

  searchProjects(criteria: string, status: string, index: string): Observable<Project[]> {
    const url = 'api/projects/search';
    let params = new HttpParams();
    params = params.append('criteria', criteria);
    params = params.append('status', status);
    params = params.append('index', index);
    return this.http.post<Project[]>(url, params);
  }

  getProject(index: number): Observable<Project> {
    const url = 'api/projects/queryById/' + index;
    return this.http.get<Project>(url);
  }

  getGroups(): Observable<Group[]> {
    const url = 'api/projects/groups';
    return this.http.get<Group[]>(url);
  }

  addProject(project: Project): Observable<any> {
    const url = 'api/projects/create-project';
    return this.http.post(url, project).pipe(catchError(this.handleError));
  }

  handleError(err): any {
    if (err instanceof HttpErrorResponse) {
      return throwError(err.error);
    } else {
      return throwError(err);
    }
  }

  deleteProject(index): Observable<any> {
    const url = 'api/projects/delete-project';
    const params = new HttpParams().append('id', index);
    return this.http.post(url, params);
  }

  deleteProjectList(list): Observable<any> {
    const url = 'api/projects/delete-project-list';
    return this.http.post(url, list);
  }

  updateProject(newProject: Project): Observable<any> {
    const url = 'api/projects/update-project';
    return this.http.post(url, newProject).pipe(catchError(this.handleError));
  }
}
