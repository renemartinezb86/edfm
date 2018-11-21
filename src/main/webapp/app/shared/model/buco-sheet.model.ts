import { IBucoVersion } from 'app/shared/model//buco-version.model';

export const enum SheetType {
    PO = 'PO',
    TEMPLATE = 'TEMPLATE',
    CONFIG = 'CONFIG',
    RULE = 'RULE'
}

export interface IBucoSheet {
    id?: number;
    sheetName?: string;
    sheetType?: SheetType;
    bucoVersion?: IBucoVersion;
}

export class BucoSheet implements IBucoSheet {
    constructor(public id?: number, public sheetName?: string, public sheetType?: SheetType, public bucoVersion?: IBucoVersion) {}
}
