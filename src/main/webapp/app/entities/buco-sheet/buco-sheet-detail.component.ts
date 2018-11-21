import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBucoSheet } from 'app/shared/model/buco-sheet.model';

@Component({
    selector: 'jhi-buco-sheet-detail',
    templateUrl: './buco-sheet-detail.component.html'
})
export class BucoSheetDetailComponent implements OnInit {
    bucoSheet: IBucoSheet;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bucoSheet }) => {
            this.bucoSheet = bucoSheet;
        });
    }

    previousState() {
        window.history.back();
    }
}
