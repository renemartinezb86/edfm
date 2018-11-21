import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDeployPipeline } from 'app/shared/model/deploy-pipeline.model';

type EntityResponseType = HttpResponse<IDeployPipeline>;
type EntityArrayResponseType = HttpResponse<IDeployPipeline[]>;

@Injectable({ providedIn: 'root' })
export class DeployPipelineService {
    public resourceUrl = SERVER_API_URL + 'api/deploy-pipelines';

    constructor(private http: HttpClient) {}

    create(deployPipeline: IDeployPipeline): Observable<EntityResponseType> {
        return this.http.post<IDeployPipeline>(this.resourceUrl, deployPipeline, { observe: 'response' });
    }

    update(deployPipeline: IDeployPipeline): Observable<EntityResponseType> {
        return this.http.put<IDeployPipeline>(this.resourceUrl, deployPipeline, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDeployPipeline>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDeployPipeline[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
