import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductOffering } from 'app/shared/model/product-offering.model';

@Component({
    selector: 'jhi-product-offering-detail',
    templateUrl: './product-offering-detail.component.html'
})
export class ProductOfferingDetailComponent implements OnInit {
    productOffering: IProductOffering;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productOffering }) => {
            this.productOffering = productOffering;
        });
    }

    previousState() {
        window.history.back();
    }
}
