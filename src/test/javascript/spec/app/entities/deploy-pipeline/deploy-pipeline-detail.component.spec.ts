/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { DeployPipelineDetailComponent } from 'app/entities/deploy-pipeline/deploy-pipeline-detail.component';
import { DeployPipeline } from 'app/shared/model/deploy-pipeline.model';

describe('Component Tests', () => {
    describe('DeployPipeline Management Detail Component', () => {
        let comp: DeployPipelineDetailComponent;
        let fixture: ComponentFixture<DeployPipelineDetailComponent>;
        const route = ({ data: of({ deployPipeline: new DeployPipeline(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [DeployPipelineDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DeployPipelineDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DeployPipelineDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.deployPipeline).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
