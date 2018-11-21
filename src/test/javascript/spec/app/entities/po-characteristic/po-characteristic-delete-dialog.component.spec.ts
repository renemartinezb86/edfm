/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EdfmTestModule } from '../../../test.module';
import { POCharacteristicDeleteDialogComponent } from 'app/entities/po-characteristic/po-characteristic-delete-dialog.component';
import { POCharacteristicService } from 'app/entities/po-characteristic/po-characteristic.service';

describe('Component Tests', () => {
    describe('POCharacteristic Management Delete Component', () => {
        let comp: POCharacteristicDeleteDialogComponent;
        let fixture: ComponentFixture<POCharacteristicDeleteDialogComponent>;
        let service: POCharacteristicService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [POCharacteristicDeleteDialogComponent]
            })
                .overrideTemplate(POCharacteristicDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(POCharacteristicDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(POCharacteristicService);
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
