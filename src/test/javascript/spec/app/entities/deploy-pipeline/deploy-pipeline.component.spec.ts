/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EdfmTestModule } from '../../../test.module';
import { DeployPipelineComponent } from 'app/entities/deploy-pipeline/deploy-pipeline.component';
import { DeployPipelineService } from 'app/entities/deploy-pipeline/deploy-pipeline.service';
import { DeployPipeline } from 'app/shared/model/deploy-pipeline.model';

describe('Component Tests', () => {
    describe('DeployPipeline Management Component', () => {
        let comp: DeployPipelineComponent;
        let fixture: ComponentFixture<DeployPipelineComponent>;
        let service: DeployPipelineService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [DeployPipelineComponent],
                providers: []
            })
                .overrideTemplate(DeployPipelineComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DeployPipelineComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeployPipelineService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DeployPipeline(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.deployPipelines[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
