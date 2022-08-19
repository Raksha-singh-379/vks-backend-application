import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILoanDetails } from '../loan-details.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../loan-details.test-samples';

import { LoanDetailsService, RestLoanDetails } from './loan-details.service';

const requireRestSample: RestLoanDetails = {
  ...sampleWithRequiredData,
  loanStartDate: sampleWithRequiredData.loanStartDate?.toJSON(),
  loanEndDate: sampleWithRequiredData.loanEndDate?.toJSON(),
  loanPlannedClosureDate: sampleWithRequiredData.loanPlannedClosureDate?.toJSON(),
  loanCloserDate: sampleWithRequiredData.loanCloserDate?.toJSON(),
  loanEffectiveDate: sampleWithRequiredData.loanEffectiveDate?.toJSON(),
  resolutionDate: sampleWithRequiredData.resolutionDate?.toJSON(),
  mortgageDate: sampleWithRequiredData.mortgageDate?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('LoanDetails Service', () => {
  let service: LoanDetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: ILoanDetails | ILoanDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LoanDetailsService);
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

    it('should create a LoanDetails', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const loanDetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(loanDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LoanDetails', () => {
      const loanDetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(loanDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LoanDetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LoanDetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LoanDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLoanDetailsToCollectionIfMissing', () => {
      it('should add a LoanDetails to an empty array', () => {
        const loanDetails: ILoanDetails = sampleWithRequiredData;
        expectedResult = service.addLoanDetailsToCollectionIfMissing([], loanDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loanDetails);
      });

      it('should not add a LoanDetails to an array that contains it', () => {
        const loanDetails: ILoanDetails = sampleWithRequiredData;
        const loanDetailsCollection: ILoanDetails[] = [
          {
            ...loanDetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLoanDetailsToCollectionIfMissing(loanDetailsCollection, loanDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LoanDetails to an array that doesn't contain it", () => {
        const loanDetails: ILoanDetails = sampleWithRequiredData;
        const loanDetailsCollection: ILoanDetails[] = [sampleWithPartialData];
        expectedResult = service.addLoanDetailsToCollectionIfMissing(loanDetailsCollection, loanDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loanDetails);
      });

      it('should add only unique LoanDetails to an array', () => {
        const loanDetailsArray: ILoanDetails[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const loanDetailsCollection: ILoanDetails[] = [sampleWithRequiredData];
        expectedResult = service.addLoanDetailsToCollectionIfMissing(loanDetailsCollection, ...loanDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const loanDetails: ILoanDetails = sampleWithRequiredData;
        const loanDetails2: ILoanDetails = sampleWithPartialData;
        expectedResult = service.addLoanDetailsToCollectionIfMissing([], loanDetails, loanDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(loanDetails);
        expect(expectedResult).toContain(loanDetails2);
      });

      it('should accept null and undefined values', () => {
        const loanDetails: ILoanDetails = sampleWithRequiredData;
        expectedResult = service.addLoanDetailsToCollectionIfMissing([], null, loanDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(loanDetails);
      });

      it('should return initial array if no LoanDetails is added', () => {
        const loanDetailsCollection: ILoanDetails[] = [sampleWithRequiredData];
        expectedResult = service.addLoanDetailsToCollectionIfMissing(loanDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(loanDetailsCollection);
      });
    });

    describe('compareLoanDetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLoanDetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLoanDetails(entity1, entity2);
        const compareResult2 = service.compareLoanDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLoanDetails(entity1, entity2);
        const compareResult2 = service.compareLoanDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLoanDetails(entity1, entity2);
        const compareResult2 = service.compareLoanDetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
