import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILoanDisbursementDetails } from '../loan-disbursement-details.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../loan-disbursement-details.test-samples';

import { LoanDisbursementDetailsService, RestLoanDisbursementDetails } from './loan-disbursement-details.service';

const requireRestSample: RestLoanDisbursementDetails = {
  ...sampleWithRequiredData,
  disbursementDate: sampleWithRequiredData.disbursementDate?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('LoanDisbursementDetails Service', () => {
  let service: LoanDisbursementDetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: ILoanDisbursementDetails | ILoanDisbursementDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LoanDisbursementDetailsService);
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

    it('should create a LoanDisbursementDetails', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const loanDisbursementDetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(loanDisbursementDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LoanDisbursementDetails', () => {
      const loanDisbursementDetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(loanDisbursementDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LoanDisbursementDetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LoanDisbursementDetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LoanDisbursementDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLoanDisbursementDetailsToCollectionIfMissing', () => {
      it('should add a LoanDisbursementDetails to an empty array', () => {
        const loanDisbursementDetails: ILoanDisbursementDetails = sampleWithRequiredData;
        expectedResult = service.addLoanDisbursementDetailsToCollectionIfMissing([], loanDisbursementDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loanDisbursementDetails);
      });

      it('should not add a LoanDisbursementDetails to an array that contains it', () => {
        const loanDisbursementDetails: ILoanDisbursementDetails = sampleWithRequiredData;
        const loanDisbursementDetailsCollection: ILoanDisbursementDetails[] = [
          {
            ...loanDisbursementDetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLoanDisbursementDetailsToCollectionIfMissing(
          loanDisbursementDetailsCollection,
          loanDisbursementDetails
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LoanDisbursementDetails to an array that doesn't contain it", () => {
        const loanDisbursementDetails: ILoanDisbursementDetails = sampleWithRequiredData;
        const loanDisbursementDetailsCollection: ILoanDisbursementDetails[] = [sampleWithPartialData];
        expectedResult = service.addLoanDisbursementDetailsToCollectionIfMissing(
          loanDisbursementDetailsCollection,
          loanDisbursementDetails
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loanDisbursementDetails);
      });

      it('should add only unique LoanDisbursementDetails to an array', () => {
        const loanDisbursementDetailsArray: ILoanDisbursementDetails[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const loanDisbursementDetailsCollection: ILoanDisbursementDetails[] = [sampleWithRequiredData];
        expectedResult = service.addLoanDisbursementDetailsToCollectionIfMissing(
          loanDisbursementDetailsCollection,
          ...loanDisbursementDetailsArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const loanDisbursementDetails: ILoanDisbursementDetails = sampleWithRequiredData;
        const loanDisbursementDetails2: ILoanDisbursementDetails = sampleWithPartialData;
        expectedResult = service.addLoanDisbursementDetailsToCollectionIfMissing([], loanDisbursementDetails, loanDisbursementDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loanDisbursementDetails);
        expect(expectedResult).toContain(loanDisbursementDetails2);
      });

      it('should accept null and undefined values', () => {
        const loanDisbursementDetails: ILoanDisbursementDetails = sampleWithRequiredData;
        expectedResult = service.addLoanDisbursementDetailsToCollectionIfMissing([], null, loanDisbursementDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loanDisbursementDetails);
      });

      it('should return initial array if no LoanDisbursementDetails is added', () => {
        const loanDisbursementDetailsCollection: ILoanDisbursementDetails[] = [sampleWithRequiredData];
        expectedResult = service.addLoanDisbursementDetailsToCollectionIfMissing(loanDisbursementDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(loanDisbursementDetailsCollection);
      });
    });

    describe('compareLoanDisbursementDetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLoanDisbursementDetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLoanDisbursementDetails(entity1, entity2);
        const compareResult2 = service.compareLoanDisbursementDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLoanDisbursementDetails(entity1, entity2);
        const compareResult2 = service.compareLoanDisbursementDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLoanDisbursementDetails(entity1, entity2);
        const compareResult2 = service.compareLoanDisbursementDetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
