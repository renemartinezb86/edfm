import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EdfmSharedModule } from 'app/shared';
import {
    CfssPopComponent,
    CfssPopDetailComponent,
    CfssPopUpdateComponent,
    CfssPopDeletePopupComponent,
    CfssPopDeleteDialogComponent,
    cfssPopRoute,
    cfssPopPopupRoute
} from './';

const ENTITY_STATES = [...cfssPopRoute, ...cfssPopPopupRoute];

@NgModule({
    imports: [EdfmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CfssPopComponent,
        CfssPopDetailComponent,
        CfssPopUpdateComponent,
        CfssPopDeleteDialogComponent,
        CfssPopDeletePopupComponent
    ],
    entryComponents: [CfssPopComponent, CfssPopUpdateComponent, CfssPopDeleteDialogComponent, CfssPopDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmCfssPopModule {}
