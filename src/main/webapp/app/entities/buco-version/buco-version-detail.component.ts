import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBucoVersion } from 'app/shared/model/buco-version.model';

@Component({
    selector: 'jhi-buco-version-detail',
    templateUrl: './buco-version-detail.component.html'
})
export class BucoVersionDetailComponent implements OnInit {
    bucoVersion: IBucoVersion;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bucoVersion }) => {
            this.bucoVersion = bucoVersion;
        });
    }

    previousState() {
        window.history.back();
    }
}
