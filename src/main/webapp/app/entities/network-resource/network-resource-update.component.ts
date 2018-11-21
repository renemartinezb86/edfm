import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { INetworkResource } from 'app/shared/model/network-resource.model';
import { NetworkResourceService } from './network-resource.service';
import { INetworkParameter } from 'app/shared/model/network-parameter.model';
import { NetworkParameterService } from 'app/entities/network-parameter';
import { IProductOffering } from 'app/shared/model/product-offering.model';
import { ProductOfferingService } from 'app/entities/product-offering';

@Component({
    selector: 'jhi-network-resource-update',
    templateUrl: './network-resource-update.component.html'
})
export class NetworkResourceUpdateComponent implements OnInit {
    networkResource: INetworkResource;
    isSaving: boolean;

    networkparameters: INetworkParameter[];

    productofferings: IProductOffering[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private networkResourceService: NetworkResourceService,
        private networkParameterService: NetworkParameterService,
        private productOfferingService: ProductOfferingService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ networkResource }) => {
            this.networkResource = networkResource;
        });
        this.networkParameterService.query().subscribe(
            (res: HttpResponse<INetworkParameter[]>) => {
                this.networkparameters = res.body;
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
        if (this.networkResource.id !== undefined) {
            this.subscribeToSaveResponse(this.networkResourceService.update(this.networkResource));
        } else {
            this.subscribeToSaveResponse(this.networkResourceService.create(this.networkResource));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<INetworkResource>>) {
        result.subscribe((res: HttpResponse<INetworkResource>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackNetworkParameterById(index: number, item: INetworkParameter) {
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
