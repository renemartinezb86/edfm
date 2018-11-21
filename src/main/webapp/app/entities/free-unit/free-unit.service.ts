import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFreeUnit } from 'app/shared/model/free-unit.model';

type EntityResponseType = HttpResponse<IFreeUnit>;
type EntityArrayResponseType = HttpResponse<IFreeUnit[]>;

@Injectable({ providedIn: 'root' })
export class FreeUnitService {
    public resourceUrl = SERVER_API_URL + 'api/free-units';

    constructor(private http: HttpClient) {}

    create(freeUnit: IFreeUnit): Observable<EntityResponseType> {
        return this.http.post<IFreeUnit>(this.resourceUrl, freeUnit, { observe: 'response' });
    }

    update(freeUnit: IFreeUnit): Observable<EntityResponseType> {
        return this.http.put<IFreeUnit>(this.resourceUrl, freeUnit, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFreeUnit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFreeUnit[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
