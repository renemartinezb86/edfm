import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INetworkParameter } from 'app/shared/model/network-parameter.model';
import { NetworkParameterService } from './network-parameter.service';

@Component({
    selector: 'jhi-network-parameter-delete-dialog',
    templateUrl: './network-parameter-delete-dialog.component.html'
})
export class NetworkParameterDeleteDialogComponent {
    networkParameter: INetworkParameter;

    constructor(
        private networkParameterService: NetworkParameterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.networkParameterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'networkParameterListModification',
                content: 'Deleted an networkParameter'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-network-parameter-delete-popup',
    template: ''
})
export class NetworkParameterDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ networkParameter }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(NetworkParameterDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.networkParameter = networkParameter;
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
