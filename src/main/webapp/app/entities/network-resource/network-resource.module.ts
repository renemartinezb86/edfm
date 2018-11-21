import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdfmSharedModule } from 'app/shared';
import {
    NetworkResourceComponent,
    NetworkResourceDetailComponent,
    NetworkResourceUpdateComponent,
    NetworkResourceDeletePopupComponent,
    NetworkResourceDeleteDialogComponent,
    networkResourceRoute,
    networkResourcePopupRoute
} from './';

const ENTITY_STATES = [...networkResourceRoute, ...networkResourcePopupRoute];

@NgModule({
    imports: [EdfmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        NetworkResourceComponent,
        NetworkResourceDetailComponent,
        NetworkResourceUpdateComponent,
        NetworkResourceDeleteDialogComponent,
        NetworkResourceDeletePopupComponent
    ],
    entryComponents: [
        NetworkResourceComponent,
        NetworkResourceUpdateComponent,
        NetworkResourceDeleteDialogComponent,
        NetworkResourceDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmNetworkResourceModule {}
