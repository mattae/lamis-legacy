import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { createRequestOption, SERVER_API_URL_CONFIG, ServerApiUrlConfig } from '@lamis/web-core';
import { Observable } from 'rxjs';
import { DDDOutlet } from '../model/ddd-outlet.model';

@Injectable({
    providedIn: 'root'
})
export class DddOutletService {
    public resourceUrl = '';

    constructor(protected http: HttpClient, @Inject(SERVER_API_URL_CONFIG) private serverUrl: ServerApiUrlConfig) {
        this.resourceUrl = serverUrl.SERVER_API_URL + '/api/ddd-outlets';
    }

    create(caseManager: DDDOutlet): Observable<HttpResponse<DDDOutlet>> {
        return this.http
            .post<DDDOutlet>(this.resourceUrl, caseManager, {observe: 'response'});
    }

    update(caseManager: DDDOutlet): Observable<HttpResponse<DDDOutlet>> {
        return this.http
            .put<DDDOutlet>(this.resourceUrl, caseManager, {observe: 'response'})
    }

    find(id: number): Observable<HttpResponse<DDDOutlet>> {
        return this.http
            .get<DDDOutlet>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }

    query(req?: any): Observable<HttpResponse<DDDOutlet[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<DDDOutlet[]>(this.resourceUrl, {params: options, observe: 'response'});
    }

    getLgasByState(id) {
        return this.http.get<any[]>(`/api/provinces/state/${id}`)
    }
}
