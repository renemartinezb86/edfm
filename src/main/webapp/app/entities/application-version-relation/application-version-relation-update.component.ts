import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IApplicationVersionRelation } from 'app/shared/model/application-version-relation.model';
import { ApplicationVersionRelationService } from './application-version-relation.service';
import { IApplicationVersion } from 'app/shared/model/application-version.model';
import { ApplicationVersionService } from 'app/entities/application-version';

@Component({
    selector: 'jhi-application-version-relation-update',
    templateUrl: './application-version-relation-update.component.html'
})
export class ApplicationVersionRelationUpdateComponent implements OnInit {
    applicationVersionRelation: IApplicationVersionRelation;
    isSaving: boolean;

    applicationversions: IApplicationVersion[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private applicationVersionRelationService: ApplicationVersionRelationService,
        private applicationVersionService: ApplicationVersionService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ applicationVersionRelation }) => {
            this.applicationVersionRelation = applicationVersionRelation;
        });
        this.applicationVersionService.query().subscribe(
            (res: HttpResponse<IApplicationVersion[]>) => {
                this.applicationversions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.applicationVersionRelation.id !== undefined) {
            this.subscribeToSaveResponse(this.applicationVersionRelationService.update(this.applicationVersionRelation));
        } else {
            this.subscribeToSaveResponse(this.applicationVersionRelationService.create(this.applicationVersionRelation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IApplicationVersionRelation>>) {
        result.subscribe(
            (res: HttpResponse<IApplicationVersionRelation>) => this.onSaveSuccess(),
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackApplicationVersionById(index: number, item: IApplicationVersion) {
        return item.id;
    }
}
