import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEnvironmentType } from 'app/shared/model/environment-type.model';
import { Principal } from 'app/core';
import { EnvironmentTypeService } from './environment-type.service';

@Component({
    selector: 'jhi-environment-type',
    templateUrl: './environment-type.component.html'
})
export class EnvironmentTypeComponent implements OnInit, OnDestroy {
    environmentTypes: IEnvironmentType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private environmentTypeService: EnvironmentTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.environmentTypeService.query().subscribe(
            (res: HttpResponse<IEnvironmentType[]>) => {
                this.environmentTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEnvironmentTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEnvironmentType) {
        return item.id;
    }

    registerChangeInEnvironmentTypes() {
        this.eventSubscriber = this.eventManager.subscribe('environmentTypeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
