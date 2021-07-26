import { Component, OnInit } from '@angular/core';
import { AuthorizationInfo } from 'src/app/interfaces/authorization-info';
import { AuthorizationInfoService } from 'src/app/services/authorization-info.service';
import { environment } from 'src/environments/environment';

@Component({
    selector: 'app-authorize',
    templateUrl: './authorize.component.html',
    styleUrls: ['./authorize.component.scss']
})
export class AuthorizeComponent implements OnInit {

    // Información sobre el cliente
    public authorizationInfo: AuthorizationInfo | undefined;
    public authorizeUrl: string = `${environment.host}/oauth2/authorize`;

    public constructor(
        private authorizationInfoService: AuthorizationInfoService
    ) { }

    public ngOnInit(): void { }

    public ngAfterViewInit(): void {
        // Get authorization info
        this.authorizationInfoService.getAuthorizationInfo().subscribe(
            authorizationInfo => {

                // Copy authorizationInfo
                this.authorizationInfo = authorizationInfo;
            }
        );
    }

}
