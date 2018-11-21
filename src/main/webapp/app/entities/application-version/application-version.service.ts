import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApplicationVersion } from 'app/shared/model/application-version.model';

type EntityResponseType = HttpResponse<IApplicationVersion>;
type EntityArrayResponseType = HttpResponse<IApplicationVersion[]>;

@Injectable({ providedIn: 'root' })
export class ApplicationVersionService {
    public resourceUrl = SERVER_API_URL + 'api/application-versions';

    constructor(private http: HttpClient) {}

    create(applicationVersion: IApplicationVersion): Observable<EntityResponseType> {
        return this.http.post<IApplicationVersion>(this.resourceUrl, applicationVersion, { observe: 'response' });
    }

    update(applicationVersion: IApplicationVersion): Observable<EntityResponseType> {
        return this.http.put<IApplicationVersion>(this.resourceUrl, applicationVersion, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IApplicationVersion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IApplicationVersion[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
