import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPOCharacteristic } from 'app/shared/model/po-characteristic.model';
import { POCharacteristicService } from './po-characteristic.service';
import { IProductOffering } from 'app/shared/model/product-offering.model';
import { ProductOfferingService } from 'app/entities/product-offering';

@Component({
    selector: 'jhi-po-characteristic-update',
    templateUrl: './po-characteristic-update.component.html'
})
export class POCharacteristicUpdateComponent implements OnInit {
    pOCharacteristic: IPOCharacteristic;
    isSaving: boolean;

    productofferings: IProductOffering[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private pOCharacteristicService: POCharacteristicService,
        private productOfferingService: ProductOfferingService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pOCharacteristic }) => {
            this.pOCharacteristic = pOCharacteristic;
        });
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
        if (this.pOCharacteristic.id !== undefined) {
            this.subscribeToSaveResponse(this.pOCharacteristicService.update(this.pOCharacteristic));
        } else {
            this.subscribeToSaveResponse(this.pOCharacteristicService.create(this.pOCharacteristic));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPOCharacteristic>>) {
        result.subscribe((res: HttpResponse<IPOCharacteristic>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProductOfferingById(index: number, item: IProductOffering) {
        return item.id;
    }
}
