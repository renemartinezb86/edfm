/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { EnvironmentTypeDetailComponent } from 'app/entities/environment-type/environment-type-detail.component';
import { EnvironmentType } from 'app/shared/model/environment-type.model';

describe('Component Tests', () => {
    describe('EnvironmentType Management Detail Component', () => {
        let comp: EnvironmentTypeDetailComponent;
        let fixture: ComponentFixture<EnvironmentTypeDetailComponent>;
        const route = ({ data: of({ environmentType: new EnvironmentType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [EnvironmentTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EnvironmentTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EnvironmentTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.environmentType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
