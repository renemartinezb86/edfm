import { IApplicationVersion } from 'app/shared/model//application-version.model';

export interface IApplicationVersionRelation {
    id?: number;
    name?: string;
    version?: string;
    applicationVersion?: IApplicationVersion;
}

export class ApplicationVersionRelation implements IApplicationVersionRelation {
    constructor(public id?: number, public name?: string, public version?: string, public applicationVersion?: IApplicationVersion) {}
}
