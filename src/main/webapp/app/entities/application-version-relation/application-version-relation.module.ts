import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdfmSharedModule } from 'app/shared';
import {
    ApplicationVersionRelationComponent,
    ApplicationVersionRelationDetailComponent,
    ApplicationVersionRelationUpdateComponent,
    ApplicationVersionRelationDeletePopupComponent,
    ApplicationVersionRelationDeleteDialogComponent,
    applicationVersionRelationRoute,
    applicationVersionRelationPopupRoute
} from './';

const ENTITY_STATES = [...applicationVersionRelationRoute, ...applicationVersionRelationPopupRoute];

@NgModule({
    imports: [EdfmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ApplicationVersionRelationComponent,
        ApplicationVersionRelationDetailComponent,
        ApplicationVersionRelationUpdateComponent,
        ApplicationVersionRelationDeleteDialogComponent,
        ApplicationVersionRelationDeletePopupComponent
    ],
    entryComponents: [
        ApplicationVersionRelationComponent,
        ApplicationVersionRelationUpdateComponent,
        ApplicationVersionRelationDeleteDialogComponent,
        ApplicationVersionRelationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmApplicationVersionRelationModule {}
