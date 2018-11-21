import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BucoVersion } from 'app/shared/model/buco-version.model';
import { BucoVersionService } from './buco-version.service';
import { BucoVersionComponent } from './buco-version.component';
import { BucoVersionDetailComponent } from './buco-version-detail.component';
import { BucoVersionUpdateComponent } from './buco-version-update.component';
import { BucoVersionDeletePopupComponent } from './buco-version-delete-dialog.component';
import { IBucoVersion } from 'app/shared/model/buco-version.model';

@Injectable({ providedIn: 'root' })
export class BucoVersionResolve implements Resolve<IBucoVersion> {
    constructor(private service: BucoVersionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<BucoVersion> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BucoVersion>) => response.ok),
                map((bucoVersion: HttpResponse<BucoVersion>) => bucoVersion.body)
            );
        }
        return of(new BucoVersion());
    }
}

export const bucoVersionRoute: Routes = [
    {
        path: 'buco-version',
        component: BucoVersionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.bucoVersion.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'buco-version/:id/view',
        component: BucoVersionDetailComponent,
        resolve: {
            bucoVersion: BucoVersionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.bucoVersion.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'buco-version/new',
        component: BucoVersionUpdateComponent,
        resolve: {
            bucoVersion: BucoVersionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.bucoVersion.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'buco-version/:id/edit',
        component: BucoVersionUpdateComponent,
        resolve: {
            bucoVersion: BucoVersionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.bucoVersion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bucoVersionPopupRoute: Routes = [
    {
        path: 'buco-version/:id/delete',
        component: BucoVersionDeletePopupComponent,
        resolve: {
            bucoVersion: BucoVersionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.bucoVersion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
