import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICfssPop } from 'app/shared/model/cfss-pop.model';
import { CfssPopService } from './cfss-pop.service';
import { IProductOffering } from 'app/shared/model/product-offering.model';
import { ProductOfferingService } from 'app/entities/product-offering';

@Component({
    selector: 'jhi-cfss-pop-update',
    templateUrl: './cfss-pop-update.component.html'
})
export class CfssPopUpdateComponent implements OnInit {
    cfssPop: ICfssPop;
    isSaving: boolean;

    productofferings: IProductOffering[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private cfssPopService: CfssPopService,
        private productOfferingService: ProductOfferingService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cfssPop }) => {
            this.cfssPop = cfssPop;
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
        if (this.cfssPop.id !== undefined) {
            this.subscribeToSaveResponse(this.cfssPopService.update(this.cfssPop));
        } else {
            this.subscribeToSaveResponse(this.cfssPopService.create(this.cfssPop));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICfssPop>>) {
        result.subscribe((res: HttpResponse<ICfssPop>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
