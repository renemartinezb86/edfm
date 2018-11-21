/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { ApplicationVersionRelationDetailComponent } from 'app/entities/application-version-relation/application-version-relation-detail.component';
import { ApplicationVersionRelation } from 'app/shared/model/application-version-relation.model';

describe('Component Tests', () => {
    describe('ApplicationVersionRelation Management Detail Component', () => {
        let comp: ApplicationVersionRelationDetailComponent;
        let fixture: ComponentFixture<ApplicationVersionRelationDetailComponent>;
        const route = ({ data: of({ applicationVersionRelation: new ApplicationVersionRelation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [ApplicationVersionRelationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ApplicationVersionRelationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApplicationVersionRelationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.applicationVersionRelation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
