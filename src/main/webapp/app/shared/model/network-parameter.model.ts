import { INetworkResource } from 'app/shared/model//network-resource.model';

export interface INetworkParameter {
    id?: number;
    name?: string;
    type?: string;
    value?: string;
    networkResources?: INetworkResource[];
}

export class NetworkParameter implements INetworkParameter {
    constructor(
        public id?: number,
        public name?: string,
        public type?: string,
        public value?: string,
        public networkResources?: INetworkResource[]
    ) {}
}
