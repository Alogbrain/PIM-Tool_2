import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {ProjectService} from '../../projects-list/project.service';
import {AbstractControl, AsyncValidatorFn} from '@angular/forms';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable()
export class ProjectNumberValidator{
  constructor(private http: HttpClient, private projectService: ProjectService) {}

  projectNumberValidator(editMode: boolean): AsyncValidatorFn{
    return (control: AbstractControl): Promise<any> | Observable<any> => {
      if (!editMode){
        return this.http.get('/api/projects/projectNumber/' + control.value).pipe(
          catchError (
            (res: HttpErrorResponse) => {
              return Promise.resolve({'numberProjectError': res.error});
            }
          )
        );
      }
      return Promise.resolve(null);
    };
  }
}
