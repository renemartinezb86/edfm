import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EnvironmentType } from 'app/shared/model/environment-type.model';
import { EnvironmentTypeService } from './environment-type.service';
import { EnvironmentTypeComponent } from './environment-type.component';
import { EnvironmentTypeDetailComponent } from './environment-type-detail.component';
import { EnvironmentTypeUpdateComponent } from './environment-type-update.component';
import { EnvironmentTypeDeletePopupComponent } from './environment-type-delete-dialog.component';
import { IEnvironmentType } from 'app/shared/model/environment-type.model';

@Injectable({ providedIn: 'root' })
export class EnvironmentTypeResolve implements Resolve<IEnvironmentType> {
    constructor(private service: EnvironmentTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<EnvironmentType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EnvironmentType>) => response.ok),
                map((environmentType: HttpResponse<EnvironmentType>) => environmentType.body)
            );
        }
        return of(new EnvironmentType());
    }
}

export const environmentTypeRoute: Routes = [
    {
        path: 'environment-type',
        component: EnvironmentTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.environmentType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'environment-type/:id/view',
        component: EnvironmentTypeDetailComponent,
        resolve: {
            environmentType: EnvironmentTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.environmentType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'environment-type/new',
        component: EnvironmentTypeUpdateComponent,
        resolve: {
            environmentType: EnvironmentTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.environmentType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'environment-type/:id/edit',
        component: EnvironmentTypeUpdateComponent,
        resolve: {
            environmentType: EnvironmentTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.environmentType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const environmentTypePopupRoute: Routes = [
    {
        path: 'environment-type/:id/delete',
        component: EnvironmentTypeDeletePopupComponent,
        resolve: {
            environmentType: EnvironmentTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.environmentType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
