/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { NetworkResourceDetailComponent } from 'app/entities/network-resource/network-resource-detail.component';
import { NetworkResource } from 'app/shared/model/network-resource.model';

describe('Component Tests', () => {
    describe('NetworkResource Management Detail Component', () => {
        let comp: NetworkResourceDetailComponent;
        let fixture: ComponentFixture<NetworkResourceDetailComponent>;
        const route = ({ data: of({ networkResource: new NetworkResource(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [NetworkResourceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(NetworkResourceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NetworkResourceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.networkResource).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
