import { Component, OnInit } from '@angular/core';
import { CardViewBoolItemModel, CardViewItem, CardViewTextItemModel, NotificationService } from '@alfresco/adf-core';
import { ActivatedRoute, Router } from '@angular/router';
import { TdDialogService } from '@covalent/core';
import { DDDOutlet } from '../../model/ddd-outlet.model';
import { DddOutletService } from '../../services/ddd-outlet.service';

@Component({
    selector: 'ddd-outlet-details',
    templateUrl: './ddd-outlet.details.component.html'
})
export class DddOutletDetailsComponent implements OnInit {
    properties: CardViewItem[] = [];
    entity: DDDOutlet;

    constructor(private router: Router, private route: ActivatedRoute, private service: DddOutletService,
                private _dialogService: TdDialogService,
                private notificationService: NotificationService) {
    }

    ngOnInit() {
        this.route.data.subscribe(({entity}) => {
            this.entity = !!entity && entity.body ? entity.body : entity;
            this.buildProperties();
        });
    }

    edit() {
        this.router.navigate(['/', 'admin', 'config', 'ddd-outlets', this.entity.id, 'edit']);
    }

    delete() {
        this._dialogService.openConfirm({
            title: 'Confirm',
            message: 'Do you want to delete this DDD Outlet, action cannot be reversed?',
            cancelButton: 'No',
            acceptButton: 'Yes',
            width: '500px',
        }).afterClosed().subscribe((accept: boolean) => {
            if (accept) {
                this.service.delete(this.entity.id).subscribe((res) => {
                    if (res.ok) {
                        this.router.navigate(['admin', 'config', 'ddd-outlets'])
                    } else {
                        this.notificationService.showError('Error deleting DDD Outlet, please try again')
                    }
                })
            } else {
                // DO SOMETHING ELSE
            }
        });
    }

    buildProperties() {
        this.properties.push(new CardViewTextItemModel({
            label: 'Name',
            key: 'cs',
            value: this.entity.name
        }));
        this.properties.push(new CardViewTextItemModel({
            label: 'Type',
            key: 'tp',
            value: this.entity.type
        }));
        this.properties.push(new CardViewTextItemModel({
            label: 'Address',
            key: 'fs',
            value: this.entity.address
        }));
        this.properties.push(new CardViewTextItemModel({
            label: 'PIN',
            key: 'fs',
            value: this.entity.pin
        }));
        this.properties.push(new CardViewTextItemModel({
            label: 'Phone',
            key: 'ts',
            value: this.entity.phone
        }));
        this.properties.push(new CardViewTextItemModel({
            label: 'Email',
            key: 'cd4p',
            value: this.entity.email
        }));
        this.properties.push(new CardViewBoolItemModel({
            label: 'Active',
            key: 'cd4p',
            value: this.entity.active
        }));
    }

    previousState() {
        window.history.back();
    }

}
