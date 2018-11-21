/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EdfmTestModule } from '../../../test.module';
import { DeploymentPipelineLogComponent } from 'app/entities/deployment-pipeline-log/deployment-pipeline-log.component';
import { DeploymentPipelineLogService } from 'app/entities/deployment-pipeline-log/deployment-pipeline-log.service';
import { DeploymentPipelineLog } from 'app/shared/model/deployment-pipeline-log.model';

describe('Component Tests', () => {
    describe('DeploymentPipelineLog Management Component', () => {
        let comp: DeploymentPipelineLogComponent;
        let fixture: ComponentFixture<DeploymentPipelineLogComponent>;
        let service: DeploymentPipelineLogService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [DeploymentPipelineLogComponent],
                providers: []
            })
                .overrideTemplate(DeploymentPipelineLogComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DeploymentPipelineLogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeploymentPipelineLogService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DeploymentPipelineLog(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.deploymentPipelineLogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
