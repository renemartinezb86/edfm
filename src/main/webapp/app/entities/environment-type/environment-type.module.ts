import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdfmSharedModule } from 'app/shared';
import {
    EnvironmentTypeComponent,
    EnvironmentTypeDetailComponent,
    EnvironmentTypeUpdateComponent,
    EnvironmentTypeDeletePopupComponent,
    EnvironmentTypeDeleteDialogComponent,
    environmentTypeRoute,
    environmentTypePopupRoute
} from './';

const ENTITY_STATES = [...environmentTypeRoute, ...environmentTypePopupRoute];

@NgModule({
    imports: [EdfmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EnvironmentTypeComponent,
        EnvironmentTypeDetailComponent,
        EnvironmentTypeUpdateComponent,
        EnvironmentTypeDeleteDialogComponent,
        EnvironmentTypeDeletePopupComponent
    ],
    entryComponents: [
        EnvironmentTypeComponent,
        EnvironmentTypeUpdateComponent,
        EnvironmentTypeDeleteDialogComponent,
        EnvironmentTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmEnvironmentTypeModule {}
