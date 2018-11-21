import { IProductOffering } from 'app/shared/model//product-offering.model';

export const enum RuleType {
    Availability = 'Availability',
    Validation = 'Validation',
    Automatic = 'Automatic',
    Price = 'Price',
    Eligibility = 'Eligibility'
}

export interface IRule {
    id?: number;
    ruleId?: string;
    ruleType?: RuleType;
    definition?: string;
    scenario?: string;
    detail?: string;
    productOfferings?: IProductOffering[];
}

export class Rule implements IRule {
    constructor(
        public id?: number,
        public ruleId?: string,
        public ruleType?: RuleType,
        public definition?: string,
        public scenario?: string,
        public detail?: string,
        public productOfferings?: IProductOffering[]
    ) {}
}
