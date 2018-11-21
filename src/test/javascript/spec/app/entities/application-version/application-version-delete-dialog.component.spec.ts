/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EdfmTestModule } from '../../../test.module';
import { ApplicationVersionDeleteDialogComponent } from 'app/entities/application-version/application-version-delete-dialog.component';
import { ApplicationVersionService } from 'app/entities/application-version/application-version.service';

describe('Component Tests', () => {
    describe('ApplicationVersion Management Delete Component', () => {
        let comp: ApplicationVersionDeleteDialogComponent;
        let fixture: ComponentFixture<ApplicationVersionDeleteDialogComponent>;
        let service: ApplicationVersionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [ApplicationVersionDeleteDialogComponent]
            })
                .overrideTemplate(ApplicationVersionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApplicationVersionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApplicationVersionService);
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
