import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthorizationInfo } from '../interfaces/authorization-info';

@Injectable({
    providedIn: 'root'
})
export class AuthorizationInfoService {

    public constructor(
        private http: HttpClient
    ) { }

    public getAuthorizationInfo(): Observable<any> {
        return this.http.get<AuthorizationInfo>(`${environment.host}/oauth2/authorization-info`, { withCredentials: true });
    }

}
