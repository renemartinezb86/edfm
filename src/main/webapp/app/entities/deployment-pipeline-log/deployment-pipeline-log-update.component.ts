import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDeploymentPipelineLog } from 'app/shared/model/deployment-pipeline-log.model';
import { DeploymentPipelineLogService } from './deployment-pipeline-log.service';

@Component({
    selector: 'jhi-deployment-pipeline-log-update',
    templateUrl: './deployment-pipeline-log-update.component.html'
})
export class DeploymentPipelineLogUpdateComponent implements OnInit {
    deploymentPipelineLog: IDeploymentPipelineLog;
    isSaving: boolean;

    constructor(private deploymentPipelineLogService: DeploymentPipelineLogService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ deploymentPipelineLog }) => {
            this.deploymentPipelineLog = deploymentPipelineLog;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.deploymentPipelineLog.id !== undefined) {
            this.subscribeToSaveResponse(this.deploymentPipelineLogService.update(this.deploymentPipelineLog));
        } else {
            this.subscribeToSaveResponse(this.deploymentPipelineLogService.create(this.deploymentPipelineLog));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDeploymentPipelineLog>>) {
        result.subscribe(
            (res: HttpResponse<IDeploymentPipelineLog>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
