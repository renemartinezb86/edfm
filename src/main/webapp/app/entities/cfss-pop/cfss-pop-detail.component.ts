import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICfssPop } from 'app/shared/model/cfss-pop.model';

@Component({
    selector: 'jhi-cfss-pop-detail',
    templateUrl: './cfss-pop-detail.component.html'
})
export class CfssPopDetailComponent implements OnInit {
    cfssPop: ICfssPop;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cfssPop }) => {
            this.cfssPop = cfssPop;
        });
    }

    previousState() {
        window.history.back();
    }
}
