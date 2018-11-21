import { IDeployPipeline } from 'app/shared/model//deploy-pipeline.model';
import { IApplicationVersion } from 'app/shared/model//application-version.model';

export const enum Program {
    PREPAID = 'PREPAID',
    POSTPAID = 'POSTPAID'
}

export interface IApplication {
    id?: number;
    name?: string;
    program?: Program;
    gitURL?: string;
    gitToken?: string;
    jiraURL?: string;
    jiraToken?: string;
    deployPipelines?: IDeployPipeline[];
    applicationVersions?: IApplicationVersion[];
}

export class Application implements IApplication {
    constructor(
        public id?: number,
        public name?: string,
        public program?: Program,
        public gitURL?: string,
        public gitToken?: string,
        public jiraURL?: string,
        public jiraToken?: string,
        public deployPipelines?: IDeployPipeline[],
        public applicationVersions?: IApplicationVersion[]
    ) {}
}
