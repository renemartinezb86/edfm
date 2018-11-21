import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApplicationVersion } from 'app/shared/model/application-version.model';
import { ApplicationVersionService } from './application-version.service';
import { ApplicationVersionComponent } from './application-version.component';
import { ApplicationVersionDetailComponent } from './application-version-detail.component';
import { ApplicationVersionUpdateComponent } from './application-version-update.component';
import { ApplicationVersionDeletePopupComponent } from './application-version-delete-dialog.component';
import { IApplicationVersion } from 'app/shared/model/application-version.model';

@Injectable({ providedIn: 'root' })
export class ApplicationVersionResolve implements Resolve<IApplicationVersion> {
    constructor(private service: ApplicationVersionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ApplicationVersion> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ApplicationVersion>) => response.ok),
                map((applicationVersion: HttpResponse<ApplicationVersion>) => applicationVersion.body)
            );
        }
        return of(new ApplicationVersion());
    }
}

export const applicationVersionRoute: Routes = [
    {
        path: 'application-version',
        component: ApplicationVersionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.applicationVersion.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'application-version/:id/view',
        component: ApplicationVersionDetailComponent,
        resolve: {
            applicationVersion: ApplicationVersionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.applicationVersion.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'application-version/new',
        component: ApplicationVersionUpdateComponent,
        resolve: {
            applicationVersion: ApplicationVersionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.applicationVersion.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'application-version/:id/edit',
        component: ApplicationVersionUpdateComponent,
        resolve: {
            applicationVersion: ApplicationVersionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.applicationVersion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const applicationVersionPopupRoute: Routes = [
    {
        path: 'application-version/:id/delete',
        component: ApplicationVersionDeletePopupComponent,
        resolve: {
            applicationVersion: ApplicationVersionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.applicationVersion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
