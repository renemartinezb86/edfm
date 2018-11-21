import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicationVersion } from 'app/shared/model/application-version.model';

@Component({
    selector: 'jhi-application-version-detail',
    templateUrl: './application-version-detail.component.html'
})
export class ApplicationVersionDetailComponent implements OnInit {
    applicationVersion: IApplicationVersion;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ applicationVersion }) => {
            this.applicationVersion = applicationVersion;
        });
    }

    previousState() {
        window.history.back();
    }
}
