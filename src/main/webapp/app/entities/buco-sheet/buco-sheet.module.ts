import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdfmSharedModule } from 'app/shared';
import {
    BucoSheetComponent,
    BucoSheetDetailComponent,
    BucoSheetUpdateComponent,
    BucoSheetDeletePopupComponent,
    BucoSheetDeleteDialogComponent,
    bucoSheetRoute,
    bucoSheetPopupRoute
} from './';

const ENTITY_STATES = [...bucoSheetRoute, ...bucoSheetPopupRoute];

@NgModule({
    imports: [EdfmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BucoSheetComponent,
        BucoSheetDetailComponent,
        BucoSheetUpdateComponent,
        BucoSheetDeleteDialogComponent,
        BucoSheetDeletePopupComponent
    ],
    entryComponents: [BucoSheetComponent, BucoSheetUpdateComponent, BucoSheetDeleteDialogComponent, BucoSheetDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmBucoSheetModule {}
