import { IApplication } from 'app/shared/model//application.model';
import { IApplicationVersionRelation } from 'app/shared/model//application-version-relation.model';
import { IDeployment } from 'app/shared/model//deployment.model';

export interface IApplicationVersion {
    id?: number;
    name?: string;
    application?: IApplication;
    versionRelations?: IApplicationVersionRelation[];
    deployments?: IDeployment[];
}

export class ApplicationVersion implements IApplicationVersion {
    constructor(
        public id?: number,
        public name?: string,
        public application?: IApplication,
        public versionRelations?: IApplicationVersionRelation[],
        public deployments?: IDeployment[]
    ) {}
}
