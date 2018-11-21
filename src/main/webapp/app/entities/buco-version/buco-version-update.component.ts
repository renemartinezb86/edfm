import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBucoVersion } from 'app/shared/model/buco-version.model';
import { BucoVersionService } from './buco-version.service';

@Component({
    selector: 'jhi-buco-version-update',
    templateUrl: './buco-version-update.component.html'
})
export class BucoVersionUpdateComponent implements OnInit {
    bucoVersion: IBucoVersion;
    isSaving: boolean;
    createdDate: string;

    constructor(private bucoVersionService: BucoVersionService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bucoVersion }) => {
            this.bucoVersion = bucoVersion;
            this.createdDate = this.bucoVersion.createdDate != null ? this.bucoVersion.createdDate.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.bucoVersion.createdDate = this.createdDate != null ? moment(this.createdDate, DATE_TIME_FORMAT) : null;
        if (this.bucoVersion.id !== undefined) {
            this.subscribeToSaveResponse(this.bucoVersionService.update(this.bucoVersion));
        } else {
            this.subscribeToSaveResponse(this.bucoVersionService.create(this.bucoVersion));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBucoVersion>>) {
        result.subscribe((res: HttpResponse<IBucoVersion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
