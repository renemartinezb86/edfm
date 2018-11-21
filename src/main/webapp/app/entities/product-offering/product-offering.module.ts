import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdfmSharedModule } from 'app/shared';
import {
    ProductOfferingComponent,
    ProductOfferingDetailComponent,
    ProductOfferingUpdateComponent,
    ProductOfferingDeletePopupComponent,
    ProductOfferingDeleteDialogComponent,
    productOfferingRoute,
    productOfferingPopupRoute
} from './';

const ENTITY_STATES = [...productOfferingRoute, ...productOfferingPopupRoute];

@NgModule({
    imports: [EdfmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductOfferingComponent,
        ProductOfferingDetailComponent,
        ProductOfferingUpdateComponent,
        ProductOfferingDeleteDialogComponent,
        ProductOfferingDeletePopupComponent
    ],
    entryComponents: [
        ProductOfferingComponent,
        ProductOfferingUpdateComponent,
        ProductOfferingDeleteDialogComponent,
        ProductOfferingDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmProductOfferingModule {}
