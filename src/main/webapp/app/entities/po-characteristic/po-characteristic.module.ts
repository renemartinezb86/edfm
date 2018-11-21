import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdfmSharedModule } from 'app/shared';
import {
    POCharacteristicComponent,
    POCharacteristicDetailComponent,
    POCharacteristicUpdateComponent,
    POCharacteristicDeletePopupComponent,
    POCharacteristicDeleteDialogComponent,
    pOCharacteristicRoute,
    pOCharacteristicPopupRoute
} from './';

const ENTITY_STATES = [...pOCharacteristicRoute, ...pOCharacteristicPopupRoute];

@NgModule({
    imports: [EdfmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        POCharacteristicComponent,
        POCharacteristicDetailComponent,
        POCharacteristicUpdateComponent,
        POCharacteristicDeleteDialogComponent,
        POCharacteristicDeletePopupComponent
    ],
    entryComponents: [
        POCharacteristicComponent,
        POCharacteristicUpdateComponent,
        POCharacteristicDeleteDialogComponent,
        POCharacteristicDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmPOCharacteristicModule {}
