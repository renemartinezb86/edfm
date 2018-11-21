import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDeploymentPipelineLog } from 'app/shared/model/deployment-pipeline-log.model';
import { Principal } from 'app/core';
import { DeploymentPipelineLogService } from './deployment-pipeline-log.service';

@Component({
    selector: 'jhi-deployment-pipeline-log',
    templateUrl: './deployment-pipeline-log.component.html'
})
export class DeploymentPipelineLogComponent implements OnInit, OnDestroy {
    deploymentPipelineLogs: IDeploymentPipelineLog[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private deploymentPipelineLogService: DeploymentPipelineLogService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.deploymentPipelineLogService.query().subscribe(
            (res: HttpResponse<IDeploymentPipelineLog[]>) => {
                this.deploymentPipelineLogs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDeploymentPipelineLogs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDeploymentPipelineLog) {
        return item.id;
    }

    registerChangeInDeploymentPipelineLogs() {
        this.eventSubscriber = this.eventManager.subscribe('deploymentPipelineLogListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
