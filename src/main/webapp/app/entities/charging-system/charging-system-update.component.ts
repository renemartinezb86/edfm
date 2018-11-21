import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IChargingSystem } from 'app/shared/model/charging-system.model';
import { ChargingSystemService } from './charging-system.service';
import { IService } from 'app/shared/model/service.model';
import { ServiceService } from 'app/entities/service';
import { IFreeUnit } from 'app/shared/model/free-unit.model';
import { FreeUnitService } from 'app/entities/free-unit';

@Component({
    selector: 'jhi-charging-system-update',
    templateUrl: './charging-system-update.component.html'
})
export class ChargingSystemUpdateComponent implements OnInit {
    chargingSystem: IChargingSystem;
    isSaving: boolean;

    services: IService[];

    freeunits: IFreeUnit[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private chargingSystemService: ChargingSystemService,
        private serviceService: ServiceService,
        private freeUnitService: FreeUnitService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ chargingSystem }) => {
            this.chargingSystem = chargingSystem;
        });
        this.serviceService.query({ 'chargingSystem(offerTemplate)Id.specified': 'false' }).subscribe(
            (res: HttpResponse<IService[]>) => {
                if (!this.chargingSystem.service || !this.chargingSystem.service.id) {
                    this.services = res.body;
                } else {
                    this.serviceService.find(this.chargingSystem.service.id).subscribe(
                        (subRes: HttpResponse<IService>) => {
                            this.services = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.freeUnitService.query({ 'chargingSystem(offerTemplate)Id.specified': 'false' }).subscribe(
            (res: HttpResponse<IFreeUnit[]>) => {
                if (!this.chargingSystem.freeUnit || !this.chargingSystem.freeUnit.id) {
                    this.freeunits = res.body;
                } else {
                    this.freeUnitService.find(this.chargingSystem.freeUnit.id).subscribe(
                        (subRes: HttpResponse<IFreeUnit>) => {
                            this.freeunits = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.chargingSystem.id !== undefined) {
            this.subscribeToSaveResponse(this.chargingSystemService.update(this.chargingSystem));
        } else {
            this.subscribeToSaveResponse(this.chargingSystemService.create(this.chargingSystem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IChargingSystem>>) {
        result.subscribe((res: HttpResponse<IChargingSystem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackServiceById(index: number, item: IService) {
        return item.id;
    }

    trackFreeUnitById(index: number, item: IFreeUnit) {
        return item.id;
    }
}
