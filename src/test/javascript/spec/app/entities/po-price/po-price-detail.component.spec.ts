/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { PoPriceDetailComponent } from 'app/entities/po-price/po-price-detail.component';
import { PoPrice } from 'app/shared/model/po-price.model';

describe('Component Tests', () => {
    describe('PoPrice Management Detail Component', () => {
        let comp: PoPriceDetailComponent;
        let fixture: ComponentFixture<PoPriceDetailComponent>;
        const route = ({ data: of({ poPrice: new PoPrice(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [PoPriceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PoPriceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PoPriceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.poPrice).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
