import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEnvironment } from 'app/shared/model/environment.model';
import { Principal } from 'app/core';
import { EnvironmentService } from './environment.service';

@Component({
    selector: 'jhi-environment',
    templateUrl: './environment.component.html'
})
export class EnvironmentComponent implements OnInit, OnDestroy {
    environments: IEnvironment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private environmentService: EnvironmentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.environmentService.query().subscribe(
            (res: HttpResponse<IEnvironment[]>) => {
                this.environments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEnvironments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEnvironment) {
        return item.id;
    }

    registerChangeInEnvironments() {
        this.eventSubscriber = this.eventManager.subscribe('environmentListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
