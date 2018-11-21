export interface IDeploymentPipelineLog {
    id?: number;
    name?: string;
    url?: string;
    description?: string;
    status?: string;
}

export class DeploymentPipelineLog implements IDeploymentPipelineLog {
    constructor(public id?: number, public name?: string, public url?: string, public description?: string, public status?: string) {}
}
