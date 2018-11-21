import { INetworkParameter } from 'app/shared/model//network-parameter.model';
import { IProductOffering } from 'app/shared/model//product-offering.model';

export interface INetworkResource {
    id?: number;
    name?: string;
    networkParameters?: INetworkParameter[];
    productOfferings?: IProductOffering[];
}

export class NetworkResource implements INetworkResource {
    constructor(
        public id?: number,
        public name?: string,
        public networkParameters?: INetworkParameter[],
        public productOfferings?: IProductOffering[]
    ) {}
}
