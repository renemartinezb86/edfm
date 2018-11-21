import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FreeUnit } from 'app/shared/model/free-unit.model';
import { FreeUnitService } from './free-unit.service';
import { FreeUnitComponent } from './free-unit.component';
import { FreeUnitDetailComponent } from './free-unit-detail.component';
import { FreeUnitUpdateComponent } from './free-unit-update.component';
import { FreeUnitDeletePopupComponent } from './free-unit-delete-dialog.component';
import { IFreeUnit } from 'app/shared/model/free-unit.model';

@Injectable({ providedIn: 'root' })
export class FreeUnitResolve implements Resolve<IFreeUnit> {
    constructor(private service: FreeUnitService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<FreeUnit> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<FreeUnit>) => response.ok),
                map((freeUnit: HttpResponse<FreeUnit>) => freeUnit.body)
            );
        }
        return of(new FreeUnit());
    }
}

export const freeUnitRoute: Routes = [
    {
        path: 'free-unit',
        component: FreeUnitComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.freeUnit.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'free-unit/:id/view',
        component: FreeUnitDetailComponent,
        resolve: {
            freeUnit: FreeUnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.freeUnit.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'free-unit/new',
        component: FreeUnitUpdateComponent,
        resolve: {
            freeUnit: FreeUnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.freeUnit.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'free-unit/:id/edit',
        component: FreeUnitUpdateComponent,
        resolve: {
            freeUnit: FreeUnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.freeUnit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const freeUnitPopupRoute: Routes = [
    {
        path: 'free-unit/:id/delete',
        component: FreeUnitDeletePopupComponent,
        resolve: {
            freeUnit: FreeUnitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.freeUnit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
