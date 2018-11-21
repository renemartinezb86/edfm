import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPOCharacteristic } from 'app/shared/model/po-characteristic.model';

type EntityResponseType = HttpResponse<IPOCharacteristic>;
type EntityArrayResponseType = HttpResponse<IPOCharacteristic[]>;

@Injectable({ providedIn: 'root' })
export class POCharacteristicService {
    public resourceUrl = SERVER_API_URL + 'api/po-characteristics';

    constructor(private http: HttpClient) {}

    create(pOCharacteristic: IPOCharacteristic): Observable<EntityResponseType> {
        return this.http.post<IPOCharacteristic>(this.resourceUrl, pOCharacteristic, { observe: 'response' });
    }

    update(pOCharacteristic: IPOCharacteristic): Observable<EntityResponseType> {
        return this.http.put<IPOCharacteristic>(this.resourceUrl, pOCharacteristic, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPOCharacteristic>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPOCharacteristic[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
