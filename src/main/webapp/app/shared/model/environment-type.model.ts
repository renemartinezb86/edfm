import { IEnvironment } from 'app/shared/model//environment.model';

export interface IEnvironmentType {
    id?: number;
    name?: string;
    environments?: IEnvironment[];
}

export class EnvironmentType implements IEnvironmentType {
    constructor(public id?: number, public name?: string, public environments?: IEnvironment[]) {}
}
