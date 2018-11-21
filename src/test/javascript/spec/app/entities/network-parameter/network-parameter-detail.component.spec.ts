/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { NetworkParameterDetailComponent } from 'app/entities/network-parameter/network-parameter-detail.component';
import { NetworkParameter } from 'app/shared/model/network-parameter.model';

describe('Component Tests', () => {
    describe('NetworkParameter Management Detail Component', () => {
        let comp: NetworkParameterDetailComponent;
        let fixture: ComponentFixture<NetworkParameterDetailComponent>;
        const route = ({ data: of({ networkParameter: new NetworkParameter(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [NetworkParameterDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(NetworkParameterDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NetworkParameterDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.networkParameter).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
