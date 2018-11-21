import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdfmSharedModule } from 'app/shared';
import {
    EnvironmentComponent,
    EnvironmentDetailComponent,
    EnvironmentUpdateComponent,
    EnvironmentDeletePopupComponent,
    EnvironmentDeleteDialogComponent,
    environmentRoute,
    environmentPopupRoute
} from './';

const ENTITY_STATES = [...environmentRoute, ...environmentPopupRoute];

@NgModule({
    imports: [EdfmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EnvironmentComponent,
        EnvironmentDetailComponent,
        EnvironmentUpdateComponent,
        EnvironmentDeleteDialogComponent,
        EnvironmentDeletePopupComponent
    ],
    entryComponents: [EnvironmentComponent, EnvironmentUpdateComponent, EnvironmentDeleteDialogComponent, EnvironmentDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmEnvironmentModule {}
