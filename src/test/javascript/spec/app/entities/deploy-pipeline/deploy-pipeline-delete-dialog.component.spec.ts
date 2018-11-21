/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EdfmTestModule } from '../../../test.module';
import { DeployPipelineDeleteDialogComponent } from 'app/entities/deploy-pipeline/deploy-pipeline-delete-dialog.component';
import { DeployPipelineService } from 'app/entities/deploy-pipeline/deploy-pipeline.service';

describe('Component Tests', () => {
    describe('DeployPipeline Management Delete Component', () => {
        let comp: DeployPipelineDeleteDialogComponent;
        let fixture: ComponentFixture<DeployPipelineDeleteDialogComponent>;
        let service: DeployPipelineService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [DeployPipelineDeleteDialogComponent]
            })
                .overrideTemplate(DeployPipelineDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DeployPipelineDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeployPipelineService);
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
