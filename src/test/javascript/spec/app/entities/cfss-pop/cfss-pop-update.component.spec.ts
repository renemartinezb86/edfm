/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { CfssPopUpdateComponent } from 'app/entities/cfss-pop/cfss-pop-update.component';
import { CfssPopService } from 'app/entities/cfss-pop/cfss-pop.service';
import { CfssPop } from 'app/shared/model/cfss-pop.model';

describe('Component Tests', () => {
    describe('CfssPop Management Update Component', () => {
        let comp: CfssPopUpdateComponent;
        let fixture: ComponentFixture<CfssPopUpdateComponent>;
        let service: CfssPopService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [CfssPopUpdateComponent]
            })
                .overrideTemplate(CfssPopUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CfssPopUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CfssPopService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CfssPop(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cfssPop = entity;
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
                    const entity = new CfssPop();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cfssPop = entity;
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
