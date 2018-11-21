/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EdfmTestModule } from '../../../test.module';
import { NetworkResourceDeleteDialogComponent } from 'app/entities/network-resource/network-resource-delete-dialog.component';
import { NetworkResourceService } from 'app/entities/network-resource/network-resource.service';

describe('Component Tests', () => {
    describe('NetworkResource Management Delete Component', () => {
        let comp: NetworkResourceDeleteDialogComponent;
        let fixture: ComponentFixture<NetworkResourceDeleteDialogComponent>;
        let service: NetworkResourceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [NetworkResourceDeleteDialogComponent]
            })
                .overrideTemplate(NetworkResourceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NetworkResourceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NetworkResourceService);
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
