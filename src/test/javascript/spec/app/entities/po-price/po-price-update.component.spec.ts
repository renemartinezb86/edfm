/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { PoPriceUpdateComponent } from 'app/entities/po-price/po-price-update.component';
import { PoPriceService } from 'app/entities/po-price/po-price.service';
import { PoPrice } from 'app/shared/model/po-price.model';

describe('Component Tests', () => {
    describe('PoPrice Management Update Component', () => {
        let comp: PoPriceUpdateComponent;
        let fixture: ComponentFixture<PoPriceUpdateComponent>;
        let service: PoPriceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [PoPriceUpdateComponent]
            })
                .overrideTemplate(PoPriceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PoPriceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PoPriceService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PoPrice(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.poPrice = entity;
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
                    const entity = new PoPrice();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.poPrice = entity;
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
