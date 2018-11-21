import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPOCharacteristic } from 'app/shared/model/po-characteristic.model';

@Component({
    selector: 'jhi-po-characteristic-detail',
    templateUrl: './po-characteristic-detail.component.html'
})
export class POCharacteristicDetailComponent implements OnInit {
    pOCharacteristic: IPOCharacteristic;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pOCharacteristic }) => {
            this.pOCharacteristic = pOCharacteristic;
        });
    }

    previousState() {
        window.history.back();
    }
}
