import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IProductOffering } from 'app/shared/model/product-offering.model';
import { ProductOfferingService } from './product-offering.service';
import { IPoPrice } from 'app/shared/model/po-price.model';
import { PoPriceService } from 'app/entities/po-price';
import { IBucoSheet } from 'app/shared/model/buco-sheet.model';
import { BucoSheetService } from 'app/entities/buco-sheet';
import { IRule } from 'app/shared/model/rule.model';
import { RuleService } from 'app/entities/rule';
import { IResource } from 'app/shared/model/resource.model';
import { ResourceService } from 'app/entities/resource';
import { INetworkResource } from 'app/shared/model/network-resource.model';
import { NetworkResourceService } from 'app/entities/network-resource';
import { IService } from 'app/shared/model/service.model';
import { ServiceService } from 'app/entities/service';
import { ICfssPop } from 'app/shared/model/cfss-pop.model';
import { CfssPopService } from 'app/entities/cfss-pop';
import { IFreeUnit } from 'app/shared/model/free-unit.model';
import { FreeUnitService } from 'app/entities/free-unit';

@Component({
    selector: 'jhi-product-offering-update',
    templateUrl: './product-offering-update.component.html'
})
export class ProductOfferingUpdateComponent implements OnInit {
    productOffering: IProductOffering;
    isSaving: boolean;

    poprices: IPoPrice[];

    bucosheets: IBucoSheet[];

    rules: IRule[];

    resources: IResource[];

    networkresources: INetworkResource[];

    services: IService[];

    cfsspops: ICfssPop[];

    freeunits: IFreeUnit[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private productOfferingService: ProductOfferingService,
        private poPriceService: PoPriceService,
        private bucoSheetService: BucoSheetService,
        private ruleService: RuleService,
        private resourceService: ResourceService,
        private networkResourceService: NetworkResourceService,
        private serviceService: ServiceService,
        private cfssPopService: CfssPopService,
        private freeUnitService: FreeUnitService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productOffering }) => {
            this.productOffering = productOffering;
        });
        this.poPriceService.query({ 'productOffering(poId)Id.specified': 'false' }).subscribe(
            (res: HttpResponse<IPoPrice[]>) => {
                if (!this.productOffering.poPrice || !this.productOffering.poPrice.id) {
                    this.poprices = res.body;
                } else {
                    this.poPriceService.find(this.productOffering.poPrice.id).subscribe(
                        (subRes: HttpResponse<IPoPrice>) => {
                            this.poprices = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.bucoSheetService.query().subscribe(
            (res: HttpResponse<IBucoSheet[]>) => {
                this.bucosheets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.ruleService.query().subscribe(
            (res: HttpResponse<IRule[]>) => {
                this.rules = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.resourceService.query().subscribe(
            (res: HttpResponse<IResource[]>) => {
                this.resources = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.networkResourceService.query().subscribe(
            (res: HttpResponse<INetworkResource[]>) => {
                this.networkresources = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.serviceService.query().subscribe(
            (res: HttpResponse<IService[]>) => {
                this.services = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.cfssPopService.query().subscribe(
            (res: HttpResponse<ICfssPop[]>) => {
                this.cfsspops = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.freeUnitService.query().subscribe(
            (res: HttpResponse<IFreeUnit[]>) => {
                this.freeunits = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productOffering.id !== undefined) {
            this.subscribeToSaveResponse(this.productOfferingService.update(this.productOffering));
        } else {
            this.subscribeToSaveResponse(this.productOfferingService.create(this.productOffering));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProductOffering>>) {
        result.subscribe((res: HttpResponse<IProductOffering>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPoPriceById(index: number, item: IPoPrice) {
        return item.id;
    }

    trackBucoSheetById(index: number, item: IBucoSheet) {
        return item.id;
    }

    trackRuleById(index: number, item: IRule) {
        return item.id;
    }

    trackResourceById(index: number, item: IResource) {
        return item.id;
    }

    trackNetworkResourceById(index: number, item: INetworkResource) {
        return item.id;
    }

    trackServiceById(index: number, item: IService) {
        return item.id;
    }

    trackCfssPopById(index: number, item: ICfssPop) {
        return item.id;
    }

    trackFreeUnitById(index: number, item: IFreeUnit) {
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
