import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICfssPop } from 'app/shared/model/cfss-pop.model';

type EntityResponseType = HttpResponse<ICfssPop>;
type EntityArrayResponseType = HttpResponse<ICfssPop[]>;

@Injectable({ providedIn: 'root' })
export class CfssPopService {
    public resourceUrl = SERVER_API_URL + 'api/cfss-pops';

    constructor(private http: HttpClient) {}

    create(cfssPop: ICfssPop): Observable<EntityResponseType> {
        return this.http.post<ICfssPop>(this.resourceUrl, cfssPop, { observe: 'response' });
    }

    update(cfssPop: ICfssPop): Observable<EntityResponseType> {
        return this.http.put<ICfssPop>(this.resourceUrl, cfssPop, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICfssPop>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICfssPop[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
