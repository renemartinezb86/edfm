/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EdfmTestModule } from '../../../test.module';
import { ApplicationVersionRelationDeleteDialogComponent } from 'app/entities/application-version-relation/application-version-relation-delete-dialog.component';
import { ApplicationVersionRelationService } from 'app/entities/application-version-relation/application-version-relation.service';

describe('Component Tests', () => {
    describe('ApplicationVersionRelation Management Delete Component', () => {
        let comp: ApplicationVersionRelationDeleteDialogComponent;
        let fixture: ComponentFixture<ApplicationVersionRelationDeleteDialogComponent>;
        let service: ApplicationVersionRelationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [ApplicationVersionRelationDeleteDialogComponent]
            })
                .overrideTemplate(ApplicationVersionRelationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApplicationVersionRelationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApplicationVersionRelationService);
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
