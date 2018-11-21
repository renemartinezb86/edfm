import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INetworkResource } from 'app/shared/model/network-resource.model';
import { NetworkResourceService } from './network-resource.service';

@Component({
    selector: 'jhi-network-resource-delete-dialog',
    templateUrl: './network-resource-delete-dialog.component.html'
})
export class NetworkResourceDeleteDialogComponent {
    networkResource: INetworkResource;

    constructor(
        private networkResourceService: NetworkResourceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.networkResourceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'networkResourceListModification',
                content: 'Deleted an networkResource'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-network-resource-delete-popup',
    template: ''
})
export class NetworkResourceDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ networkResource }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(NetworkResourceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.networkResource = networkResource;
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
