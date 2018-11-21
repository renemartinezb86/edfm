import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBscs } from 'app/shared/model/bscs.model';

@Component({
    selector: 'jhi-bscs-detail',
    templateUrl: './bscs-detail.component.html'
})
export class BscsDetailComponent implements OnInit {
    bscs: IBscs;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bscs }) => {
            this.bscs = bscs;
        });
    }

    previousState() {
        window.history.back();
    }
}
