/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EdfmTestModule } from '../../../test.module';
import { BucoVersionDeleteDialogComponent } from 'app/entities/buco-version/buco-version-delete-dialog.component';
import { BucoVersionService } from 'app/entities/buco-version/buco-version.service';

describe('Component Tests', () => {
    describe('BucoVersion Management Delete Component', () => {
        let comp: BucoVersionDeleteDialogComponent;
        let fixture: ComponentFixture<BucoVersionDeleteDialogComponent>;
        let service: BucoVersionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [BucoVersionDeleteDialogComponent]
            })
                .overrideTemplate(BucoVersionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BucoVersionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BucoVersionService);
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
