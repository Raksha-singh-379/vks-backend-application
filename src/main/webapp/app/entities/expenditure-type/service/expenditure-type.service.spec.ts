import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IExpenditureType } from '../expenditure-type.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../expenditure-type.test-samples';

import { ExpenditureTypeService, RestExpenditureType } from './expenditure-type.service';

const requireRestSample: RestExpenditureType = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('ExpenditureType Service', () => {
  let service: ExpenditureTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: IExpenditureType | IExpenditureType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ExpenditureTypeService);
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

    it('should create a ExpenditureType', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const expenditureType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(expenditureType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ExpenditureType', () => {
      const expenditureType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(expenditureType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ExpenditureType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ExpenditureType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ExpenditureType', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addExpenditureTypeToCollectionIfMissing', () => {
      it('should add a ExpenditureType to an empty array', () => {
        const expenditureType: IExpenditureType = sampleWithRequiredData;
        expectedResult = service.addExpenditureTypeToCollectionIfMissing([], expenditureType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(expenditureType);
      });

      it('should not add a ExpenditureType to an array that contains it', () => {
        const expenditureType: IExpenditureType = sampleWithRequiredData;
        const expenditureTypeCollection: IExpenditureType[] = [
          {
            ...expenditureType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addExpenditureTypeToCollectionIfMissing(expenditureTypeCollection, expenditureType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ExpenditureType to an array that doesn't contain it", () => {
        const expenditureType: IExpenditureType = sampleWithRequiredData;
        const expenditureTypeCollection: IExpenditureType[] = [sampleWithPartialData];
        expectedResult = service.addExpenditureTypeToCollectionIfMissing(expenditureTypeCollection, expenditureType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(expenditureType);
      });

      it('should add only unique ExpenditureType to an array', () => {
        const expenditureTypeArray: IExpenditureType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const expenditureTypeCollection: IExpenditureType[] = [sampleWithRequiredData];
        expectedResult = service.addExpenditureTypeToCollectionIfMissing(expenditureTypeCollection, ...expenditureTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const expenditureType: IExpenditureType = sampleWithRequiredData;
        const expenditureType2: IExpenditureType = sampleWithPartialData;
        expectedResult = service.addExpenditureTypeToCollectionIfMissing([], expenditureType, expenditureType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(expenditureType);
        expect(expectedResult).toContain(expenditureType2);
      });

      it('should accept null and undefined values', () => {
        const expenditureType: IExpenditureType = sampleWithRequiredData;
        expectedResult = service.addExpenditureTypeToCollectionIfMissing([], null, expenditureType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(expenditureType);
      });

      it('should return initial array if no ExpenditureType is added', () => {
        const expenditureTypeCollection: IExpenditureType[] = [sampleWithRequiredData];
        expectedResult = service.addExpenditureTypeToCollectionIfMissing(expenditureTypeCollection, undefined, null);
        expect(expectedResult).toEqual(expenditureTypeCollection);
      });
    });

    describe('compareExpenditureType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareExpenditureType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareExpenditureType(entity1, entity2);
        const compareResult2 = service.compareExpenditureType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareExpenditureType(entity1, entity2);
        const compareResult2 = service.compareExpenditureType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareExpenditureType(entity1, entity2);
        const compareResult2 = service.compareExpenditureType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
