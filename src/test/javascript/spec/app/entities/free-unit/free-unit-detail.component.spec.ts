/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { FreeUnitDetailComponent } from 'app/entities/free-unit/free-unit-detail.component';
import { FreeUnit } from 'app/shared/model/free-unit.model';

describe('Component Tests', () => {
    describe('FreeUnit Management Detail Component', () => {
        let comp: FreeUnitDetailComponent;
        let fixture: ComponentFixture<FreeUnitDetailComponent>;
        const route = ({ data: of({ freeUnit: new FreeUnit(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [FreeUnitDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FreeUnitDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FreeUnitDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.freeUnit).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
