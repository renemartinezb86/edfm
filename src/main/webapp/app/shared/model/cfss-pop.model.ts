import { IProductOffering } from 'app/shared/model//product-offering.model';

export const enum CfssPopType {
    Usage = 'Usage'
}

export interface ICfssPop {
    id?: number;
    cfssPopType?: CfssPopType;
    characteristic?: string;
    value?: string;
    productOfferings?: IProductOffering[];
}

export class CfssPop implements ICfssPop {
    constructor(
        public id?: number,
        public cfssPopType?: CfssPopType,
        public characteristic?: string,
        public value?: string,
        public productOfferings?: IProductOffering[]
    ) {}
}
