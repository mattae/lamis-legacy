import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { DDDOutletResolve, ROUTES } from './services/ddd-outlet.routes';
import {
    MatButtonModule,
    MatCardModule,
    MatDividerModule,
    MatIconModule,
    MatInputModule,
    MatSelectModule
} from '@angular/material';
import { DddOutletDetailsComponent } from './components/community-pharmacy/ddd-outlet.details.component';
import { DddOutletEditComponent } from './components/community-pharmacy/ddd-outlet.edit.component';
import { DddOutListComponent } from './components/community-pharmacy/ddd-out-list.component';
import { CoreModule } from '@alfresco/adf-core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LamisCoreModule } from '@lamis/web-core';
import { CovalentCommonModule, CovalentDialogsModule } from '@covalent/core';

@NgModule({
    declarations: [
        DddOutletDetailsComponent,
        DddOutletEditComponent,
        DddOutListComponent
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
    exports: [],
    providers: [
        DDDOutletResolve
    ]
})
export class DddOutletModule {

}
