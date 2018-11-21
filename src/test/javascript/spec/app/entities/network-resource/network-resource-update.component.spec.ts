/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { NetworkResourceUpdateComponent } from 'app/entities/network-resource/network-resource-update.component';
import { NetworkResourceService } from 'app/entities/network-resource/network-resource.service';
import { NetworkResource } from 'app/shared/model/network-resource.model';

describe('Component Tests', () => {
    describe('NetworkResource Management Update Component', () => {
        let comp: NetworkResourceUpdateComponent;
        let fixture: ComponentFixture<NetworkResourceUpdateComponent>;
        let service: NetworkResourceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [NetworkResourceUpdateComponent]
            })
                .overrideTemplate(NetworkResourceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NetworkResourceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NetworkResourceService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new NetworkResource(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.networkResource = entity;
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
                    const entity = new NetworkResource();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.networkResource = entity;
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
