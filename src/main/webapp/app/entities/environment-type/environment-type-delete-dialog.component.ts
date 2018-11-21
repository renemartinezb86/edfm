import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnvironmentType } from 'app/shared/model/environment-type.model';
import { EnvironmentTypeService } from './environment-type.service';

@Component({
    selector: 'jhi-environment-type-delete-dialog',
    templateUrl: './environment-type-delete-dialog.component.html'
})
export class EnvironmentTypeDeleteDialogComponent {
    environmentType: IEnvironmentType;

    constructor(
        private environmentTypeService: EnvironmentTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.environmentTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'environmentTypeListModification',
                content: 'Deleted an environmentType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-environment-type-delete-popup',
    template: ''
})
export class EnvironmentTypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ environmentType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EnvironmentTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.environmentType = environmentType;
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
