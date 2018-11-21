/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EdfmTestModule } from '../../../test.module';
import { PoPriceDeleteDialogComponent } from 'app/entities/po-price/po-price-delete-dialog.component';
import { PoPriceService } from 'app/entities/po-price/po-price.service';

describe('Component Tests', () => {
    describe('PoPrice Management Delete Component', () => {
        let comp: PoPriceDeleteDialogComponent;
        let fixture: ComponentFixture<PoPriceDeleteDialogComponent>;
        let service: PoPriceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [PoPriceDeleteDialogComponent]
            })
                .overrideTemplate(PoPriceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PoPriceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoPriceService);
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
