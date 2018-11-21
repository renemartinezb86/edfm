import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApplicationVersionRelation } from 'app/shared/model/application-version-relation.model';
import { ApplicationVersionRelationService } from './application-version-relation.service';
import { ApplicationVersionRelationComponent } from './application-version-relation.component';
import { ApplicationVersionRelationDetailComponent } from './application-version-relation-detail.component';
import { ApplicationVersionRelationUpdateComponent } from './application-version-relation-update.component';
import { ApplicationVersionRelationDeletePopupComponent } from './application-version-relation-delete-dialog.component';
import { IApplicationVersionRelation } from 'app/shared/model/application-version-relation.model';

@Injectable({ providedIn: 'root' })
export class ApplicationVersionRelationResolve implements Resolve<IApplicationVersionRelation> {
    constructor(private service: ApplicationVersionRelationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ApplicationVersionRelation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ApplicationVersionRelation>) => response.ok),
                map((applicationVersionRelation: HttpResponse<ApplicationVersionRelation>) => applicationVersionRelation.body)
            );
        }
        return of(new ApplicationVersionRelation());
    }
}

export const applicationVersionRelationRoute: Routes = [
    {
        path: 'application-version-relation',
        component: ApplicationVersionRelationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.applicationVersionRelation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'application-version-relation/:id/view',
        component: ApplicationVersionRelationDetailComponent,
        resolve: {
            applicationVersionRelation: ApplicationVersionRelationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.applicationVersionRelation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'application-version-relation/new',
        component: ApplicationVersionRelationUpdateComponent,
        resolve: {
            applicationVersionRelation: ApplicationVersionRelationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.applicationVersionRelation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'application-version-relation/:id/edit',
        component: ApplicationVersionRelationUpdateComponent,
        resolve: {
            applicationVersionRelation: ApplicationVersionRelationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.applicationVersionRelation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const applicationVersionRelationPopupRoute: Routes = [
    {
        path: 'application-version-relation/:id/delete',
        component: ApplicationVersionRelationDeletePopupComponent,
        resolve: {
            applicationVersionRelation: ApplicationVersionRelationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.applicationVersionRelation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
