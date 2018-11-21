import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBucoVersion } from 'app/shared/model/buco-version.model';
import { BucoVersionService } from './buco-version.service';

@Component({
    selector: 'jhi-buco-version-delete-dialog',
    templateUrl: './buco-version-delete-dialog.component.html'
})
export class BucoVersionDeleteDialogComponent {
    bucoVersion: IBucoVersion;

    constructor(
        private bucoVersionService: BucoVersionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bucoVersionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bucoVersionListModification',
                content: 'Deleted an bucoVersion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-buco-version-delete-popup',
    template: ''
})
export class BucoVersionDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bucoVersion }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BucoVersionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.bucoVersion = bucoVersion;
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
