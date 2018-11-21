import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRule } from 'app/shared/model/rule.model';
import { RuleService } from './rule.service';
import { IProductOffering } from 'app/shared/model/product-offering.model';
import { ProductOfferingService } from 'app/entities/product-offering';

@Component({
    selector: 'jhi-rule-update',
    templateUrl: './rule-update.component.html'
})
export class RuleUpdateComponent implements OnInit {
    rule: IRule;
    isSaving: boolean;

    productofferings: IProductOffering[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private ruleService: RuleService,
        private productOfferingService: ProductOfferingService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rule }) => {
            this.rule = rule;
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
        if (this.rule.id !== undefined) {
            this.subscribeToSaveResponse(this.ruleService.update(this.rule));
        } else {
            this.subscribeToSaveResponse(this.ruleService.create(this.rule));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRule>>) {
        result.subscribe((res: HttpResponse<IRule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
