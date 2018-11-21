/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EdfmTestModule } from '../../../test.module';
import { ApplicationVersionRelationComponent } from 'app/entities/application-version-relation/application-version-relation.component';
import { ApplicationVersionRelationService } from 'app/entities/application-version-relation/application-version-relation.service';
import { ApplicationVersionRelation } from 'app/shared/model/application-version-relation.model';

describe('Component Tests', () => {
    describe('ApplicationVersionRelation Management Component', () => {
        let comp: ApplicationVersionRelationComponent;
        let fixture: ComponentFixture<ApplicationVersionRelationComponent>;
        let service: ApplicationVersionRelationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [ApplicationVersionRelationComponent],
                providers: []
            })
                .overrideTemplate(ApplicationVersionRelationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApplicationVersionRelationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApplicationVersionRelationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ApplicationVersionRelation(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.applicationVersionRelations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
