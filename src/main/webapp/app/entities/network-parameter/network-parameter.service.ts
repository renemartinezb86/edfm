import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INetworkParameter } from 'app/shared/model/network-parameter.model';

type EntityResponseType = HttpResponse<INetworkParameter>;
type EntityArrayResponseType = HttpResponse<INetworkParameter[]>;

@Injectable({ providedIn: 'root' })
export class NetworkParameterService {
    public resourceUrl = SERVER_API_URL + 'api/network-parameters';

    constructor(private http: HttpClient) {}

    create(networkParameter: INetworkParameter): Observable<EntityResponseType> {
        return this.http.post<INetworkParameter>(this.resourceUrl, networkParameter, { observe: 'response' });
    }

    update(networkParameter: INetworkParameter): Observable<EntityResponseType> {
        return this.http.put<INetworkParameter>(this.resourceUrl, networkParameter, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<INetworkParameter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<INetworkParameter[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
