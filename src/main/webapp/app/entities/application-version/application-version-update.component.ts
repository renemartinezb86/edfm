import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IApplicationVersion } from 'app/shared/model/application-version.model';
import { ApplicationVersionService } from './application-version.service';
import { IApplication } from 'app/shared/model/application.model';
import { ApplicationService } from 'app/entities/application';

@Component({
    selector: 'jhi-application-version-update',
    templateUrl: './application-version-update.component.html'
})
export class ApplicationVersionUpdateComponent implements OnInit {
    applicationVersion: IApplicationVersion;
    isSaving: boolean;

    applications: IApplication[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private applicationVersionService: ApplicationVersionService,
        private applicationService: ApplicationService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ applicationVersion }) => {
            this.applicationVersion = applicationVersion;
        });
        this.applicationService.query().subscribe(
            (res: HttpResponse<IApplication[]>) => {
                this.applications = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.applicationVersion.id !== undefined) {
            this.subscribeToSaveResponse(this.applicationVersionService.update(this.applicationVersion));
        } else {
            this.subscribeToSaveResponse(this.applicationVersionService.create(this.applicationVersion));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IApplicationVersion>>) {
        result.subscribe((res: HttpResponse<IApplicationVersion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
