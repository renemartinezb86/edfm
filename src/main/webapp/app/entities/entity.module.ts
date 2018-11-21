import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { EdfmBucoVersionModule } from './buco-version/buco-version.module';
import { EdfmBucoSheetModule } from './buco-sheet/buco-sheet.module';
import { EdfmProductOfferingModule } from './product-offering/product-offering.module';
import { EdfmPOCharacteristicModule } from './po-characteristic/po-characteristic.module';
import { EdfmServiceModule } from './service/service.module';
import { EdfmCfssPopModule } from './cfss-pop/cfss-pop.module';
import { EdfmRuleModule } from './rule/rule.module';
import { EdfmNetworkResourceModule } from './network-resource/network-resource.module';
import { EdfmNetworkParameterModule } from './network-parameter/network-parameter.module';
import { EdfmResourceModule } from './resource/resource.module';
import { EdfmPoPriceModule } from './po-price/po-price.module';
import { EdfmFreeUnitModule } from './free-unit/free-unit.module';
import { EdfmChargingSystemModule } from './charging-system/charging-system.module';
import { EdfmBscsModule } from './bscs/bscs.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        EdfmBucoVersionModule,
        EdfmBucoSheetModule,
        EdfmProductOfferingModule,
        EdfmPOCharacteristicModule,
        EdfmServiceModule,
        EdfmCfssPopModule,
        EdfmRuleModule,
        EdfmNetworkResourceModule,
        EdfmNetworkParameterModule,
        EdfmResourceModule,
        EdfmPoPriceModule,
        EdfmFreeUnitModule,
        EdfmChargingSystemModule,
        EdfmBscsModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EdfmEntityModule {}
