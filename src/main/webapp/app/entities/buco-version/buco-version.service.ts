import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBucoVersion } from 'app/shared/model/buco-version.model';

type EntityResponseType = HttpResponse<IBucoVersion>;
type EntityArrayResponseType = HttpResponse<IBucoVersion[]>;

@Injectable({ providedIn: 'root' })
export class BucoVersionService {
    public resourceUrl = SERVER_API_URL + 'api/buco-versions';

    constructor(private http: HttpClient) {}

    create(bucoVersion: IBucoVersion): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(bucoVersion);
        return this.http
            .post<IBucoVersion>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(bucoVersion: IBucoVersion): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(bucoVersion);
        return this.http
            .put<IBucoVersion>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBucoVersion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBucoVersion[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(bucoVersion: IBucoVersion): IBucoVersion {
        const copy: IBucoVersion = Object.assign({}, bucoVersion, {
            createdDate: bucoVersion.createdDate != null && bucoVersion.createdDate.isValid() ? bucoVersion.createdDate.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((bucoVersion: IBucoVersion) => {
                bucoVersion.createdDate = bucoVersion.createdDate != null ? moment(bucoVersion.createdDate) : null;
            });
        }
        return res;
    }
}
