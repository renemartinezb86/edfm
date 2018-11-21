import { IChargingSystem } from 'app/shared/model//charging-system.model';
import { IProductOffering } from 'app/shared/model//product-offering.model';

export const enum FreeUnitType {
    RecurringFee = 'RecurringFee',
    SMS = 'SMS',
    Data = 'Data',
    AutoRefill = 'AutoRefill',
    RRSS = 'RRSS'
}

export interface IFreeUnit {
    id?: number;
    name?: string;
    freeUnitType?: FreeUnitType;
    units?: string;
    amount?: number;
    chargingSystem?: IChargingSystem;
    productOfferings?: IProductOffering[];
}

export class FreeUnit implements IFreeUnit {
    constructor(
        public id?: number,
        public name?: string,
        public freeUnitType?: FreeUnitType,
        public units?: string,
        public amount?: number,
        public chargingSystem?: IChargingSystem,
        public productOfferings?: IProductOffering[]
    ) {}
}
