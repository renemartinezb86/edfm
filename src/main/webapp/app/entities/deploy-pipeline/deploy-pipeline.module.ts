import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdfmSharedModule } from 'app/shared';
import {
    DeployPipelineComponent,
    DeployPipelineDetailComponent,
    DeployPipelineUpdateComponent,
    DeployPipelineDeletePopupComponent,
    DeployPipelineDeleteDialogComponent,
    deployPipelineRoute,
    deployPipelinePopupRoute
} from './';

const ENTITY_STATES = [...deployPipelineRoute, ...deployPipelinePopupRoute];

@NgModule({
    imports: [EdfmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DeployPipelineComponent,
        DeployPipelineDetailComponent,
        DeployPipelineUpdateComponent,
        DeployPipelineDeleteDialogComponent,
        DeployPipelineDeletePopupComponent
    ],
    entryComponents: [
        DeployPipelineComponent,
        DeployPipelineUpdateComponent,
        DeployPipelineDeleteDialogComponent,
        DeployPipelineDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmDeployPipelineModule {}
