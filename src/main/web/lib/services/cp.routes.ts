import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { CommunityPharmacy } from '../model/community-pharmacy.model';
import { CommunityPharmacyService } from './community-pharmacy.service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { CommunityPharmacyListComponent } from '../components/community-pharmacy/community-pharmacy-list.component';
import { CommunityPharmacyDetailsComponent } from '../components/community-pharmacy/community-pharmacy.details.component';
import { CommunityPharmacyEditComponent } from '../components/community-pharmacy/community-pharmacy.edit.component';

@Injectable()
export class CommunityPharmacyResolve implements Resolve<CommunityPharmacy> {
    constructor(private service: CommunityPharmacyService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CommunityPharmacy> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommunityPharmacy>) => response.ok),
                map((patient: HttpResponse<CommunityPharmacy>) => patient.body)
            );
        }
        return of(<CommunityPharmacy>{});
    }
}

export const ROUTES: Routes = [
    {
        path: '',
        data: {
            title: 'Community Pharmacies',
            breadcrumb: 'COMMUNITY PHARMACIES'
        },
        children: [
            {
                path: '',
                component: CommunityPharmacyListComponent,
                data: {
                    authorities: ['ROLE_USER'],
                    title: 'Community Pharmacies',
                    breadcrumb: 'COMMUNITY PHARMACIES'
                },
                //canActivate: [UserRouteAccessService]
            },
            {
                path: ':id/view',
                component: CommunityPharmacyDetailsComponent,
                resolve: {
                    entity: CommunityPharmacyResolve
                },
                data: {
                    authorities: ['ROLE_USER'],
                    title: 'Community Pharmacy',
                    breadcrumb: 'COMMUNITY PHARMACY'
                },
                //canActivate: [UserRouteAccessService]
            },
            {
                path: 'new',
                component: CommunityPharmacyEditComponent,
                data: {
                    authorities: ['ROLE_DEC'],
                    title: 'Add Community Pharmacy',
                    breadcrumb: 'ADD COMMUNITY PHARMACY'
                },
                //canActivate: [UserRouteAccessService]
            },
            {
                path: ':id/edit',
                component: CommunityPharmacyEditComponent,
                resolve: {
                    entity: CommunityPharmacyResolve
                },
                data: {
                    authorities: ['ROLE_DEC'],
                    title: 'Community Pharmacy Edit',
                    breadcrumb: 'COMMUNITY PHARMACY EDIT'
                },
                //canActivate: [UserRouteAccessService]
            }
        ]
    }
];
