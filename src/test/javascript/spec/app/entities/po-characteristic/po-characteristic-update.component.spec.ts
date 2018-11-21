/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { POCharacteristicUpdateComponent } from 'app/entities/po-characteristic/po-characteristic-update.component';
import { POCharacteristicService } from 'app/entities/po-characteristic/po-characteristic.service';
import { POCharacteristic } from 'app/shared/model/po-characteristic.model';

describe('Component Tests', () => {
    describe('POCharacteristic Management Update Component', () => {
        let comp: POCharacteristicUpdateComponent;
        let fixture: ComponentFixture<POCharacteristicUpdateComponent>;
        let service: POCharacteristicService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [POCharacteristicUpdateComponent]
            })
                .overrideTemplate(POCharacteristicUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(POCharacteristicUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(POCharacteristicService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new POCharacteristic(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pOCharacteristic = entity;
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
                    const entity = new POCharacteristic();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pOCharacteristic = entity;
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
