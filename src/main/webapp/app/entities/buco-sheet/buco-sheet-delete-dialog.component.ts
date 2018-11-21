import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBucoSheet } from 'app/shared/model/buco-sheet.model';
import { BucoSheetService } from './buco-sheet.service';

@Component({
    selector: 'jhi-buco-sheet-delete-dialog',
    templateUrl: './buco-sheet-delete-dialog.component.html'
})
export class BucoSheetDeleteDialogComponent {
    bucoSheet: IBucoSheet;

    constructor(private bucoSheetService: BucoSheetService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bucoSheetService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bucoSheetListModification',
                content: 'Deleted an bucoSheet'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-buco-sheet-delete-popup',
    template: ''
})
export class BucoSheetDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bucoSheet }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BucoSheetDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.bucoSheet = bucoSheet;
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
