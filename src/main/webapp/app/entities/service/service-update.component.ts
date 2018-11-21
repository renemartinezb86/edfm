import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IService } from 'app/shared/model/service.model';
import { ServiceService } from './service.service';
import { IChargingSystem } from 'app/shared/model/charging-system.model';
import { ChargingSystemService } from 'app/entities/charging-system';
import { IBscs } from 'app/shared/model/bscs.model';
import { BscsService } from 'app/entities/bscs';
import { IProductOffering } from 'app/shared/model/product-offering.model';
import { ProductOfferingService } from 'app/entities/product-offering';

@Component({
    selector: 'jhi-service-update',
    templateUrl: './service-update.component.html'
})
export class ServiceUpdateComponent implements OnInit {
    service: IService;
    isSaving: boolean;

    chargingsystems: IChargingSystem[];

    bscs: IBscs[];

    productofferings: IProductOffering[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private serviceService: ServiceService,
        private chargingSystemService: ChargingSystemService,
        private bscsService: BscsService,
        private productOfferingService: ProductOfferingService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ service }) => {
            this.service = service;
        });
        this.chargingSystemService.query().subscribe(
            (res: HttpResponse<IChargingSystem[]>) => {
                this.chargingsystems = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.bscsService.query().subscribe(
            (res: HttpResponse<IBscs[]>) => {
                this.bscs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.productOfferingService.query().subscribe(
            (res: HttpResponse<IProductOffering[]>) => {
                this.productofferings = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.service.id !== undefined) {
            this.subscribeToSaveResponse(this.serviceService.update(this.service));
        } else {
            this.subscribeToSaveResponse(this.serviceService.create(this.service));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IService>>) {
        result.subscribe((res: HttpResponse<IService>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackChargingSystemById(index: number, item: IChargingSystem) {
        return item.id;
    }

    trackBscsById(index: number, item: IBscs) {
        return item.id;
    }

    trackProductOfferingById(index: number, item: IProductOffering) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
