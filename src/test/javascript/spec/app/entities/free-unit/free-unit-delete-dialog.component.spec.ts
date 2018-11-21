/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EdfmTestModule } from '../../../test.module';
import { FreeUnitDeleteDialogComponent } from 'app/entities/free-unit/free-unit-delete-dialog.component';
import { FreeUnitService } from 'app/entities/free-unit/free-unit.service';

describe('Component Tests', () => {
    describe('FreeUnit Management Delete Component', () => {
        let comp: FreeUnitDeleteDialogComponent;
        let fixture: ComponentFixture<FreeUnitDeleteDialogComponent>;
        let service: FreeUnitService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [FreeUnitDeleteDialogComponent]
            })
                .overrideTemplate(FreeUnitDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FreeUnitDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FreeUnitService);
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
