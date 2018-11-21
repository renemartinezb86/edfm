/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { DeploymentDetailComponent } from 'app/entities/deployment/deployment-detail.component';
import { Deployment } from 'app/shared/model/deployment.model';

describe('Component Tests', () => {
    describe('Deployment Management Detail Component', () => {
        let comp: DeploymentDetailComponent;
        let fixture: ComponentFixture<DeploymentDetailComponent>;
        const route = ({ data: of({ deployment: new Deployment(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [DeploymentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DeploymentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DeploymentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.deployment).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
