import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CommunityPharmacyResolve, ROUTES } from './services/cp.routes';
import {
    MatButtonModule,
    MatCardModule,
    MatDividerModule,
    MatIconModule,
    MatInputModule,
    MatSelectModule
} from '@angular/material';
import { CommunityPharmacyDetailsComponent } from './components/community-pharmacy/community-pharmacy.details.component';
import { CommunityPharmacyEditComponent } from './components/community-pharmacy/community-pharmacy.edit.component';
import { CommunityPharmacyListComponent } from './components/community-pharmacy/community-pharmacy-list.component';
import { CoreModule } from '@alfresco/adf-core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LamisCoreModule } from '@lamis/web-core';
import { CovalentCommonModule, CovalentDialogsModule } from '@covalent/core';

@NgModule({
    declarations: [
        CommunityPharmacyDetailsComponent,
        CommunityPharmacyEditComponent,
        CommunityPharmacyListComponent
    ],
    imports: [
        CommonModule,
        MatInputModule,
        MatIconModule,
        MatDividerModule,
        MatCardModule,
        MatSelectModule,
        MatButtonModule,
        FormsModule,
        RouterModule.forChild(ROUTES),
        CoreModule,
        CovalentCommonModule,
        CovalentDialogsModule,
        NgbModule,
        LamisCoreModule
    ],
    exports: [
    ],
    providers: [
        CommunityPharmacyResolve
    ]
})
export class CommunityPharmacyModule {

}
