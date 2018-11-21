/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { ApplicationVersionDetailComponent } from 'app/entities/application-version/application-version-detail.component';
import { ApplicationVersion } from 'app/shared/model/application-version.model';

describe('Component Tests', () => {
    describe('ApplicationVersion Management Detail Component', () => {
        let comp: ApplicationVersionDetailComponent;
        let fixture: ComponentFixture<ApplicationVersionDetailComponent>;
        const route = ({ data: of({ applicationVersion: new ApplicationVersion(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [ApplicationVersionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ApplicationVersionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApplicationVersionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.applicationVersion).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
