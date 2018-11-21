/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { ApplicationVersionUpdateComponent } from 'app/entities/application-version/application-version-update.component';
import { ApplicationVersionService } from 'app/entities/application-version/application-version.service';
import { ApplicationVersion } from 'app/shared/model/application-version.model';

describe('Component Tests', () => {
    describe('ApplicationVersion Management Update Component', () => {
        let comp: ApplicationVersionUpdateComponent;
        let fixture: ComponentFixture<ApplicationVersionUpdateComponent>;
        let service: ApplicationVersionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [ApplicationVersionUpdateComponent]
            })
                .overrideTemplate(ApplicationVersionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApplicationVersionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApplicationVersionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ApplicationVersion(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.applicationVersion = entity;
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
                    const entity = new ApplicationVersion();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.applicationVersion = entity;
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
