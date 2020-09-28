import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {ProjectService} from '../../projects-list/project.service';
import {AbstractControl, AsyncValidatorFn} from '@angular/forms';
import {Observable} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';

@Injectable()
export class MembersValidator {
    constructor(private http: HttpClient, private projectService: ProjectService) {
    }

    membersValidator(): AsyncValidatorFn {
        return (control: AbstractControl): Promise<any> | Observable<any> => {
            const params = new HttpParams().append('members', control.value);
            console.log(control.value);
            return this.http.post('/api/projects/members', params).pipe(
                catchError(
                    (res: HttpErrorResponse) => {
                        return Promise.resolve({'membersError': res.error});
                    }
                )
            );
            return Promise.resolve(null);
        };
    }
}