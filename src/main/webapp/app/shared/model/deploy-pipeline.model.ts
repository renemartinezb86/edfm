import { IApplication } from 'app/shared/model//application.model';
import { IEnvironment } from 'app/shared/model//environment.model';

export interface IDeployPipeline {
    id?: number;
    name?: string;
    url?: string;
    username?: string;
    password?: string;
    jobName?: string;
    application?: IApplication;
    environment?: IEnvironment;
}

export class DeployPipeline implements IDeployPipeline {
    constructor(
        public id?: number,
        public name?: string,
        public url?: string,
        public username?: string,
        public password?: string,
        public jobName?: string,
        public application?: IApplication,
        public environment?: IEnvironment
    ) {}
}
