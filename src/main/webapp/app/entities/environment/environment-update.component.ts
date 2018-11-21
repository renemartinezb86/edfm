import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEnvironment } from 'app/shared/model/environment.model';
import { EnvironmentService } from './environment.service';
import { IEnvironmentType } from 'app/shared/model/environment-type.model';
import { EnvironmentTypeService } from 'app/entities/environment-type';

@Component({
    selector: 'jhi-environment-update',
    templateUrl: './environment-update.component.html'
})
export class EnvironmentUpdateComponent implements OnInit {
    environment: IEnvironment;
    isSaving: boolean;

    environmenttypes: IEnvironmentType[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private environmentService: EnvironmentService,
        private environmentTypeService: EnvironmentTypeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ environment }) => {
            this.environment = environment;
        });
        this.environmentTypeService.query().subscribe(
            (res: HttpResponse<IEnvironmentType[]>) => {
                this.environmenttypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.environment.id !== undefined) {
            this.subscribeToSaveResponse(this.environmentService.update(this.environment));
        } else {
            this.subscribeToSaveResponse(this.environmentService.create(this.environment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEnvironment>>) {
        result.subscribe((res: HttpResponse<IEnvironment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEnvironmentTypeById(index: number, item: IEnvironmentType) {
        return item.id;
    }
}
