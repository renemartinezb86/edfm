/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { FreeUnitUpdateComponent } from 'app/entities/free-unit/free-unit-update.component';
import { FreeUnitService } from 'app/entities/free-unit/free-unit.service';
import { FreeUnit } from 'app/shared/model/free-unit.model';

describe('Component Tests', () => {
    describe('FreeUnit Management Update Component', () => {
        let comp: FreeUnitUpdateComponent;
        let fixture: ComponentFixture<FreeUnitUpdateComponent>;
        let service: FreeUnitService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [FreeUnitUpdateComponent]
            })
                .overrideTemplate(FreeUnitUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FreeUnitUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FreeUnitService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new FreeUnit(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.freeUnit = entity;
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
                    const entity = new FreeUnit();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.freeUnit = entity;
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
