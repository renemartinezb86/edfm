import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPoPrice } from 'app/shared/model/po-price.model';
import { PoPriceService } from './po-price.service';

@Component({
    selector: 'jhi-po-price-delete-dialog',
    templateUrl: './po-price-delete-dialog.component.html'
})
export class PoPriceDeleteDialogComponent {
    poPrice: IPoPrice;

    constructor(private poPriceService: PoPriceService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.poPriceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'poPriceListModification',
                content: 'Deleted an poPrice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-po-price-delete-popup',
    template: ''
})
export class PoPriceDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ poPrice }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PoPriceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.poPrice = poPrice;
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
