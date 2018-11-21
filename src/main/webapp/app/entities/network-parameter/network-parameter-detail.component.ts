import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INetworkParameter } from 'app/shared/model/network-parameter.model';

@Component({
    selector: 'jhi-network-parameter-detail',
    templateUrl: './network-parameter-detail.component.html'
})
export class NetworkParameterDetailComponent implements OnInit {
    networkParameter: INetworkParameter;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ networkParameter }) => {
            this.networkParameter = networkParameter;
        });
    }

    previousState() {
        window.history.back();
    }
}
