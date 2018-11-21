import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPoPrice } from 'app/shared/model/po-price.model';
import { PoPriceService } from './po-price.service';
import { IProductOffering } from 'app/shared/model/product-offering.model';
import { ProductOfferingService } from 'app/entities/product-offering';

@Component({
    selector: 'jhi-po-price-update',
    templateUrl: './po-price-update.component.html'
})
export class PoPriceUpdateComponent implements OnInit {
    poPrice: IPoPrice;
    isSaving: boolean;

    productofferings: IProductOffering[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private poPriceService: PoPriceService,
        private productOfferingService: ProductOfferingService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ poPrice }) => {
            this.poPrice = poPrice;
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
        if (this.poPrice.id !== undefined) {
            this.subscribeToSaveResponse(this.poPriceService.update(this.poPrice));
        } else {
            this.subscribeToSaveResponse(this.poPriceService.create(this.poPrice));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPoPrice>>) {
        result.subscribe((res: HttpResponse<IPoPrice>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
