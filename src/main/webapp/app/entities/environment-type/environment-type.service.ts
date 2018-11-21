import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEnvironmentType } from 'app/shared/model/environment-type.model';

type EntityResponseType = HttpResponse<IEnvironmentType>;
type EntityArrayResponseType = HttpResponse<IEnvironmentType[]>;

@Injectable({ providedIn: 'root' })
export class EnvironmentTypeService {
    public resourceUrl = SERVER_API_URL + 'api/environment-types';

    constructor(private http: HttpClient) {}

    create(environmentType: IEnvironmentType): Observable<EntityResponseType> {
        return this.http.post<IEnvironmentType>(this.resourceUrl, environmentType, { observe: 'response' });
    }

    update(environmentType: IEnvironmentType): Observable<EntityResponseType> {
        return this.http.put<IEnvironmentType>(this.resourceUrl, environmentType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEnvironmentType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEnvironmentType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
