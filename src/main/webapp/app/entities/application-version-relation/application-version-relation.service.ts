import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApplicationVersionRelation } from 'app/shared/model/application-version-relation.model';

type EntityResponseType = HttpResponse<IApplicationVersionRelation>;
type EntityArrayResponseType = HttpResponse<IApplicationVersionRelation[]>;

@Injectable({ providedIn: 'root' })
export class ApplicationVersionRelationService {
    public resourceUrl = SERVER_API_URL + 'api/application-version-relations';

    constructor(private http: HttpClient) {}

    create(applicationVersionRelation: IApplicationVersionRelation): Observable<EntityResponseType> {
        return this.http.post<IApplicationVersionRelation>(this.resourceUrl, applicationVersionRelation, { observe: 'response' });
    }

    update(applicationVersionRelation: IApplicationVersionRelation): Observable<EntityResponseType> {
        return this.http.put<IApplicationVersionRelation>(this.resourceUrl, applicationVersionRelation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IApplicationVersionRelation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IApplicationVersionRelation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
