import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeployPipeline } from 'app/shared/model/deploy-pipeline.model';
import { DeployPipelineService } from './deploy-pipeline.service';

@Component({
    selector: 'jhi-deploy-pipeline-delete-dialog',
    templateUrl: './deploy-pipeline-delete-dialog.component.html'
})
export class DeployPipelineDeleteDialogComponent {
    deployPipeline: IDeployPipeline;

    constructor(
        private deployPipelineService: DeployPipelineService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.deployPipelineService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'deployPipelineListModification',
                content: 'Deleted an deployPipeline'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-deploy-pipeline-delete-popup',
    template: ''
})
export class DeployPipelineDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ deployPipeline }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DeployPipelineDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.deployPipeline = deployPipeline;
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
