import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdfmSharedModule } from 'app/shared';
import {
    ChargingSystemComponent,
    ChargingSystemDetailComponent,
    ChargingSystemUpdateComponent,
    ChargingSystemDeletePopupComponent,
    ChargingSystemDeleteDialogComponent,
    chargingSystemRoute,
    chargingSystemPopupRoute
} from './';

const ENTITY_STATES = [...chargingSystemRoute, ...chargingSystemPopupRoute];

@NgModule({
    imports: [EdfmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ChargingSystemComponent,
        ChargingSystemDetailComponent,
        ChargingSystemUpdateComponent,
        ChargingSystemDeleteDialogComponent,
        ChargingSystemDeletePopupComponent
    ],
    entryComponents: [
        ChargingSystemComponent,
        ChargingSystemUpdateComponent,
        ChargingSystemDeleteDialogComponent,
        ChargingSystemDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmChargingSystemModule {}
