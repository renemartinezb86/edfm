import { Moment } from 'moment';

export interface IBucoVersion {
    id?: number;
    name?: string;
    fileName?: string;
    createdDate?: Moment;
}

export class BucoVersion implements IBucoVersion {
    constructor(public id?: number, public name?: string, public fileName?: string, public createdDate?: Moment) {}
}
