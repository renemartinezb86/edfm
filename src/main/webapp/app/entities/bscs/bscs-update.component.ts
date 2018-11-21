import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBscs } from 'app/shared/model/bscs.model';
import { BscsService } from './bscs.service';
import { IService } from 'app/shared/model/service.model';
import { ServiceService } from 'app/entities/service';

@Component({
    selector: 'jhi-bscs-update',
    templateUrl: './bscs-update.component.html'
})
export class BscsUpdateComponent implements OnInit {
    bscs: IBscs;
    isSaving: boolean;

    services: IService[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private bscsService: BscsService,
        private serviceService: ServiceService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bscs }) => {
            this.bscs = bscs;
        });
        this.serviceService.query({ 'bscs(services)Id.specified': 'false' }).subscribe(
            (res: HttpResponse<IService[]>) => {
                if (!this.bscs.service || !this.bscs.service.id) {
                    this.services = res.body;
                } else {
                    this.serviceService.find(this.bscs.service.id).subscribe(
                        (subRes: HttpResponse<IService>) => {
                            this.services = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bscs.id !== undefined) {
            this.subscribeToSaveResponse(this.bscsService.update(this.bscs));
        } else {
            this.subscribeToSaveResponse(this.bscsService.create(this.bscs));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBscs>>) {
        result.subscribe((res: HttpResponse<IBscs>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackServiceById(index: number, item: IService) {
        return item.id;
    }
}
