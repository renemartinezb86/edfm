import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApplicationVersion } from 'app/shared/model/application-version.model';
import { ApplicationVersionService } from './application-version.service';

@Component({
    selector: 'jhi-application-version-delete-dialog',
    templateUrl: './application-version-delete-dialog.component.html'
})
export class ApplicationVersionDeleteDialogComponent {
    applicationVersion: IApplicationVersion;

    constructor(
        private applicationVersionService: ApplicationVersionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.applicationVersionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'applicationVersionListModification',
                content: 'Deleted an applicationVersion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-application-version-delete-popup',
    template: ''
})
export class ApplicationVersionDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ applicationVersion }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ApplicationVersionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.applicationVersion = applicationVersion;
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
