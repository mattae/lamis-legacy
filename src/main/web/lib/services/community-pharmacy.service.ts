import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { createRequestOption, SERVER_API_URL_CONFIG, ServerApiUrlConfig } from '@lamis/web-core';
import { Observable } from 'rxjs';
import { CommunityPharmacy } from '../model/community-pharmacy.model';

@Injectable({
    providedIn: 'root'
})
export class CommunityPharmacyService {
    public resourceUrl = '';

    constructor(protected http: HttpClient, @Inject(SERVER_API_URL_CONFIG) private serverUrl: ServerApiUrlConfig) {
        this.resourceUrl = serverUrl.SERVER_API_URL + '/api/community-pharmacies';
    }

    create(caseManager: CommunityPharmacy): Observable<HttpResponse<CommunityPharmacy>> {
        return this.http
            .post<CommunityPharmacy>(this.resourceUrl, caseManager, {observe: 'response'});
    }

    update(caseManager: CommunityPharmacy): Observable<HttpResponse<CommunityPharmacy>> {
        return this.http
            .put<CommunityPharmacy>(this.resourceUrl, caseManager, {observe: 'response'})
    }

    find(id: number): Observable<HttpResponse<CommunityPharmacy>> {
        return this.http
            .get<CommunityPharmacy>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
    }

    query(req?: any): Observable<HttpResponse<CommunityPharmacy[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<CommunityPharmacy[]>(this.resourceUrl, {params: options, observe: 'response'});
    }

    getLgasByState(id) {
        return this.http.get<any[]>(`/api/provinces/state/${id}`)
    }
}
