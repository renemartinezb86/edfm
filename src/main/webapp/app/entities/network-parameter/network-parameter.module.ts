import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdfmSharedModule } from 'app/shared';
import {
    NetworkParameterComponent,
    NetworkParameterDetailComponent,
    NetworkParameterUpdateComponent,
    NetworkParameterDeletePopupComponent,
    NetworkParameterDeleteDialogComponent,
    networkParameterRoute,
    networkParameterPopupRoute
} from './';

const ENTITY_STATES = [...networkParameterRoute, ...networkParameterPopupRoute];

@NgModule({
    imports: [EdfmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        NetworkParameterComponent,
        NetworkParameterDetailComponent,
        NetworkParameterUpdateComponent,
        NetworkParameterDeleteDialogComponent,
        NetworkParameterDeletePopupComponent
    ],
    entryComponents: [
        NetworkParameterComponent,
        NetworkParameterUpdateComponent,
        NetworkParameterDeleteDialogComponent,
        NetworkParameterDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmNetworkParameterModule {}
