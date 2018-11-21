/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EdfmTestModule } from '../../../test.module';
import { BscsDeleteDialogComponent } from 'app/entities/bscs/bscs-delete-dialog.component';
import { BscsService } from 'app/entities/bscs/bscs.service';

describe('Component Tests', () => {
    describe('Bscs Management Delete Component', () => {
        let comp: BscsDeleteDialogComponent;
        let fixture: ComponentFixture<BscsDeleteDialogComponent>;
        let service: BscsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [BscsDeleteDialogComponent]
            })
                .overrideTemplate(BscsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BscsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BscsService);
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
