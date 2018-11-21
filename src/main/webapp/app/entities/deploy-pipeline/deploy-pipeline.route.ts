import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DeployPipeline } from 'app/shared/model/deploy-pipeline.model';
import { DeployPipelineService } from './deploy-pipeline.service';
import { DeployPipelineComponent } from './deploy-pipeline.component';
import { DeployPipelineDetailComponent } from './deploy-pipeline-detail.component';
import { DeployPipelineUpdateComponent } from './deploy-pipeline-update.component';
import { DeployPipelineDeletePopupComponent } from './deploy-pipeline-delete-dialog.component';
import { IDeployPipeline } from 'app/shared/model/deploy-pipeline.model';

@Injectable({ providedIn: 'root' })
export class DeployPipelineResolve implements Resolve<IDeployPipeline> {
    constructor(private service: DeployPipelineService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DeployPipeline> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DeployPipeline>) => response.ok),
                map((deployPipeline: HttpResponse<DeployPipeline>) => deployPipeline.body)
            );
        }
        return of(new DeployPipeline());
    }
}

export const deployPipelineRoute: Routes = [
    {
        path: 'deploy-pipeline',
        component: DeployPipelineComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.deployPipeline.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'deploy-pipeline/:id/view',
        component: DeployPipelineDetailComponent,
        resolve: {
            deployPipeline: DeployPipelineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.deployPipeline.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'deploy-pipeline/new',
        component: DeployPipelineUpdateComponent,
        resolve: {
            deployPipeline: DeployPipelineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.deployPipeline.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'deploy-pipeline/:id/edit',
        component: DeployPipelineUpdateComponent,
        resolve: {
            deployPipeline: DeployPipelineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.deployPipeline.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const deployPipelinePopupRoute: Routes = [
    {
        path: 'deploy-pipeline/:id/delete',
        component: DeployPipelineDeletePopupComponent,
        resolve: {
            deployPipeline: DeployPipelineResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.deployPipeline.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
