import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeployPipeline } from 'app/shared/model/deploy-pipeline.model';

@Component({
    selector: 'jhi-deploy-pipeline-detail',
    templateUrl: './deploy-pipeline-detail.component.html'
})
export class DeployPipelineDetailComponent implements OnInit {
    deployPipeline: IDeployPipeline;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ deployPipeline }) => {
            this.deployPipeline = deployPipeline;
        });
    }

    previousState() {
        window.history.back();
    }
}
