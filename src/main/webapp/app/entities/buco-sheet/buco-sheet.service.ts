import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBucoSheet } from 'app/shared/model/buco-sheet.model';

type EntityResponseType = HttpResponse<IBucoSheet>;
type EntityArrayResponseType = HttpResponse<IBucoSheet[]>;

@Injectable({ providedIn: 'root' })
export class BucoSheetService {
    public resourceUrl = SERVER_API_URL + 'api/buco-sheets';

    constructor(private http: HttpClient) {}

    create(bucoSheet: IBucoSheet): Observable<EntityResponseType> {
        return this.http.post<IBucoSheet>(this.resourceUrl, bucoSheet, { observe: 'response' });
    }

    update(bucoSheet: IBucoSheet): Observable<EntityResponseType> {
        return this.http.put<IBucoSheet>(this.resourceUrl, bucoSheet, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBucoSheet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBucoSheet[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
