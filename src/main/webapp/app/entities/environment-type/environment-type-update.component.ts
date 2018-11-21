import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IEnvironmentType } from 'app/shared/model/environment-type.model';
import { EnvironmentTypeService } from './environment-type.service';

@Component({
    selector: 'jhi-environment-type-update',
    templateUrl: './environment-type-update.component.html'
})
export class EnvironmentTypeUpdateComponent implements OnInit {
    environmentType: IEnvironmentType;
    isSaving: boolean;

    constructor(private environmentTypeService: EnvironmentTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ environmentType }) => {
            this.environmentType = environmentType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.environmentType.id !== undefined) {
            this.subscribeToSaveResponse(this.environmentTypeService.update(this.environmentType));
        } else {
            this.subscribeToSaveResponse(this.environmentTypeService.create(this.environmentType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEnvironmentType>>) {
        result.subscribe((res: HttpResponse<IEnvironmentType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
