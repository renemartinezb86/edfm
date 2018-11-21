/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { CfssPopDetailComponent } from 'app/entities/cfss-pop/cfss-pop-detail.component';
import { CfssPop } from 'app/shared/model/cfss-pop.model';

describe('Component Tests', () => {
    describe('CfssPop Management Detail Component', () => {
        let comp: CfssPopDetailComponent;
        let fixture: ComponentFixture<CfssPopDetailComponent>;
        const route = ({ data: of({ cfssPop: new CfssPop(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [CfssPopDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CfssPopDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CfssPopDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cfssPop).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
