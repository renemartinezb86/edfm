/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EdfmTestModule } from '../../../test.module';
import { ApplicationVersionComponent } from 'app/entities/application-version/application-version.component';
import { ApplicationVersionService } from 'app/entities/application-version/application-version.service';
import { ApplicationVersion } from 'app/shared/model/application-version.model';

describe('Component Tests', () => {
    describe('ApplicationVersion Management Component', () => {
        let comp: ApplicationVersionComponent;
        let fixture: ComponentFixture<ApplicationVersionComponent>;
        let service: ApplicationVersionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [ApplicationVersionComponent],
                providers: []
            })
                .overrideTemplate(ApplicationVersionComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApplicationVersionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApplicationVersionService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ApplicationVersion(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.applicationVersions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
