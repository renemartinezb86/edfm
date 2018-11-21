import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeployment } from 'app/shared/model/deployment.model';

@Component({
    selector: 'jhi-deployment-detail',
    templateUrl: './deployment-detail.component.html'
})
export class DeploymentDetailComponent implements OnInit {
    deployment: IDeployment;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ deployment }) => {
            this.deployment = deployment;
        });
    }

    previousState() {
        window.history.back();
    }
}
