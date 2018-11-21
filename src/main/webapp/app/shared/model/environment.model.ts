import { IDeployPipeline } from 'app/shared/model//deploy-pipeline.model';
import { IDeployment } from 'app/shared/model//deployment.model';
import { IEnvironmentType } from 'app/shared/model//environment-type.model';

export interface IEnvironment {
    id?: number;
    name?: string;
    deployPipelines?: IDeployPipeline[];
    deployments?: IDeployment[];
    environmentType?: IEnvironmentType;
}

export class Environment implements IEnvironment {
    constructor(
        public id?: number,
        public name?: string,
        public deployPipelines?: IDeployPipeline[],
        public deployments?: IDeployment[],
        public environmentType?: IEnvironmentType
    ) {}
}
