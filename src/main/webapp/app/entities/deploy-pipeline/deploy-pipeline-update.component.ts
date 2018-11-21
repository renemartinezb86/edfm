import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDeployPipeline } from 'app/shared/model/deploy-pipeline.model';
import { DeployPipelineService } from './deploy-pipeline.service';
import { IApplication } from 'app/shared/model/application.model';
import { ApplicationService } from 'app/entities/application';
import { IEnvironment } from 'app/shared/model/environment.model';
import { EnvironmentService } from 'app/entities/environment';

@Component({
    selector: 'jhi-deploy-pipeline-update',
    templateUrl: './deploy-pipeline-update.component.html'
})
export class DeployPipelineUpdateComponent implements OnInit {
    deployPipeline: IDeployPipeline;
    isSaving: boolean;

    applications: IApplication[];

    environments: IEnvironment[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private deployPipelineService: DeployPipelineService,
        private applicationService: ApplicationService,
        private environmentService: EnvironmentService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ deployPipeline }) => {
            this.deployPipeline = deployPipeline;
        });
        this.applicationService.query().subscribe(
            (res: HttpResponse<IApplication[]>) => {
                this.applications = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.environmentService.query().subscribe(
            (res: HttpResponse<IEnvironment[]>) => {
                this.environments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.deployPipeline.id !== undefined) {
            this.subscribeToSaveResponse(this.deployPipelineService.update(this.deployPipeline));
        } else {
            this.subscribeToSaveResponse(this.deployPipelineService.create(this.deployPipeline));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDeployPipeline>>) {
        result.subscribe((res: HttpResponse<IDeployPipeline>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackApplicationById(index: number, item: IApplication) {
        return item.id;
    }

    trackEnvironmentById(index: number, item: IEnvironment) {
        return item.id;
    }
}
