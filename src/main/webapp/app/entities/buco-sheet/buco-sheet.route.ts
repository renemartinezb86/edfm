import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BucoSheet } from 'app/shared/model/buco-sheet.model';
import { BucoSheetService } from './buco-sheet.service';
import { BucoSheetComponent } from './buco-sheet.component';
import { BucoSheetDetailComponent } from './buco-sheet-detail.component';
import { BucoSheetUpdateComponent } from './buco-sheet-update.component';
import { BucoSheetDeletePopupComponent } from './buco-sheet-delete-dialog.component';
import { IBucoSheet } from 'app/shared/model/buco-sheet.model';

@Injectable({ providedIn: 'root' })
export class BucoSheetResolve implements Resolve<IBucoSheet> {
    constructor(private service: BucoSheetService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<BucoSheet> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BucoSheet>) => response.ok),
                map((bucoSheet: HttpResponse<BucoSheet>) => bucoSheet.body)
            );
        }
        return of(new BucoSheet());
    }
}

export const bucoSheetRoute: Routes = [
    {
        path: 'buco-sheet',
        component: BucoSheetComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.bucoSheet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'buco-sheet/:id/view',
        component: BucoSheetDetailComponent,
        resolve: {
            bucoSheet: BucoSheetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.bucoSheet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'buco-sheet/new',
        component: BucoSheetUpdateComponent,
        resolve: {
            bucoSheet: BucoSheetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.bucoSheet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'buco-sheet/:id/edit',
        component: BucoSheetUpdateComponent,
        resolve: {
            bucoSheet: BucoSheetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.bucoSheet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bucoSheetPopupRoute: Routes = [
    {
        path: 'buco-sheet/:id/delete',
        component: BucoSheetDeletePopupComponent,
        resolve: {
            bucoSheet: BucoSheetResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.bucoSheet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
