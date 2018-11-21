/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { DeploymentPipelineLogDetailComponent } from 'app/entities/deployment-pipeline-log/deployment-pipeline-log-detail.component';
import { DeploymentPipelineLog } from 'app/shared/model/deployment-pipeline-log.model';

describe('Component Tests', () => {
    describe('DeploymentPipelineLog Management Detail Component', () => {
        let comp: DeploymentPipelineLogDetailComponent;
        let fixture: ComponentFixture<DeploymentPipelineLogDetailComponent>;
        const route = ({ data: of({ deploymentPipelineLog: new DeploymentPipelineLog(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [DeploymentPipelineLogDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DeploymentPipelineLogDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DeploymentPipelineLogDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.deploymentPipelineLog).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
