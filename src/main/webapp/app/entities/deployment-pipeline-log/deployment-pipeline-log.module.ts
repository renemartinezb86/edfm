import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdfmSharedModule } from 'app/shared';
import {
    DeploymentPipelineLogComponent,
    DeploymentPipelineLogDetailComponent,
    DeploymentPipelineLogUpdateComponent,
    DeploymentPipelineLogDeletePopupComponent,
    DeploymentPipelineLogDeleteDialogComponent,
    deploymentPipelineLogRoute,
    deploymentPipelineLogPopupRoute
} from './';

const ENTITY_STATES = [...deploymentPipelineLogRoute, ...deploymentPipelineLogPopupRoute];

@NgModule({
    imports: [EdfmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DeploymentPipelineLogComponent,
        DeploymentPipelineLogDetailComponent,
        DeploymentPipelineLogUpdateComponent,
        DeploymentPipelineLogDeleteDialogComponent,
        DeploymentPipelineLogDeletePopupComponent
    ],
    entryComponents: [
        DeploymentPipelineLogComponent,
        DeploymentPipelineLogUpdateComponent,
        DeploymentPipelineLogDeleteDialogComponent,
        DeploymentPipelineLogDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmDeploymentPipelineLogModule {}
