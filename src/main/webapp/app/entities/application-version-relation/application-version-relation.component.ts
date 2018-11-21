import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IApplicationVersionRelation } from 'app/shared/model/application-version-relation.model';
import { Principal } from 'app/core';
import { ApplicationVersionRelationService } from './application-version-relation.service';

@Component({
    selector: 'jhi-application-version-relation',
    templateUrl: './application-version-relation.component.html'
})
export class ApplicationVersionRelationComponent implements OnInit, OnDestroy {
    applicationVersionRelations: IApplicationVersionRelation[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private applicationVersionRelationService: ApplicationVersionRelationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.applicationVersionRelationService.query().subscribe(
            (res: HttpResponse<IApplicationVersionRelation[]>) => {
                this.applicationVersionRelations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInApplicationVersionRelations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IApplicationVersionRelation) {
        return item.id;
    }

    registerChangeInApplicationVersionRelations() {
        this.eventSubscriber = this.eventManager.subscribe('applicationVersionRelationListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
