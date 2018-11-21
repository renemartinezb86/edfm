import { IProductOffering } from 'app/shared/model//product-offering.model';

export const enum ResourceType {
    Availability = 'Availability',
    Validation = 'Validation',
    Automatic = 'Automatic',
    Price = 'Price',
    Eligibility = 'Eligibility'
}

export interface IResource {
    id?: number;
    name?: string;
    resourceType?: ResourceType;
    relatedItem?: string;
    relatedItemCharacteristic?: string;
    productOfferings?: IProductOffering[];
}

export class Resource implements IResource {
    constructor(
        public id?: number,
        public name?: string,
        public resourceType?: ResourceType,
        public relatedItem?: string,
        public relatedItemCharacteristic?: string,
        public productOfferings?: IProductOffering[]
    ) {}
}
