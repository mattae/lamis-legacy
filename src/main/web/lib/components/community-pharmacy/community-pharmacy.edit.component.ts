import { Component, OnInit } from '@angular/core';
import { NotificationService } from '@alfresco/adf-core';
import { ActivatedRoute } from '@angular/router';
import { AppLoaderService, entityCompare } from '@lamis/web-core';
import { Observable } from 'rxjs';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { CommunityPharmacyService } from '../../services/community-pharmacy.service';
import { CommunityPharmacy } from '../../model/community-pharmacy.model';
import { FacilityService } from '../../services/facility.service';
import { Facility } from '../../model/facility.model';

@Component({
    selector: 'community-pharmacy-edit',
    templateUrl: './community-pharmacy.edit.component.html'
})
export class CommunityPharmacyEditComponent implements OnInit {
    entity: CommunityPharmacy;
    state: any;
    states: any[] = [];
    lgas: any[] = [];
    isSaving: boolean;

    constructor(private service: CommunityPharmacyService,
                protected notification: NotificationService,
                private facilityService: FacilityService,
                protected activatedRoute: ActivatedRoute,
                private appLoaderService: AppLoaderService) {
    }

    createEntity(): CommunityPharmacy {
        return <CommunityPharmacy>{};
    }

    ngOnInit(): void {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({entity}) => {
            this.entity = !!entity && entity.body ? entity.body : entity;
            if (this.entity === undefined) {
                this.entity = this.createEntity();
                this.entity.active = true;
            }
        });

        this.facilityService.getActive().subscribe((res: any)=> {
            const  facility: Facility = res.body;
            this.entity.state = facility.state;
            this.states.push(this.entity.state);
            this.stateChange(facility.state.id);
        })
    }

    save() {
        this.isSaving = true;
        this.appLoaderService.open('Saving Community Pharmacy..');
        if (this.entity.id !== undefined) {
            this.subscribeToSaveResponse(this.service.update(this.entity));
        } else {
            this.subscribeToSaveResponse(this.service.create(this.entity));
        }
    }

    previousState() {
        window.history.back();
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<any>>) {
        result.subscribe(
            (res: HttpResponse<any>) => this.onSaveSuccess(res.body),
            (res: HttpErrorResponse) => {
                this.onSaveError();
                this.onError(res.message)
            });
    }

    private onSaveSuccess(result: any) {
        this.appLoaderService.close();
        this.isSaving = false;
        this.notification.openSnackMessage('Community Pharmacy successfully saved');
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
        this.appLoaderService.close();
        //this.submitButton.disabled = true;
        this.notification.showError('Error occurred saving Community Pharmacy; try again');
        //this.progressBar.mode = 'determinate';
    }

    protected onError(errorMessage: string) {
        this.appLoaderService.close();
        this.notification.showError(errorMessage);
    }

    stateChange(id) {
        this.service.getLgasByState(id).subscribe(res => this.lgas = res)
    }

    entityCompare(e1, e2) {
        return entityCompare(e1, e2);
    }
}
