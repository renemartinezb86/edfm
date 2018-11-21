/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { ProductOfferingUpdateComponent } from 'app/entities/product-offering/product-offering-update.component';
import { ProductOfferingService } from 'app/entities/product-offering/product-offering.service';
import { ProductOffering } from 'app/shared/model/product-offering.model';

describe('Component Tests', () => {
    describe('ProductOffering Management Update Component', () => {
        let comp: ProductOfferingUpdateComponent;
        let fixture: ComponentFixture<ProductOfferingUpdateComponent>;
        let service: ProductOfferingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [ProductOfferingUpdateComponent]
            })
                .overrideTemplate(ProductOfferingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductOfferingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductOfferingService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProductOffering(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.productOffering = entity;
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
                    const entity = new ProductOffering();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.productOffering = entity;
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
