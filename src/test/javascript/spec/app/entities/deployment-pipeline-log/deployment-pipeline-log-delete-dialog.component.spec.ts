/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EdfmTestModule } from '../../../test.module';
import { DeploymentPipelineLogDeleteDialogComponent } from 'app/entities/deployment-pipeline-log/deployment-pipeline-log-delete-dialog.component';
import { DeploymentPipelineLogService } from 'app/entities/deployment-pipeline-log/deployment-pipeline-log.service';

describe('Component Tests', () => {
    describe('DeploymentPipelineLog Management Delete Component', () => {
        let comp: DeploymentPipelineLogDeleteDialogComponent;
        let fixture: ComponentFixture<DeploymentPipelineLogDeleteDialogComponent>;
        let service: DeploymentPipelineLogService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [DeploymentPipelineLogDeleteDialogComponent]
            })
                .overrideTemplate(DeploymentPipelineLogDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DeploymentPipelineLogDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeploymentPipelineLogService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
