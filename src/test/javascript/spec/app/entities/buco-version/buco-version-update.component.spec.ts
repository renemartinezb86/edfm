/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { BucoVersionUpdateComponent } from 'app/entities/buco-version/buco-version-update.component';
import { BucoVersionService } from 'app/entities/buco-version/buco-version.service';
import { BucoVersion } from 'app/shared/model/buco-version.model';

describe('Component Tests', () => {
    describe('BucoVersion Management Update Component', () => {
        let comp: BucoVersionUpdateComponent;
        let fixture: ComponentFixture<BucoVersionUpdateComponent>;
        let service: BucoVersionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [BucoVersionUpdateComponent]
            })
                .overrideTemplate(BucoVersionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BucoVersionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BucoVersionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BucoVersion(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bucoVersion = entity;
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
                    const entity = new BucoVersion();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bucoVersion = entity;
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
