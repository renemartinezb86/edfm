import { IPoPrice } from 'app/shared/model//po-price.model';
import { IBucoSheet } from 'app/shared/model//buco-sheet.model';
import { IRule } from 'app/shared/model//rule.model';
import { IResource } from 'app/shared/model//resource.model';
import { INetworkResource } from 'app/shared/model//network-resource.model';
import { IService } from 'app/shared/model//service.model';
import { ICfssPop } from 'app/shared/model//cfss-pop.model';
import { IFreeUnit } from 'app/shared/model//free-unit.model';

export interface IProductOffering {
    id?: number;
    poId?: string;
    name?: string;
    comercialName?: string;
    poPrice?: IPoPrice;
    bucoSheet?: IBucoSheet;
    rules?: IRule[];
    resources?: IResource[];
    networkResources?: INetworkResource[];
    services?: IService[];
    cfssPops?: ICfssPop[];
    freeUnits?: IFreeUnit[];
}

export class ProductOffering implements IProductOffering {
    constructor(
        public id?: number,
        public poId?: string,
        public name?: string,
        public comercialName?: string,
        public poPrice?: IPoPrice,
        public bucoSheet?: IBucoSheet,
        public rules?: IRule[],
        public resources?: IResource[],
        public networkResources?: INetworkResource[],
        public services?: IService[],
        public cfssPops?: ICfssPop[],
        public freeUnits?: IFreeUnit[]
    ) {}
}
