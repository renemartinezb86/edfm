import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PoPrice } from 'app/shared/model/po-price.model';
import { PoPriceService } from './po-price.service';
import { PoPriceComponent } from './po-price.component';
import { PoPriceDetailComponent } from './po-price-detail.component';
import { PoPriceUpdateComponent } from './po-price-update.component';
import { PoPriceDeletePopupComponent } from './po-price-delete-dialog.component';
import { IPoPrice } from 'app/shared/model/po-price.model';

@Injectable({ providedIn: 'root' })
export class PoPriceResolve implements Resolve<IPoPrice> {
    constructor(private service: PoPriceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<PoPrice> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PoPrice>) => response.ok),
                map((poPrice: HttpResponse<PoPrice>) => poPrice.body)
            );
        }
        return of(new PoPrice());
    }
}

export const poPriceRoute: Routes = [
    {
        path: 'po-price',
        component: PoPriceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.poPrice.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'po-price/:id/view',
        component: PoPriceDetailComponent,
        resolve: {
            poPrice: PoPriceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.poPrice.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'po-price/new',
        component: PoPriceUpdateComponent,
        resolve: {
            poPrice: PoPriceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.poPrice.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'po-price/:id/edit',
        component: PoPriceUpdateComponent,
        resolve: {
            poPrice: PoPriceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.poPrice.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const poPricePopupRoute: Routes = [
    {
        path: 'po-price/:id/delete',
        component: PoPriceDeletePopupComponent,
        resolve: {
            poPrice: PoPriceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.poPrice.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
