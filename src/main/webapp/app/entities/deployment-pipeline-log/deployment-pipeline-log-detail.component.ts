import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeploymentPipelineLog } from 'app/shared/model/deployment-pipeline-log.model';

@Component({
    selector: 'jhi-deployment-pipeline-log-detail',
    templateUrl: './deployment-pipeline-log-detail.component.html'
})
export class DeploymentPipelineLogDetailComponent implements OnInit {
    deploymentPipelineLog: IDeploymentPipelineLog;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ deploymentPipelineLog }) => {
            this.deploymentPipelineLog = deploymentPipelineLog;
        });
    }

    previousState() {
        window.history.back();
    }
}
