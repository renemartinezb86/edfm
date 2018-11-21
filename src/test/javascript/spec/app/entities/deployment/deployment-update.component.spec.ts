/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { DeploymentUpdateComponent } from 'app/entities/deployment/deployment-update.component';
import { DeploymentService } from 'app/entities/deployment/deployment.service';
import { Deployment } from 'app/shared/model/deployment.model';

describe('Component Tests', () => {
    describe('Deployment Management Update Component', () => {
        let comp: DeploymentUpdateComponent;
        let fixture: ComponentFixture<DeploymentUpdateComponent>;
        let service: DeploymentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [DeploymentUpdateComponent]
            })
                .overrideTemplate(DeploymentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DeploymentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeploymentService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Deployment(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.deployment = entity;
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
                    const entity = new Deployment();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.deployment = entity;
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
