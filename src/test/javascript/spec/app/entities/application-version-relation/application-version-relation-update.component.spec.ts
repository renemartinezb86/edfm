/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { ApplicationVersionRelationUpdateComponent } from 'app/entities/application-version-relation/application-version-relation-update.component';
import { ApplicationVersionRelationService } from 'app/entities/application-version-relation/application-version-relation.service';
import { ApplicationVersionRelation } from 'app/shared/model/application-version-relation.model';

describe('Component Tests', () => {
    describe('ApplicationVersionRelation Management Update Component', () => {
        let comp: ApplicationVersionRelationUpdateComponent;
        let fixture: ComponentFixture<ApplicationVersionRelationUpdateComponent>;
        let service: ApplicationVersionRelationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [ApplicationVersionRelationUpdateComponent]
            })
                .overrideTemplate(ApplicationVersionRelationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApplicationVersionRelationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApplicationVersionRelationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ApplicationVersionRelation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.applicationVersionRelation = entity;
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
                    const entity = new ApplicationVersionRelation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.applicationVersionRelation = entity;
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
