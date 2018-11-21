/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EdfmTestModule } from '../../../test.module';
import { EnvironmentTypeComponent } from 'app/entities/environment-type/environment-type.component';
import { EnvironmentTypeService } from 'app/entities/environment-type/environment-type.service';
import { EnvironmentType } from 'app/shared/model/environment-type.model';

describe('Component Tests', () => {
    describe('EnvironmentType Management Component', () => {
        let comp: EnvironmentTypeComponent;
        let fixture: ComponentFixture<EnvironmentTypeComponent>;
        let service: EnvironmentTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [EnvironmentTypeComponent],
                providers: []
            })
                .overrideTemplate(EnvironmentTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EnvironmentTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnvironmentTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new EnvironmentType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.environmentTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
