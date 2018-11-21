import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductOffering } from 'app/shared/model/product-offering.model';
import { ProductOfferingService } from './product-offering.service';

@Component({
    selector: 'jhi-product-offering-delete-dialog',
    templateUrl: './product-offering-delete-dialog.component.html'
})
export class ProductOfferingDeleteDialogComponent {
    productOffering: IProductOffering;

    constructor(
        private productOfferingService: ProductOfferingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productOfferingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productOfferingListModification',
                content: 'Deleted an productOffering'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-offering-delete-popup',
    template: ''
})
export class ProductOfferingDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productOffering }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProductOfferingDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.productOffering = productOffering;
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
