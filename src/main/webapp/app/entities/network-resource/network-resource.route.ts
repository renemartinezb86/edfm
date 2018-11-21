import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NetworkResource } from 'app/shared/model/network-resource.model';
import { NetworkResourceService } from './network-resource.service';
import { NetworkResourceComponent } from './network-resource.component';
import { NetworkResourceDetailComponent } from './network-resource-detail.component';
import { NetworkResourceUpdateComponent } from './network-resource-update.component';
import { NetworkResourceDeletePopupComponent } from './network-resource-delete-dialog.component';
import { INetworkResource } from 'app/shared/model/network-resource.model';

@Injectable({ providedIn: 'root' })
export class NetworkResourceResolve implements Resolve<INetworkResource> {
    constructor(private service: NetworkResourceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<NetworkResource> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<NetworkResource>) => response.ok),
                map((networkResource: HttpResponse<NetworkResource>) => networkResource.body)
            );
        }
        return of(new NetworkResource());
    }
}

export const networkResourceRoute: Routes = [
    {
        path: 'network-resource',
        component: NetworkResourceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.networkResource.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'network-resource/:id/view',
        component: NetworkResourceDetailComponent,
        resolve: {
            networkResource: NetworkResourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.networkResource.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'network-resource/new',
        component: NetworkResourceUpdateComponent,
        resolve: {
            networkResource: NetworkResourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.networkResource.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'network-resource/:id/edit',
        component: NetworkResourceUpdateComponent,
        resolve: {
            networkResource: NetworkResourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.networkResource.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const networkResourcePopupRoute: Routes = [
    {
        path: 'network-resource/:id/delete',
        component: NetworkResourceDeletePopupComponent,
        resolve: {
            networkResource: NetworkResourceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'edfmApp.networkResource.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
