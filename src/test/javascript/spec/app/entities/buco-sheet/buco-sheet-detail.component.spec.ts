/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { BucoSheetDetailComponent } from 'app/entities/buco-sheet/buco-sheet-detail.component';
import { BucoSheet } from 'app/shared/model/buco-sheet.model';

describe('Component Tests', () => {
    describe('BucoSheet Management Detail Component', () => {
        let comp: BucoSheetDetailComponent;
        let fixture: ComponentFixture<BucoSheetDetailComponent>;
        const route = ({ data: of({ bucoSheet: new BucoSheet(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [BucoSheetDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BucoSheetDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BucoSheetDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bucoSheet).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
