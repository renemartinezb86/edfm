import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBscs } from 'app/shared/model/bscs.model';

type EntityResponseType = HttpResponse<IBscs>;
type EntityArrayResponseType = HttpResponse<IBscs[]>;

@Injectable({ providedIn: 'root' })
export class BscsService {
    public resourceUrl = SERVER_API_URL + 'api/bscs';

    constructor(private http: HttpClient) {}

    create(bscs: IBscs): Observable<EntityResponseType> {
        return this.http.post<IBscs>(this.resourceUrl, bscs, { observe: 'response' });
    }

    update(bscs: IBscs): Observable<EntityResponseType> {
        return this.http.put<IBscs>(this.resourceUrl, bscs, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBscs>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBscs[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
