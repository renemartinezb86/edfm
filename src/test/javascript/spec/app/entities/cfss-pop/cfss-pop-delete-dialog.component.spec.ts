/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EdfmTestModule } from '../../../test.module';
import { CfssPopDeleteDialogComponent } from 'app/entities/cfss-pop/cfss-pop-delete-dialog.component';
import { CfssPopService } from 'app/entities/cfss-pop/cfss-pop.service';

describe('Component Tests', () => {
    describe('CfssPop Management Delete Component', () => {
        let comp: CfssPopDeleteDialogComponent;
        let fixture: ComponentFixture<CfssPopDeleteDialogComponent>;
        let service: CfssPopService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [CfssPopDeleteDialogComponent]
            })
                .overrideTemplate(CfssPopDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CfssPopDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CfssPopService);
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
