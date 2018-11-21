import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdfmSharedModule } from 'app/shared';
import {
    ApplicationVersionComponent,
    ApplicationVersionDetailComponent,
    ApplicationVersionUpdateComponent,
    ApplicationVersionDeletePopupComponent,
    ApplicationVersionDeleteDialogComponent,
    applicationVersionRoute,
    applicationVersionPopupRoute
} from './';

const ENTITY_STATES = [...applicationVersionRoute, ...applicationVersionPopupRoute];

@NgModule({
    imports: [EdfmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ApplicationVersionComponent,
        ApplicationVersionDetailComponent,
        ApplicationVersionUpdateComponent,
        ApplicationVersionDeleteDialogComponent,
        ApplicationVersionDeletePopupComponent
    ],
    entryComponents: [
        ApplicationVersionComponent,
        ApplicationVersionUpdateComponent,
        ApplicationVersionDeleteDialogComponent,
        ApplicationVersionDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmApplicationVersionModule {}
