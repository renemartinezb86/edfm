import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INetworkResource } from 'app/shared/model/network-resource.model';

type EntityResponseType = HttpResponse<INetworkResource>;
type EntityArrayResponseType = HttpResponse<INetworkResource[]>;

@Injectable({ providedIn: 'root' })
export class NetworkResourceService {
    public resourceUrl = SERVER_API_URL + 'api/network-resources';

    constructor(private http: HttpClient) {}

    create(networkResource: INetworkResource): Observable<EntityResponseType> {
        return this.http.post<INetworkResource>(this.resourceUrl, networkResource, { observe: 'response' });
    }

    update(networkResource: INetworkResource): Observable<EntityResponseType> {
        return this.http.put<INetworkResource>(this.resourceUrl, networkResource, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<INetworkResource>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<INetworkResource[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
