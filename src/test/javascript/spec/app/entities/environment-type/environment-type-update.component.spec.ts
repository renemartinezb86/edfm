/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { EnvironmentTypeUpdateComponent } from 'app/entities/environment-type/environment-type-update.component';
import { EnvironmentTypeService } from 'app/entities/environment-type/environment-type.service';
import { EnvironmentType } from 'app/shared/model/environment-type.model';

describe('Component Tests', () => {
    describe('EnvironmentType Management Update Component', () => {
        let comp: EnvironmentTypeUpdateComponent;
        let fixture: ComponentFixture<EnvironmentTypeUpdateComponent>;
        let service: EnvironmentTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [EnvironmentTypeUpdateComponent]
            })
                .overrideTemplate(EnvironmentTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EnvironmentTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnvironmentTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EnvironmentType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.environmentType = entity;
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
                    const entity = new EnvironmentType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.environmentType = entity;
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
