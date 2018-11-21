import { Moment } from 'moment';
import { IApplicationVersion } from 'app/shared/model//application-version.model';
import { IEnvironment } from 'app/shared/model//environment.model';
import { IDeploymentPipelineLog } from 'app/shared/model//deployment-pipeline-log.model';

export interface IDeployment {
    id?: number;
    name?: string;
    date?: Moment;
    applicationVersion?: IApplicationVersion;
    environment?: IEnvironment;
    deploymentLogs?: IDeploymentPipelineLog;
}

export class Deployment implements IDeployment {
    constructor(
        public id?: number,
        public name?: string,
        public date?: Moment,
        public applicationVersion?: IApplicationVersion,
        public environment?: IEnvironment,
        public deploymentLogs?: IDeploymentPipelineLog
    ) {}
}
