import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductOffering } from 'app/shared/model/product-offering.model';

type EntityResponseType = HttpResponse<IProductOffering>;
type EntityArrayResponseType = HttpResponse<IProductOffering[]>;

@Injectable({ providedIn: 'root' })
export class ProductOfferingService {
    public resourceUrl = SERVER_API_URL + 'api/product-offerings';

    constructor(private http: HttpClient) {}

    create(productOffering: IProductOffering): Observable<EntityResponseType> {
        return this.http.post<IProductOffering>(this.resourceUrl, productOffering, { observe: 'response' });
    }

    update(productOffering: IProductOffering): Observable<EntityResponseType> {
        return this.http.put<IProductOffering>(this.resourceUrl, productOffering, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProductOffering>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductOffering[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
