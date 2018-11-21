import { IProductOffering } from 'app/shared/model//product-offering.model';

export const enum PoPriceType {
    Recurring = 'Recurring',
    OneShot = 'OneShot'
}

export const enum PaymentType {
    Cash = 'Cash',
    Bill = 'Bill'
}

export interface IPoPrice {
    id?: number;
    poPriceType?: PoPriceType;
    amount?: number;
    paymentType?: PaymentType;
    showInBill?: boolean;
    payInAdvance?: boolean;
    billOnSuspension?: boolean;
    multiDiscount?: boolean;
    productOffering?: IProductOffering;
}

export class PoPrice implements IPoPrice {
    constructor(
        public id?: number,
        public poPriceType?: PoPriceType,
        public amount?: number,
        public paymentType?: PaymentType,
        public showInBill?: boolean,
        public payInAdvance?: boolean,
        public billOnSuspension?: boolean,
        public multiDiscount?: boolean,
        public productOffering?: IProductOffering
    ) {
        this.showInBill = this.showInBill || false;
        this.payInAdvance = this.payInAdvance || false;
        this.billOnSuspension = this.billOnSuspension || false;
        this.multiDiscount = this.multiDiscount || false;
    }
}
