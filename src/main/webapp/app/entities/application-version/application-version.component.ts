import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApplicationVersion } from 'app/shared/model/application-version.model';
import { Principal } from 'app/core';
import { ApplicationVersionService } from './application-version.service';

@Component({
    selector: 'jhi-application-version',
    templateUrl: './application-version.component.html'
})
export class ApplicationVersionComponent implements OnInit, OnDestroy {
    applicationVersions: IApplicationVersion[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private applicationVersionService: ApplicationVersionService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.applicationVersionService.query().subscribe(
            (res: HttpResponse<IApplicationVersion[]>) => {
                this.applicationVersions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInApplicationVersions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IApplicationVersion) {
        return item.id;
    }

    registerChangeInApplicationVersions() {
        this.eventSubscriber = this.eventManager.subscribe('applicationVersionListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
