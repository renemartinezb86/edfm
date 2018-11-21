/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EdfmTestModule } from '../../../test.module';
import { EnvironmentTypeDeleteDialogComponent } from 'app/entities/environment-type/environment-type-delete-dialog.component';
import { EnvironmentTypeService } from 'app/entities/environment-type/environment-type.service';

describe('Component Tests', () => {
    describe('EnvironmentType Management Delete Component', () => {
        let comp: EnvironmentTypeDeleteDialogComponent;
        let fixture: ComponentFixture<EnvironmentTypeDeleteDialogComponent>;
        let service: EnvironmentTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [EnvironmentTypeDeleteDialogComponent]
            })
                .overrideTemplate(EnvironmentTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EnvironmentTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnvironmentTypeService);
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
