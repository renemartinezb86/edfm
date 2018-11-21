/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EdfmTestModule } from '../../../test.module';
import { ProductOfferingDeleteDialogComponent } from 'app/entities/product-offering/product-offering-delete-dialog.component';
import { ProductOfferingService } from 'app/entities/product-offering/product-offering.service';

describe('Component Tests', () => {
    describe('ProductOffering Management Delete Component', () => {
        let comp: ProductOfferingDeleteDialogComponent;
        let fixture: ComponentFixture<ProductOfferingDeleteDialogComponent>;
        let service: ProductOfferingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [ProductOfferingDeleteDialogComponent]
            })
                .overrideTemplate(ProductOfferingDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductOfferingDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductOfferingService);
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
