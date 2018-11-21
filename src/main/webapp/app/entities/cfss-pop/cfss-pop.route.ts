import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CfssPop } from 'app/shared/model/cfss-pop.model';
import { CfssPopService } from './cfss-pop.service';
import { CfssPopComponent } from './cfss-pop.component';
import { CfssPopDetailComponent } from './cfss-pop-detail.component';
import { CfssPopUpdateComponent } from './cfss-pop-update.component';
import { CfssPopDeletePopupComponent } from './cfss-pop-delete-dialog.component';
import { ICfssPop } from 'app/shared/model/cfss-pop.model';

@Injectable({ providedIn: 'root' })
export class CfssPopResolve implements Resolve<ICfssPop> {
    constructor(private service: CfssPopService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CfssPop> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CfssPop>) => response.ok),
                map((cfssPop: HttpResponse<CfssPop>) => cfssPop.body)
            );
        }
        return of(new CfssPop());
    }
}

export const cfssPopRoute: Routes = [
    {
        path: 'cfss-pop',
        component: CfssPopComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.cfssPop.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cfss-pop/:id/view',
        component: CfssPopDetailComponent,
        resolve: {
            cfssPop: CfssPopResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.cfssPop.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cfss-pop/new',
        component: CfssPopUpdateComponent,
        resolve: {
            cfssPop: CfssPopResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.cfssPop.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cfss-pop/:id/edit',
        component: CfssPopUpdateComponent,
        resolve: {
            cfssPop: CfssPopResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.cfssPop.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cfssPopPopupRoute: Routes = [
    {
        path: 'cfss-pop/:id/delete',
        component: CfssPopDeletePopupComponent,
        resolve: {
            cfssPop: CfssPopResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.cfssPop.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
