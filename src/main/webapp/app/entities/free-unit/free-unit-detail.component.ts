import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFreeUnit } from 'app/shared/model/free-unit.model';

@Component({
    selector: 'jhi-free-unit-detail',
    templateUrl: './free-unit-detail.component.html'
})
export class FreeUnitDetailComponent implements OnInit {
    freeUnit: IFreeUnit;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ freeUnit }) => {
            this.freeUnit = freeUnit;
        });
    }

    previousState() {
        window.history.back();
    }
}
