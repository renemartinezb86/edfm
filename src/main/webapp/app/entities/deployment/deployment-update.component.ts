import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IDeployment } from 'app/shared/model/deployment.model';
import { DeploymentService } from './deployment.service';
import { IApplicationVersion } from 'app/shared/model/application-version.model';
import { ApplicationVersionService } from 'app/entities/application-version';
import { IEnvironment } from 'app/shared/model/environment.model';
import { EnvironmentService } from 'app/entities/environment';
import { IDeploymentPipelineLog } from 'app/shared/model/deployment-pipeline-log.model';
import { DeploymentPipelineLogService } from 'app/entities/deployment-pipeline-log';

@Component({
    selector: 'jhi-deployment-update',
    templateUrl: './deployment-update.component.html'
})
export class DeploymentUpdateComponent implements OnInit {
    deployment: IDeployment;
    isSaving: boolean;

    applicationversions: IApplicationVersion[];

    environments: IEnvironment[];

    deploymentlogs: IDeploymentPipelineLog[];
    date: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private deploymentService: DeploymentService,
        private applicationVersionService: ApplicationVersionService,
        private environmentService: EnvironmentService,
        private deploymentPipelineLogService: DeploymentPipelineLogService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ deployment }) => {
            this.deployment = deployment;
            this.date = this.deployment.date != null ? this.deployment.date.format(DATE_TIME_FORMAT) : null;
        });
        this.applicationVersionService.query().subscribe(
            (res: HttpResponse<IApplicationVersion[]>) => {
                this.applicationversions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.environmentService.query().subscribe(
            (res: HttpResponse<IEnvironment[]>) => {
                this.environments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.deploymentPipelineLogService.query({ 'deploymentId.specified': 'false' }).subscribe(
            (res: HttpResponse<IDeploymentPipelineLog[]>) => {
                if (!this.deployment.deploymentLogs || !this.deployment.deploymentLogs.id) {
                    this.deploymentlogs = res.body;
                } else {
                    this.deploymentPipelineLogService.find(this.deployment.deploymentLogs.id).subscribe(
                        (subRes: HttpResponse<IDeploymentPipelineLog>) => {
                            this.deploymentlogs = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.deployment.date = this.date != null ? moment(this.date, DATE_TIME_FORMAT) : null;
        if (this.deployment.id !== undefined) {
            this.subscribeToSaveResponse(this.deploymentService.update(this.deployment));
        } else {
            this.subscribeToSaveResponse(this.deploymentService.create(this.deployment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDeployment>>) {
        result.subscribe((res: HttpResponse<IDeployment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackApplicationVersionById(index: number, item: IApplicationVersion) {
        return item.id;
    }

    trackEnvironmentById(index: number, item: IEnvironment) {
        return item.id;
    }

    trackDeploymentPipelineLogById(index: number, item: IDeploymentPipelineLog) {
        return item.id;
    }
}
