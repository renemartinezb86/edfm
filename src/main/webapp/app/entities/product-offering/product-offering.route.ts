import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductOffering } from 'app/shared/model/product-offering.model';
import { ProductOfferingService } from './product-offering.service';
import { ProductOfferingComponent } from './product-offering.component';
import { ProductOfferingDetailComponent } from './product-offering-detail.component';
import { ProductOfferingUpdateComponent } from './product-offering-update.component';
import { ProductOfferingDeletePopupComponent } from './product-offering-delete-dialog.component';
import { IProductOffering } from 'app/shared/model/product-offering.model';

@Injectable({ providedIn: 'root' })
export class ProductOfferingResolve implements Resolve<IProductOffering> {
    constructor(private service: ProductOfferingService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ProductOffering> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductOffering>) => response.ok),
                map((productOffering: HttpResponse<ProductOffering>) => productOffering.body)
            );
        }
        return of(new ProductOffering());
    }
}

export const productOfferingRoute: Routes = [
    {
        path: 'product-offering',
        component: ProductOfferingComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.productOffering.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'product-offering/:id/view',
        component: ProductOfferingDetailComponent,
        resolve: {
            productOffering: ProductOfferingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.productOffering.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'product-offering/new',
        component: ProductOfferingUpdateComponent,
        resolve: {
            productOffering: ProductOfferingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.productOffering.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'product-offering/:id/edit',
        component: ProductOfferingUpdateComponent,
        resolve: {
            productOffering: ProductOfferingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.productOffering.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productOfferingPopupRoute: Routes = [
    {
        path: 'product-offering/:id/delete',
        component: ProductOfferingDeletePopupComponent,
        resolve: {
            productOffering: ProductOfferingResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.productOffering.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
