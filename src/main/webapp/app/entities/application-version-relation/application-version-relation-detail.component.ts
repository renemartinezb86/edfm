import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicationVersionRelation } from 'app/shared/model/application-version-relation.model';

@Component({
    selector: 'jhi-application-version-relation-detail',
    templateUrl: './application-version-relation-detail.component.html'
})
export class ApplicationVersionRelationDetailComponent implements OnInit {
    applicationVersionRelation: IApplicationVersionRelation;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ applicationVersionRelation }) => {
            this.applicationVersionRelation = applicationVersionRelation;
        });
    }

    previousState() {
        window.history.back();
    }
}
