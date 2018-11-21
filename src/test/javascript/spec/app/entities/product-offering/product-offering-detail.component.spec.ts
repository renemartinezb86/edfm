/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { ProductOfferingDetailComponent } from 'app/entities/product-offering/product-offering-detail.component';
import { ProductOffering } from 'app/shared/model/product-offering.model';

describe('Component Tests', () => {
    describe('ProductOffering Management Detail Component', () => {
        let comp: ProductOfferingDetailComponent;
        let fixture: ComponentFixture<ProductOfferingDetailComponent>;
        const route = ({ data: of({ productOffering: new ProductOffering(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [ProductOfferingDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductOfferingDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductOfferingDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.productOffering).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
