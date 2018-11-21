import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdfmSharedModule } from 'app/shared';
import {
    PoPriceComponent,
    PoPriceDetailComponent,
    PoPriceUpdateComponent,
    PoPriceDeletePopupComponent,
    PoPriceDeleteDialogComponent,
    poPriceRoute,
    poPricePopupRoute
} from './';

const ENTITY_STATES = [...poPriceRoute, ...poPricePopupRoute];

@NgModule({
    imports: [EdfmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PoPriceComponent,
        PoPriceDetailComponent,
        PoPriceUpdateComponent,
        PoPriceDeleteDialogComponent,
        PoPriceDeletePopupComponent
    ],
    entryComponents: [PoPriceComponent, PoPriceUpdateComponent, PoPriceDeleteDialogComponent, PoPriceDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmPoPriceModule {}
