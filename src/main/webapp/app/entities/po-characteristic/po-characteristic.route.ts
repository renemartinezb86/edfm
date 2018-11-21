import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { POCharacteristic } from 'app/shared/model/po-characteristic.model';
import { POCharacteristicService } from './po-characteristic.service';
import { POCharacteristicComponent } from './po-characteristic.component';
import { POCharacteristicDetailComponent } from './po-characteristic-detail.component';
import { POCharacteristicUpdateComponent } from './po-characteristic-update.component';
import { POCharacteristicDeletePopupComponent } from './po-characteristic-delete-dialog.component';
import { IPOCharacteristic } from 'app/shared/model/po-characteristic.model';

@Injectable({ providedIn: 'root' })
export class POCharacteristicResolve implements Resolve<IPOCharacteristic> {
    constructor(private service: POCharacteristicService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<POCharacteristic> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<POCharacteristic>) => response.ok),
                map((pOCharacteristic: HttpResponse<POCharacteristic>) => pOCharacteristic.body)
            );
        }
        return of(new POCharacteristic());
    }
}

export const pOCharacteristicRoute: Routes = [
    {
        path: 'po-characteristic',
        component: POCharacteristicComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.pOCharacteristic.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'po-characteristic/:id/view',
        component: POCharacteristicDetailComponent,
        resolve: {
            pOCharacteristic: POCharacteristicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.pOCharacteristic.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'po-characteristic/new',
        component: POCharacteristicUpdateComponent,
        resolve: {
            pOCharacteristic: POCharacteristicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.pOCharacteristic.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'po-characteristic/:id/edit',
        component: POCharacteristicUpdateComponent,
        resolve: {
            pOCharacteristic: POCharacteristicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.pOCharacteristic.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pOCharacteristicPopupRoute: Routes = [
    {
        path: 'po-characteristic/:id/delete',
        component: POCharacteristicDeletePopupComponent,
        resolve: {
            pOCharacteristic: POCharacteristicResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.pOCharacteristic.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
