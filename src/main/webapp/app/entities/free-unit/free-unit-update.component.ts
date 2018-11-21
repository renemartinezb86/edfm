import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFreeUnit } from 'app/shared/model/free-unit.model';
import { FreeUnitService } from './free-unit.service';
import { IChargingSystem } from 'app/shared/model/charging-system.model';
import { ChargingSystemService } from 'app/entities/charging-system';
import { IProductOffering } from 'app/shared/model/product-offering.model';
import { ProductOfferingService } from 'app/entities/product-offering';

@Component({
    selector: 'jhi-free-unit-update',
    templateUrl: './free-unit-update.component.html'
})
export class FreeUnitUpdateComponent implements OnInit {
    freeUnit: IFreeUnit;
    isSaving: boolean;

    chargingsystems: IChargingSystem[];

    productofferings: IProductOffering[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private freeUnitService: FreeUnitService,
        private chargingSystemService: ChargingSystemService,
        private productOfferingService: ProductOfferingService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ freeUnit }) => {
            this.freeUnit = freeUnit;
        });
        this.chargingSystemService.query().subscribe(
            (res: HttpResponse<IChargingSystem[]>) => {
                this.chargingsystems = res.body;
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
        if (this.freeUnit.id !== undefined) {
            this.subscribeToSaveResponse(this.freeUnitService.update(this.freeUnit));
        } else {
            this.subscribeToSaveResponse(this.freeUnitService.create(this.freeUnit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFreeUnit>>) {
        result.subscribe((res: HttpResponse<IFreeUnit>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
