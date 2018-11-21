import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFreeUnit } from 'app/shared/model/free-unit.model';
import { FreeUnitService } from './free-unit.service';

@Component({
    selector: 'jhi-free-unit-delete-dialog',
    templateUrl: './free-unit-delete-dialog.component.html'
})
export class FreeUnitDeleteDialogComponent {
    freeUnit: IFreeUnit;

    constructor(private freeUnitService: FreeUnitService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.freeUnitService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'freeUnitListModification',
                content: 'Deleted an freeUnit'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-free-unit-delete-popup',
    template: ''
})
export class FreeUnitDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ freeUnit }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FreeUnitDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.freeUnit = freeUnit;
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
