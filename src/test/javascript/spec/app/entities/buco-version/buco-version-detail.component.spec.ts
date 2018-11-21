/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { BucoVersionDetailComponent } from 'app/entities/buco-version/buco-version-detail.component';
import { BucoVersion } from 'app/shared/model/buco-version.model';

describe('Component Tests', () => {
    describe('BucoVersion Management Detail Component', () => {
        let comp: BucoVersionDetailComponent;
        let fixture: ComponentFixture<BucoVersionDetailComponent>;
        const route = ({ data: of({ bucoVersion: new BucoVersion(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [BucoVersionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BucoVersionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BucoVersionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bucoVersion).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
