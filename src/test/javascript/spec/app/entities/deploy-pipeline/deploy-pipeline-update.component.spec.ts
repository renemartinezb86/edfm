/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { DeployPipelineUpdateComponent } from 'app/entities/deploy-pipeline/deploy-pipeline-update.component';
import { DeployPipelineService } from 'app/entities/deploy-pipeline/deploy-pipeline.service';
import { DeployPipeline } from 'app/shared/model/deploy-pipeline.model';

describe('Component Tests', () => {
    describe('DeployPipeline Management Update Component', () => {
        let comp: DeployPipelineUpdateComponent;
        let fixture: ComponentFixture<DeployPipelineUpdateComponent>;
        let service: DeployPipelineService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [DeployPipelineUpdateComponent]
            })
                .overrideTemplate(DeployPipelineUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DeployPipelineUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeployPipelineService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DeployPipeline(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.deployPipeline = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DeployPipeline();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.deployPipeline = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
