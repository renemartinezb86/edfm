import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBucoSheet } from 'app/shared/model/buco-sheet.model';
import { BucoSheetService } from './buco-sheet.service';
import { IBucoVersion } from 'app/shared/model/buco-version.model';
import { BucoVersionService } from 'app/entities/buco-version';

@Component({
    selector: 'jhi-buco-sheet-update',
    templateUrl: './buco-sheet-update.component.html'
})
export class BucoSheetUpdateComponent implements OnInit {
    bucoSheet: IBucoSheet;
    isSaving: boolean;

    bucoversions: IBucoVersion[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private bucoSheetService: BucoSheetService,
        private bucoVersionService: BucoVersionService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bucoSheet }) => {
            this.bucoSheet = bucoSheet;
        });
        this.bucoVersionService.query().subscribe(
            (res: HttpResponse<IBucoVersion[]>) => {
                this.bucoversions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bucoSheet.id !== undefined) {
            this.subscribeToSaveResponse(this.bucoSheetService.update(this.bucoSheet));
        } else {
            this.subscribeToSaveResponse(this.bucoSheetService.create(this.bucoSheet));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBucoSheet>>) {
        result.subscribe((res: HttpResponse<IBucoSheet>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBucoVersionById(index: number, item: IBucoVersion) {
        return item.id;
    }
}
