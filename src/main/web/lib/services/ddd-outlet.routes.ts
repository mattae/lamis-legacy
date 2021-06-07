import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { DDDOutlet } from '../model/ddd-outlet.model';
import { DddOutletService } from './ddd-outlet.service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { DddOutListComponent } from '../components/community-pharmacy/ddd-out-list.component';
import { DddOutletDetailsComponent } from '../components/community-pharmacy/ddd-outlet.details.component';
import { DddOutletEditComponent } from '../components/community-pharmacy/ddd-outlet.edit.component';

@Injectable()
export class DDDOutletResolve implements Resolve<DDDOutlet> {
    constructor(private service: DddOutletService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<DDDOutlet> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DDDOutlet>) => response.ok),
                map((patient: HttpResponse<DDDOutlet>) => patient.body)
            );
        }
        return of(<DDDOutlet>{});
    }
}

export const ROUTES: Routes = [
    {
        path: '',
        data: {
            title: 'DDD Outlets',
            breadcrumb: 'DDD OUTLETS'
        },
        children: [
            {
                path: '',
                component: DddOutListComponent,
                data: {
                    authorities: ['ROLE_USER'],
                    title: 'DDD Outlets',
                    breadcrumb: 'DDD OUTLETS'
                },
                //canActivate: [UserRouteAccessService]
            },
            {
                path: ':id/view',
                component: DddOutletDetailsComponent,
                resolve: {
                    entity: DDDOutletResolve
                },
                data: {
                    authorities: ['ROLE_USER'],
                    title: 'DDD Outlet',
                    breadcrumb: 'DDD OUTLET'
                },
                //canActivate: [UserRouteAccessService]
            },
            {
                path: 'new',
                component: DddOutletEditComponent,
                data: {
                    authorities: ['ROLE_DEC'],
                    title: 'Add DDD Outlet',
                    breadcrumb: 'ADD DDD OUTLET'
                },
                //canActivate: [UserRouteAccessService]
            },
            {
                path: ':id/edit',
                component: DddOutletEditComponent,
                resolve: {
                    entity: DDDOutletResolve
                },
                data: {
                    authorities: ['ROLE_DEC'],
                    title: 'Edit DDD Outlet',
                    breadcrumb: 'EDIT DDD OUTLET'
                },
                //canActivate: [UserRouteAccessService]
            }
        ]
    }
];
