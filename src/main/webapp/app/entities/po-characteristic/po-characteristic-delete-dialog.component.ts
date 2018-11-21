import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPOCharacteristic } from 'app/shared/model/po-characteristic.model';
import { POCharacteristicService } from './po-characteristic.service';

@Component({
    selector: 'jhi-po-characteristic-delete-dialog',
    templateUrl: './po-characteristic-delete-dialog.component.html'
})
export class POCharacteristicDeleteDialogComponent {
    pOCharacteristic: IPOCharacteristic;

    constructor(
        private pOCharacteristicService: POCharacteristicService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pOCharacteristicService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pOCharacteristicListModification',
                content: 'Deleted an pOCharacteristic'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-po-characteristic-delete-popup',
    template: ''
})
export class POCharacteristicDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pOCharacteristic }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(POCharacteristicDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.pOCharacteristic = pOCharacteristic;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
