import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeploymentPipelineLog } from 'app/shared/model/deployment-pipeline-log.model';
import { DeploymentPipelineLogService } from './deployment-pipeline-log.service';

@Component({
    selector: 'jhi-deployment-pipeline-log-delete-dialog',
    templateUrl: './deployment-pipeline-log-delete-dialog.component.html'
})
export class DeploymentPipelineLogDeleteDialogComponent {
    deploymentPipelineLog: IDeploymentPipelineLog;

    constructor(
        private deploymentPipelineLogService: DeploymentPipelineLogService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.deploymentPipelineLogService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'deploymentPipelineLogListModification',
                content: 'Deleted an deploymentPipelineLog'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-deployment-pipeline-log-delete-popup',
    template: ''
})
export class DeploymentPipelineLogDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ deploymentPipelineLog }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DeploymentPipelineLogDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.deploymentPipelineLog = deploymentPipelineLog;
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
