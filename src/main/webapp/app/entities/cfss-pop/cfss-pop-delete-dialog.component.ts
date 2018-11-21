import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICfssPop } from 'app/shared/model/cfss-pop.model';
import { CfssPopService } from './cfss-pop.service';

@Component({
    selector: 'jhi-cfss-pop-delete-dialog',
    templateUrl: './cfss-pop-delete-dialog.component.html'
})
export class CfssPopDeleteDialogComponent {
    cfssPop: ICfssPop;

    constructor(private cfssPopService: CfssPopService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cfssPopService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cfssPopListModification',
                content: 'Deleted an cfssPop'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cfss-pop-delete-popup',
    template: ''
})
export class CfssPopDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cfssPop }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CfssPopDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.cfssPop = cfssPop;
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
