import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnvironmentType } from 'app/shared/model/environment-type.model';

@Component({
    selector: 'jhi-environment-type-detail',
    templateUrl: './environment-type-detail.component.html'
})
export class EnvironmentTypeDetailComponent implements OnInit {
    environmentType: IEnvironmentType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ environmentType }) => {
            this.environmentType = environmentType;
        });
    }

    previousState() {
        window.history.back();
    }
}
