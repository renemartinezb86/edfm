import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPoPrice } from 'app/shared/model/po-price.model';

type EntityResponseType = HttpResponse<IPoPrice>;
type EntityArrayResponseType = HttpResponse<IPoPrice[]>;

@Injectable({ providedIn: 'root' })
export class PoPriceService {
    public resourceUrl = SERVER_API_URL + 'api/po-prices';

    constructor(private http: HttpClient) {}

    create(poPrice: IPoPrice): Observable<EntityResponseType> {
        return this.http.post<IPoPrice>(this.resourceUrl, poPrice, { observe: 'response' });
    }

    update(poPrice: IPoPrice): Observable<EntityResponseType> {
        return this.http.put<IPoPrice>(this.resourceUrl, poPrice, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPoPrice>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPoPrice[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
