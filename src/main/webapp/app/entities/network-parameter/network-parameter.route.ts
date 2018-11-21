import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NetworkParameter } from 'app/shared/model/network-parameter.model';
import { NetworkParameterService } from './network-parameter.service';
import { NetworkParameterComponent } from './network-parameter.component';
import { NetworkParameterDetailComponent } from './network-parameter-detail.component';
import { NetworkParameterUpdateComponent } from './network-parameter-update.component';
import { NetworkParameterDeletePopupComponent } from './network-parameter-delete-dialog.component';
import { INetworkParameter } from 'app/shared/model/network-parameter.model';

@Injectable({ providedIn: 'root' })
export class NetworkParameterResolve implements Resolve<INetworkParameter> {
    constructor(private service: NetworkParameterService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<NetworkParameter> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<NetworkParameter>) => response.ok),
                map((networkParameter: HttpResponse<NetworkParameter>) => networkParameter.body)
            );
        }
        return of(new NetworkParameter());
    }
}

export const networkParameterRoute: Routes = [
    {
        path: 'network-parameter',
        component: NetworkParameterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.networkParameter.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'network-parameter/:id/view',
        component: NetworkParameterDetailComponent,
        resolve: {
            networkParameter: NetworkParameterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.networkParameter.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'network-parameter/new',
        component: NetworkParameterUpdateComponent,
        resolve: {
            networkParameter: NetworkParameterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.networkParameter.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'network-parameter/:id/edit',
        component: NetworkParameterUpdateComponent,
        resolve: {
            networkParameter: NetworkParameterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.networkParameter.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const networkParameterPopupRoute: Routes = [
    {
        path: 'network-parameter/:id/delete',
        component: NetworkParameterDeletePopupComponent,
        resolve: {
            networkParameter: NetworkParameterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.networkParameter.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
