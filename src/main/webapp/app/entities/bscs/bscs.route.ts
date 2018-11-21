import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Bscs } from 'app/shared/model/bscs.model';
import { BscsService } from './bscs.service';
import { BscsComponent } from './bscs.component';
import { BscsDetailComponent } from './bscs-detail.component';
import { BscsUpdateComponent } from './bscs-update.component';
import { BscsDeletePopupComponent } from './bscs-delete-dialog.component';
import { IBscs } from 'app/shared/model/bscs.model';

@Injectable({ providedIn: 'root' })
export class BscsResolve implements Resolve<IBscs> {
    constructor(private service: BscsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Bscs> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Bscs>) => response.ok),
                map((bscs: HttpResponse<Bscs>) => bscs.body)
            );
        }
        return of(new Bscs());
    }
}

export const bscsRoute: Routes = [
    {
        path: 'bscs',
        component: BscsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.bscs.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bscs/:id/view',
        component: BscsDetailComponent,
        resolve: {
            bscs: BscsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.bscs.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bscs/new',
        component: BscsUpdateComponent,
        resolve: {
            bscs: BscsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.bscs.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bscs/:id/edit',
        component: BscsUpdateComponent,
        resolve: {
            bscs: BscsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.bscs.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bscsPopupRoute: Routes = [
    {
        path: 'bscs/:id/delete',
        component: BscsDeletePopupComponent,
        resolve: {
            bscs: BscsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.bscs.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
