/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EdfmTestModule } from '../../../test.module';
import { EnvironmentComponent } from 'app/entities/environment/environment.component';
import { EnvironmentService } from 'app/entities/environment/environment.service';
import { Environment } from 'app/shared/model/environment.model';

describe('Component Tests', () => {
    describe('Environment Management Component', () => {
        let comp: EnvironmentComponent;
        let fixture: ComponentFixture<EnvironmentComponent>;
        let service: EnvironmentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [EnvironmentComponent],
                providers: []
            })
                .overrideTemplate(EnvironmentComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EnvironmentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnvironmentService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Environment(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.environments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
