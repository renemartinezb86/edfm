/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { BscsDetailComponent } from 'app/entities/bscs/bscs-detail.component';
import { Bscs } from 'app/shared/model/bscs.model';

describe('Component Tests', () => {
    describe('Bscs Management Detail Component', () => {
        let comp: BscsDetailComponent;
        let fixture: ComponentFixture<BscsDetailComponent>;
        const route = ({ data: of({ bscs: new Bscs(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [BscsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BscsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BscsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bscs).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
