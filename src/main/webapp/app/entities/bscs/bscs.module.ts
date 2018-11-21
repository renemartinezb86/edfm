import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdfmSharedModule } from 'app/shared';
import {
    BscsComponent,
    BscsDetailComponent,
    BscsUpdateComponent,
    BscsDeletePopupComponent,
    BscsDeleteDialogComponent,
    bscsRoute,
    bscsPopupRoute
} from './';

const ENTITY_STATES = [...bscsRoute, ...bscsPopupRoute];

@NgModule({
    imports: [EdfmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [BscsComponent, BscsDetailComponent, BscsUpdateComponent, BscsDeleteDialogComponent, BscsDeletePopupComponent],
    entryComponents: [BscsComponent, BscsUpdateComponent, BscsDeleteDialogComponent, BscsDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmBscsModule {}
