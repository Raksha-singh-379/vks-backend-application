import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILoanWatapDetails } from '../loan-watap-details.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../loan-watap-details.test-samples';

import { LoanWatapDetailsService, RestLoanWatapDetails } from './loan-watap-details.service';

const requireRestSample: RestLoanWatapDetails = {
  ...sampleWithRequiredData,
  loanWatapDate: sampleWithRequiredData.loanWatapDate?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('LoanWatapDetails Service', () => {
  let service: LoanWatapDetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: ILoanWatapDetails | ILoanWatapDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LoanWatapDetailsService);
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

    it('should create a LoanWatapDetails', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const loanWatapDetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(loanWatapDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LoanWatapDetails', () => {
      const loanWatapDetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(loanWatapDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LoanWatapDetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LoanWatapDetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LoanWatapDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLoanWatapDetailsToCollectionIfMissing', () => {
      it('should add a LoanWatapDetails to an empty array', () => {
        const loanWatapDetails: ILoanWatapDetails = sampleWithRequiredData;
        expectedResult = service.addLoanWatapDetailsToCollectionIfMissing([], loanWatapDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loanWatapDetails);
      });

      it('should not add a LoanWatapDetails to an array that contains it', () => {
        const loanWatapDetails: ILoanWatapDetails = sampleWithRequiredData;
        const loanWatapDetailsCollection: ILoanWatapDetails[] = [
          {
            ...loanWatapDetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLoanWatapDetailsToCollectionIfMissing(loanWatapDetailsCollection, loanWatapDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LoanWatapDetails to an array that doesn't contain it", () => {
        const loanWatapDetails: ILoanWatapDetails = sampleWithRequiredData;
        const loanWatapDetailsCollection: ILoanWatapDetails[] = [sampleWithPartialData];
        expectedResult = service.addLoanWatapDetailsToCollectionIfMissing(loanWatapDetailsCollection, loanWatapDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loanWatapDetails);
      });

      it('should add only unique LoanWatapDetails to an array', () => {
        const loanWatapDetailsArray: ILoanWatapDetails[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const loanWatapDetailsCollection: ILoanWatapDetails[] = [sampleWithRequiredData];
        expectedResult = service.addLoanWatapDetailsToCollectionIfMissing(loanWatapDetailsCollection, ...loanWatapDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const loanWatapDetails: ILoanWatapDetails = sampleWithRequiredData;
        const loanWatapDetails2: ILoanWatapDetails = sampleWithPartialData;
        expectedResult = service.addLoanWatapDetailsToCollectionIfMissing([], loanWatapDetails, loanWatapDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loanWatapDetails);
        expect(expectedResult).toContain(loanWatapDetails2);
      });

      it('should accept null and undefined values', () => {
        const loanWatapDetails: ILoanWatapDetails = sampleWithRequiredData;
        expectedResult = service.addLoanWatapDetailsToCollectionIfMissing([], null, loanWatapDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loanWatapDetails);
      });

      it('should return initial array if no LoanWatapDetails is added', () => {
        const loanWatapDetailsCollection: ILoanWatapDetails[] = [sampleWithRequiredData];
        expectedResult = service.addLoanWatapDetailsToCollectionIfMissing(loanWatapDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(loanWatapDetailsCollection);
      });
    });

    describe('compareLoanWatapDetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLoanWatapDetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLoanWatapDetails(entity1, entity2);
        const compareResult2 = service.compareLoanWatapDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLoanWatapDetails(entity1, entity2);
        const compareResult2 = service.compareLoanWatapDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLoanWatapDetails(entity1, entity2);
        const compareResult2 = service.compareLoanWatapDetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
