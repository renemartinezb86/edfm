import { IService } from 'app/shared/model//service.model';
import { IFreeUnit } from 'app/shared/model//free-unit.model';

export interface IChargingSystem {
    id?: number;
    serviceClassId?: string;
    offerTemplate?: string;
    characteristicName?: string;
    service?: IService;
    freeUnit?: IFreeUnit;
}

export class ChargingSystem implements IChargingSystem {
    constructor(
        public id?: number,
        public serviceClassId?: string,
        public offerTemplate?: string,
        public characteristicName?: string,
        public service?: IService,
        public freeUnit?: IFreeUnit
    ) {}
}
