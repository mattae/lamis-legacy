import { Component, OnInit } from '@angular/core';
import { NotificationService } from '@alfresco/adf-core';
import { ActivatedRoute, Router } from '@angular/router';
import { Facility } from '../../model/facility.model';
import { CommunityPharmacy } from '../../model/community-pharmacy.model';
import { CommunityPharmacyService } from '../../services/community-pharmacy.service';
import { FacilityService } from '../../services/facility.service';

@Component({
    selector: 'community-pharmacies',
    templateUrl: './community-pharmacy-list.component.html'
})
export class CommunityPharmacyListComponent implements OnInit {
    page = 0;
    communityPharmacies: CommunityPharmacy[];
    loading = false;
    public itemsPerPage: number = 10;
    public currentSearch: string = '';
    totalItems = 0;
    display = 'list';
    facility: Facility;

    constructor(private service: CommunityPharmacyService,
                private facilityService: FacilityService,
                protected notification: NotificationService,
                protected router: Router,
                protected activatedRoute: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.facilityService.getActive().subscribe((res: any) => {
            this.facility = res.body;
            this.onPageChange(0);
        })
    }

    public select(data: any): any {
        this.router.navigate(['..', 'community-pharmacies', data.obj.id, 'view'], {relativeTo: this.activatedRoute});
    }

    onPageChange(pageInfo) {
        this.page = pageInfo;
        this.loadAll(pageInfo - 1);
    }

    loadPage(page: number) {
        this.page = page;
        this.loadAll(page - 1);
    }

    loadAll(page: number) {
        this.loading = true;
        this.service.query({
            keyword: this.currentSearch,
            page: page < 0 ? 0 : page,
            stateId: this.facility && this.facility.state && this.facility.state.id || 0,
            size: this.itemsPerPage,
            sort: ['id', 'asc']
        }).subscribe(
            (res: any) => {
                this.onSuccess(res.body, res.headers)
            },
            (res: any) => this.onError(res)
        );
    }

    protected onSuccess(data: any, headers: any) {
        this.communityPharmacies = data;
        this.totalItems = headers.get('X-Total-Count');
        this.loading = false;
    }

    private onError(error: any) {
        this.notification.openSnackMessage(error.message);
        this.loading = false;
    }
}
