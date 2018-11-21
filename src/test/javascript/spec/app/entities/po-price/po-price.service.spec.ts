/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { PoPriceService } from 'app/entities/po-price/po-price.service';
import { IPoPrice, PoPrice, PoPriceType, PaymentType } from 'app/shared/model/po-price.model';

describe('Service Tests', () => {
    describe('PoPrice Service', () => {
        let injector: TestBed;
        let service: PoPriceService;
        let httpMock: HttpTestingController;
        let elemDefault: IPoPrice;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PoPriceService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new PoPrice(0, PoPriceType.Recurring, 0, PaymentType.Cash, false, false, false, false);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a PoPrice', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new PoPrice(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a PoPrice', async () => {
                const returnedFromService = Object.assign(
                    {
                        poPriceType: 'BBBBBB',
                        amount: 1,
                        paymentType: 'BBBBBB',
                        showInBill: true,
                        payInAdvance: true,
                        billOnSuspension: true,
                        multiDiscount: true
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of PoPrice', async () => {
                const returnedFromService = Object.assign(
                    {
                        poPriceType: 'BBBBBB',
                        amount: 1,
                        paymentType: 'BBBBBB',
                        showInBill: true,
                        payInAdvance: true,
                        billOnSuspension: true,
                        multiDiscount: true
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a PoPrice', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
