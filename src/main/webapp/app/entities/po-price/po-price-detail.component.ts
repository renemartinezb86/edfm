import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPoPrice } from 'app/shared/model/po-price.model';

@Component({
    selector: 'jhi-po-price-detail',
    templateUrl: './po-price-detail.component.html'
})
export class PoPriceDetailComponent implements OnInit {
    poPrice: IPoPrice;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ poPrice }) => {
            this.poPrice = poPrice;
        });
    }

    previousState() {
        window.history.back();
    }
}
