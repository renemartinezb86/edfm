/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EdfmTestModule } from '../../../test.module';
import { POCharacteristicDetailComponent } from 'app/entities/po-characteristic/po-characteristic-detail.component';
import { POCharacteristic } from 'app/shared/model/po-characteristic.model';

describe('Component Tests', () => {
    describe('POCharacteristic Management Detail Component', () => {
        let comp: POCharacteristicDetailComponent;
        let fixture: ComponentFixture<POCharacteristicDetailComponent>;
        const route = ({ data: of({ pOCharacteristic: new POCharacteristic(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [EdfmTestModule],
                declarations: [POCharacteristicDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(POCharacteristicDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(POCharacteristicDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.pOCharacteristic).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
