import { IService } from 'app/shared/model//service.model';

export interface IBscs {
    id?: number;
    services?: string;
    service?: IService;
}

export class Bscs implements IBscs {
    constructor(public id?: number, public services?: string, public service?: IService) {}
}
