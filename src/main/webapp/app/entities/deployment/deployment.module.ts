import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdfmSharedModule } from 'app/shared';
import {
    DeploymentComponent,
    DeploymentDetailComponent,
    DeploymentUpdateComponent,
    DeploymentDeletePopupComponent,
    DeploymentDeleteDialogComponent,
    deploymentRoute,
    deploymentPopupRoute
} from './';

const ENTITY_STATES = [...deploymentRoute, ...deploymentPopupRoute];

@NgModule({
    imports: [EdfmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DeploymentComponent,
        DeploymentDetailComponent,
        DeploymentUpdateComponent,
        DeploymentDeleteDialogComponent,
        DeploymentDeletePopupComponent
    ],
    entryComponents: [DeploymentComponent, DeploymentUpdateComponent, DeploymentDeleteDialogComponent, DeploymentDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmDeploymentModule {}
