import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDeployment } from 'app/shared/model/deployment.model';

type EntityResponseType = HttpResponse<IDeployment>;
type EntityArrayResponseType = HttpResponse<IDeployment[]>;

@Injectable({ providedIn: 'root' })
export class DeploymentService {
    public resourceUrl = SERVER_API_URL + 'api/deployments';

    constructor(private http: HttpClient) {}

    create(deployment: IDeployment): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(deployment);
        return this.http
            .post<IDeployment>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(deployment: IDeployment): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(deployment);
        return this.http
            .put<IDeployment>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDeployment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDeployment[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(deployment: IDeployment): IDeployment {
        const copy: IDeployment = Object.assign({}, deployment, {
            date: deployment.date != null && deployment.date.isValid() ? deployment.date.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.date = res.body.date != null ? moment(res.body.date) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((deployment: IDeployment) => {
                deployment.date = deployment.date != null ? moment(deployment.date) : null;
            });
        }
        return res;
    }
}
