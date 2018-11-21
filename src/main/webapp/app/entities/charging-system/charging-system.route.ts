import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ChargingSystem } from 'app/shared/model/charging-system.model';
import { ChargingSystemService } from './charging-system.service';
import { ChargingSystemComponent } from './charging-system.component';
import { ChargingSystemDetailComponent } from './charging-system-detail.component';
import { ChargingSystemUpdateComponent } from './charging-system-update.component';
import { ChargingSystemDeletePopupComponent } from './charging-system-delete-dialog.component';
import { IChargingSystem } from 'app/shared/model/charging-system.model';

@Injectable({ providedIn: 'root' })
export class ChargingSystemResolve implements Resolve<IChargingSystem> {
    constructor(private service: ChargingSystemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ChargingSystem> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ChargingSystem>) => response.ok),
                map((chargingSystem: HttpResponse<ChargingSystem>) => chargingSystem.body)
            );
        }
        return of(new ChargingSystem());
    }
}

export const chargingSystemRoute: Routes = [
    {
        path: 'charging-system',
        component: ChargingSystemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.chargingSystem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'charging-system/:id/view',
        component: ChargingSystemDetailComponent,
        resolve: {
            chargingSystem: ChargingSystemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.chargingSystem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'charging-system/new',
        component: ChargingSystemUpdateComponent,
        resolve: {
            chargingSystem: ChargingSystemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.chargingSystem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'charging-system/:id/edit',
        component: ChargingSystemUpdateComponent,
        resolve: {
            chargingSystem: ChargingSystemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.chargingSystem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chargingSystemPopupRoute: Routes = [
    {
        path: 'charging-system/:id/delete',
        component: ChargingSystemDeletePopupComponent,
        resolve: {
            chargingSystem: ChargingSystemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.chargingSystem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
