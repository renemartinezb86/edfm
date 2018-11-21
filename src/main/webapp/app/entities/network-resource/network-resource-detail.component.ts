import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INetworkResource } from 'app/shared/model/network-resource.model';

@Component({
    selector: 'jhi-network-resource-detail',
    templateUrl: './network-resource-detail.component.html'
})
export class NetworkResourceDetailComponent implements OnInit {
    networkResource: INetworkResource;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ networkResource }) => {
            this.networkResource = networkResource;
        });
    }

    previousState() {
        window.history.back();
    }
}
