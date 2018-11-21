import { IChargingSystem } from 'app/shared/model//charging-system.model';
import { IBscs } from 'app/shared/model//bscs.model';
import { IProductOffering } from 'app/shared/model//product-offering.model';

export const enum ServiceType {
    Basic = 'Basic',
    Barring = 'Barring',
    Service = 'Service',
    Voice = 'Voice',
    SMS = 'SMS',
    Data = 'Data',
    LDIVoice = 'LDIVoice',
    LDISMS = 'LDISMS',
    VoiceRoaming = 'VoiceRoaming',
    SMSRoaming = 'SMSRoaming',
    DataRoaming = 'DataRoaming',
    RoamingTAPIN = 'RoamingTAPIN',
    VASBalance = 'VASBalance'
}

export interface IService {
    id?: number;
    name?: string;
    serviceType?: ServiceType;
    chargingSystem?: IChargingSystem;
    bscs?: IBscs;
    productOfferings?: IProductOffering[];
}

export class Service implements IService {
    constructor(
        public id?: number,
        public name?: string,
        public serviceType?: ServiceType,
        public chargingSystem?: IChargingSystem,
        public bscs?: IBscs,
        public productOfferings?: IProductOffering[]
    ) {}
}
