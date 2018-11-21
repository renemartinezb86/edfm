/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { BucoSheetUpdateComponent } from 'app/entities/buco-sheet/buco-sheet-update.component';
import { BucoSheetService } from 'app/entities/buco-sheet/buco-sheet.service';
import { BucoSheet } from 'app/shared/model/buco-sheet.model';

describe('Component Tests', () => {
    describe('BucoSheet Management Update Component', () => {
        let comp: BucoSheetUpdateComponent;
        let fixture: ComponentFixture<BucoSheetUpdateComponent>;
        let service: BucoSheetService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [BucoSheetUpdateComponent]
            })
                .overrideTemplate(BucoSheetUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BucoSheetUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BucoSheetService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BucoSheet(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bucoSheet = entity;
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
                    const entity = new BucoSheet();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bucoSheet = entity;
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
