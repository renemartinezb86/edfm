/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { NetworkParameterUpdateComponent } from 'app/entities/network-parameter/network-parameter-update.component';
import { NetworkParameterService } from 'app/entities/network-parameter/network-parameter.service';
import { NetworkParameter } from 'app/shared/model/network-parameter.model';

describe('Component Tests', () => {
    describe('NetworkParameter Management Update Component', () => {
        let comp: NetworkParameterUpdateComponent;
        let fixture: ComponentFixture<NetworkParameterUpdateComponent>;
        let service: NetworkParameterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [NetworkParameterUpdateComponent]
            })
                .overrideTemplate(NetworkParameterUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NetworkParameterUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NetworkParameterService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new NetworkParameter(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.networkParameter = entity;
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
                    const entity = new NetworkParameter();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.networkParameter = entity;
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
