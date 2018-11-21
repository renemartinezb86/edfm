import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApplicationVersionRelation } from 'app/shared/model/application-version-relation.model';
import { ApplicationVersionRelationService } from './application-version-relation.service';

@Component({
    selector: 'jhi-application-version-relation-delete-dialog',
    templateUrl: './application-version-relation-delete-dialog.component.html'
})
export class ApplicationVersionRelationDeleteDialogComponent {
    applicationVersionRelation: IApplicationVersionRelation;

    constructor(
        private applicationVersionRelationService: ApplicationVersionRelationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.applicationVersionRelationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'applicationVersionRelationListModification',
                content: 'Deleted an applicationVersionRelation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-application-version-relation-delete-popup',
    template: ''
})
export class ApplicationVersionRelationDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ applicationVersionRelation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ApplicationVersionRelationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.applicationVersionRelation = applicationVersionRelation;
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
