/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { BscsUpdateComponent } from 'app/entities/bscs/bscs-update.component';
import { BscsService } from 'app/entities/bscs/bscs.service';
import { Bscs } from 'app/shared/model/bscs.model';

describe('Component Tests', () => {
    describe('Bscs Management Update Component', () => {
        let comp: BscsUpdateComponent;
        let fixture: ComponentFixture<BscsUpdateComponent>;
        let service: BscsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [BscsUpdateComponent]
            })
                .overrideTemplate(BscsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BscsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BscsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Bscs(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bscs = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Bscs();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bscs = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
