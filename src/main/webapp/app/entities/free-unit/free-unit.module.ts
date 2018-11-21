import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdfmSharedModule } from 'app/shared';
import {
    FreeUnitComponent,
    FreeUnitDetailComponent,
    FreeUnitUpdateComponent,
    FreeUnitDeletePopupComponent,
    FreeUnitDeleteDialogComponent,
    freeUnitRoute,
    freeUnitPopupRoute
} from './';

const ENTITY_STATES = [...freeUnitRoute, ...freeUnitPopupRoute];

@NgModule({
    imports: [EdfmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FreeUnitComponent,
        FreeUnitDetailComponent,
        FreeUnitUpdateComponent,
        FreeUnitDeleteDialogComponent,
        FreeUnitDeletePopupComponent
    ],
    entryComponents: [FreeUnitComponent, FreeUnitUpdateComponent, FreeUnitDeleteDialogComponent, FreeUnitDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmFreeUnitModule {}
