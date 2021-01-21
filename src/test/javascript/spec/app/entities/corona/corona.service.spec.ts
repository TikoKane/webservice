import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CoronaService } from 'app/entities/corona/corona.service';
import { ICorona, Corona } from 'app/shared/model/corona.model';

describe('Service Tests', () => {
  describe('Corona Service', () => {
    let injector: TestBed;
    let service: CoronaService;
    let httpMock: HttpTestingController;
    let elemDefault: ICorona;
    let expectedResult: ICorona | ICorona[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CoronaService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Corona(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Corona', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Corona()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Corona', () => {
        const returnedFromService = Object.assign(
          {
            nombrecasparjour: 'BBBBBB',
            caspositif: 'BBBBBB',
            cascommunautaire: 'BBBBBB',
            casgrave: 'BBBBBB',
            guerison: 'BBBBBB',
            deces: 'BBBBBB',
            cascontact: 'BBBBBB',
            casimporte: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Corona', () => {
        const returnedFromService = Object.assign(
          {
            nombrecasparjour: 'BBBBBB',
            caspositif: 'BBBBBB',
            cascommunautaire: 'BBBBBB',
            casgrave: 'BBBBBB',
            guerison: 'BBBBBB',
            deces: 'BBBBBB',
            cascontact: 'BBBBBB',
            casimporte: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Corona', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
