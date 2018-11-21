import { IProductOffering } from 'app/shared/model//product-offering.model';

export interface IPOCharacteristic {
    id?: number;
    name?: string;
    value?: string;
    productOffering?: IProductOffering;
}

export class POCharacteristic implements IPOCharacteristic {
    constructor(public id?: number, public name?: string, public value?: string, public productOffering?: IProductOffering) {}
}
