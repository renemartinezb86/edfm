import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Deployment } from 'app/shared/model/deployment.model';
import { DeploymentService } from './deployment.service';
import { DeploymentComponent } from './deployment.component';
import { DeploymentDetailComponent } from './deployment-detail.component';
import { DeploymentUpdateComponent } from './deployment-update.component';
import { DeploymentDeletePopupComponent } from './deployment-delete-dialog.component';
import { IDeployment } from 'app/shared/model/deployment.model';

@Injectable({ providedIn: 'root' })
export class DeploymentResolve implements Resolve<IDeployment> {
    constructor(private service: DeploymentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Deployment> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Deployment>) => response.ok),
                map((deployment: HttpResponse<Deployment>) => deployment.body)
            );
        }
        return of(new Deployment());
    }
}

export const deploymentRoute: Routes = [
    {
        path: 'deployment',
        component: DeploymentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.deployment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'deployment/:id/view',
        component: DeploymentDetailComponent,
        resolve: {
            deployment: DeploymentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.deployment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'deployment/new',
        component: DeploymentUpdateComponent,
        resolve: {
            deployment: DeploymentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.deployment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'deployment/:id/edit',
        component: DeploymentUpdateComponent,
        resolve: {
            deployment: DeploymentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.deployment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const deploymentPopupRoute: Routes = [
    {
        path: 'deployment/:id/delete',
        component: DeploymentDeletePopupComponent,
        resolve: {
            deployment: DeploymentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.deployment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
