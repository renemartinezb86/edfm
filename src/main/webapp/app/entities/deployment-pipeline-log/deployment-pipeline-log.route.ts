import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DeploymentPipelineLog } from 'app/shared/model/deployment-pipeline-log.model';
import { DeploymentPipelineLogService } from './deployment-pipeline-log.service';
import { DeploymentPipelineLogComponent } from './deployment-pipeline-log.component';
import { DeploymentPipelineLogDetailComponent } from './deployment-pipeline-log-detail.component';
import { DeploymentPipelineLogUpdateComponent } from './deployment-pipeline-log-update.component';
import { DeploymentPipelineLogDeletePopupComponent } from './deployment-pipeline-log-delete-dialog.component';
import { IDeploymentPipelineLog } from 'app/shared/model/deployment-pipeline-log.model';

@Injectable({ providedIn: 'root' })
export class DeploymentPipelineLogResolve implements Resolve<IDeploymentPipelineLog> {
    constructor(private service: DeploymentPipelineLogService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DeploymentPipelineLog> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DeploymentPipelineLog>) => response.ok),
                map((deploymentPipelineLog: HttpResponse<DeploymentPipelineLog>) => deploymentPipelineLog.body)
            );
        }
        return of(new DeploymentPipelineLog());
    }
}

export const deploymentPipelineLogRoute: Routes = [
    {
        path: 'deployment-pipeline-log',
        component: DeploymentPipelineLogComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.deploymentPipelineLog.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'deployment-pipeline-log/:id/view',
        component: DeploymentPipelineLogDetailComponent,
        resolve: {
            deploymentPipelineLog: DeploymentPipelineLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.deploymentPipelineLog.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'deployment-pipeline-log/new',
        component: DeploymentPipelineLogUpdateComponent,
        resolve: {
            deploymentPipelineLog: DeploymentPipelineLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.deploymentPipelineLog.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'deployment-pipeline-log/:id/edit',
        component: DeploymentPipelineLogUpdateComponent,
        resolve: {
            deploymentPipelineLog: DeploymentPipelineLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.deploymentPipelineLog.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const deploymentPipelineLogPopupRoute: Routes = [
    {
        path: 'deployment-pipeline-log/:id/delete',
        component: DeploymentPipelineLogDeletePopupComponent,
        resolve: {
            deploymentPipelineLog: DeploymentPipelineLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.deploymentPipelineLog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
