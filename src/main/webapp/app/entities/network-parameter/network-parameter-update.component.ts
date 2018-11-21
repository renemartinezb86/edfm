import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { INetworkParameter } from 'app/shared/model/network-parameter.model';
import { NetworkParameterService } from './network-parameter.service';
import { INetworkResource } from 'app/shared/model/network-resource.model';
import { NetworkResourceService } from 'app/entities/network-resource';

@Component({
    selector: 'jhi-network-parameter-update',
    templateUrl: './network-parameter-update.component.html'
})
export class NetworkParameterUpdateComponent implements OnInit {
    networkParameter: INetworkParameter;
    isSaving: boolean;

    networkresources: INetworkResource[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private networkParameterService: NetworkParameterService,
        private networkResourceService: NetworkResourceService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ networkParameter }) => {
            this.networkParameter = networkParameter;
        });
        this.networkResourceService.query().subscribe(
            (res: HttpResponse<INetworkResource[]>) => {
                this.networkresources = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.networkParameter.id !== undefined) {
            this.subscribeToSaveResponse(this.networkParameterService.update(this.networkParameter));
        } else {
            this.subscribeToSaveResponse(this.networkParameterService.create(this.networkParameter));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<INetworkParameter>>) {
        result.subscribe((res: HttpResponse<INetworkParameter>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackNetworkResourceById(index: number, item: INetworkResource) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
