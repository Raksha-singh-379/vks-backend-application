import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBankDhoranDetails } from '../bank-dhoran-details.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../bank-dhoran-details.test-samples';

import { BankDhoranDetailsService, RestBankDhoranDetails } from './bank-dhoran-details.service';

const requireRestSample: RestBankDhoranDetails = {
  ...sampleWithRequiredData,
  startDate: sampleWithRequiredData.startDate?.toJSON(),
  endDate: sampleWithRequiredData.endDate?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('BankDhoranDetails Service', () => {
  let service: BankDhoranDetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: IBankDhoranDetails | IBankDhoranDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(BankDhoranDetailsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a BankDhoranDetails', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const bankDhoranDetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(bankDhoranDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a BankDhoranDetails', () => {
      const bankDhoranDetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(bankDhoranDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a BankDhoranDetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of BankDhoranDetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a BankDhoranDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addBankDhoranDetailsToCollectionIfMissing', () => {
      it('should add a BankDhoranDetails to an empty array', () => {
        const bankDhoranDetails: IBankDhoranDetails = sampleWithRequiredData;
        expectedResult = service.addBankDhoranDetailsToCollectionIfMissing([], bankDhoranDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bankDhoranDetails);
      });

      it('should not add a BankDhoranDetails to an array that contains it', () => {
        const bankDhoranDetails: IBankDhoranDetails = sampleWithRequiredData;
        const bankDhoranDetailsCollection: IBankDhoranDetails[] = [
          {
            ...bankDhoranDetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addBankDhoranDetailsToCollectionIfMissing(bankDhoranDetailsCollection, bankDhoranDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a BankDhoranDetails to an array that doesn't contain it", () => {
        const bankDhoranDetails: IBankDhoranDetails = sampleWithRequiredData;
        const bankDhoranDetailsCollection: IBankDhoranDetails[] = [sampleWithPartialData];
        expectedResult = service.addBankDhoranDetailsToCollectionIfMissing(bankDhoranDetailsCollection, bankDhoranDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bankDhoranDetails);
      });

      it('should add only unique BankDhoranDetails to an array', () => {
        const bankDhoranDetailsArray: IBankDhoranDetails[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const bankDhoranDetailsCollection: IBankDhoranDetails[] = [sampleWithRequiredData];
        expectedResult = service.addBankDhoranDetailsToCollectionIfMissing(bankDhoranDetailsCollection, ...bankDhoranDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const bankDhoranDetails: IBankDhoranDetails = sampleWithRequiredData;
        const bankDhoranDetails2: IBankDhoranDetails = sampleWithPartialData;
        expectedResult = service.addBankDhoranDetailsToCollectionIfMissing([], bankDhoranDetails, bankDhoranDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(bankDhoranDetails);
        expect(expectedResult).toContain(bankDhoranDetails2);
      });

      it('should accept null and undefined values', () => {
        const bankDhoranDetails: IBankDhoranDetails = sampleWithRequiredData;
        expectedResult = service.addBankDhoranDetailsToCollectionIfMissing([], null, bankDhoranDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(bankDhoranDetails);
      });

      it('should return initial array if no BankDhoranDetails is added', () => {
        const bankDhoranDetailsCollection: IBankDhoranDetails[] = [sampleWithRequiredData];
        expectedResult = service.addBankDhoranDetailsToCollectionIfMissing(bankDhoranDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(bankDhoranDetailsCollection);
      });
    });

    describe('compareBankDhoranDetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareBankDhoranDetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareBankDhoranDetails(entity1, entity2);
        const compareResult2 = service.compareBankDhoranDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareBankDhoranDetails(entity1, entity2);
        const compareResult2 = service.compareBankDhoranDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareBankDhoranDetails(entity1, entity2);
        const compareResult2 = service.compareBankDhoranDetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
