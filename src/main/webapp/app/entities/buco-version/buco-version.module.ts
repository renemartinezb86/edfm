import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdfmSharedModule } from 'app/shared';
import {
    BucoVersionComponent,
    BucoVersionDetailComponent,
    BucoVersionUpdateComponent,
    BucoVersionDeletePopupComponent,
    BucoVersionDeleteDialogComponent,
    bucoVersionRoute,
    bucoVersionPopupRoute
} from './';

const ENTITY_STATES = [...bucoVersionRoute, ...bucoVersionPopupRoute];

@NgModule({
    imports: [EdfmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BucoVersionComponent,
        BucoVersionDetailComponent,
        BucoVersionUpdateComponent,
        BucoVersionDeleteDialogComponent,
        BucoVersionDeletePopupComponent
    ],
    entryComponents: [BucoVersionComponent, BucoVersionUpdateComponent, BucoVersionDeleteDialogComponent, BucoVersionDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmBucoVersionModule {}
