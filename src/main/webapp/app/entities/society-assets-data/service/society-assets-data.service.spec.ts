import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISocietyAssetsData } from '../society-assets-data.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../society-assets-data.test-samples';

import { SocietyAssetsDataService, RestSocietyAssetsData } from './society-assets-data.service';

const requireRestSample: RestSocietyAssetsData = {
  ...sampleWithRequiredData,
  transactionDate: sampleWithRequiredData.transactionDate?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('SocietyAssetsData Service', () => {
  let service: SocietyAssetsDataService;
  let httpMock: HttpTestingController;
  let expectedResult: ISocietyAssetsData | ISocietyAssetsData[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SocietyAssetsDataService);
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

    it('should create a SocietyAssetsData', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const societyAssetsData = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(societyAssetsData).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SocietyAssetsData', () => {
      const societyAssetsData = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(societyAssetsData).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SocietyAssetsData', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SocietyAssetsData', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SocietyAssetsData', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSocietyAssetsDataToCollectionIfMissing', () => {
      it('should add a SocietyAssetsData to an empty array', () => {
        const societyAssetsData: ISocietyAssetsData = sampleWithRequiredData;
        expectedResult = service.addSocietyAssetsDataToCollectionIfMissing([], societyAssetsData);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societyAssetsData);
      });

      it('should not add a SocietyAssetsData to an array that contains it', () => {
        const societyAssetsData: ISocietyAssetsData = sampleWithRequiredData;
        const societyAssetsDataCollection: ISocietyAssetsData[] = [
          {
            ...societyAssetsData,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSocietyAssetsDataToCollectionIfMissing(societyAssetsDataCollection, societyAssetsData);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SocietyAssetsData to an array that doesn't contain it", () => {
        const societyAssetsData: ISocietyAssetsData = sampleWithRequiredData;
        const societyAssetsDataCollection: ISocietyAssetsData[] = [sampleWithPartialData];
        expectedResult = service.addSocietyAssetsDataToCollectionIfMissing(societyAssetsDataCollection, societyAssetsData);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societyAssetsData);
      });

      it('should add only unique SocietyAssetsData to an array', () => {
        const societyAssetsDataArray: ISocietyAssetsData[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const societyAssetsDataCollection: ISocietyAssetsData[] = [sampleWithRequiredData];
        expectedResult = service.addSocietyAssetsDataToCollectionIfMissing(societyAssetsDataCollection, ...societyAssetsDataArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const societyAssetsData: ISocietyAssetsData = sampleWithRequiredData;
        const societyAssetsData2: ISocietyAssetsData = sampleWithPartialData;
        expectedResult = service.addSocietyAssetsDataToCollectionIfMissing([], societyAssetsData, societyAssetsData2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societyAssetsData);
        expect(expectedResult).toContain(societyAssetsData2);
      });

      it('should accept null and undefined values', () => {
        const societyAssetsData: ISocietyAssetsData = sampleWithRequiredData;
        expectedResult = service.addSocietyAssetsDataToCollectionIfMissing([], null, societyAssetsData, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societyAssetsData);
      });

      it('should return initial array if no SocietyAssetsData is added', () => {
        const societyAssetsDataCollection: ISocietyAssetsData[] = [sampleWithRequiredData];
        expectedResult = service.addSocietyAssetsDataToCollectionIfMissing(societyAssetsDataCollection, undefined, null);
        expect(expectedResult).toEqual(societyAssetsDataCollection);
      });
    });

    describe('compareSocietyAssetsData', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSocietyAssetsData(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSocietyAssetsData(entity1, entity2);
        const compareResult2 = service.compareSocietyAssetsData(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSocietyAssetsData(entity1, entity2);
        const compareResult2 = service.compareSocietyAssetsData(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSocietyAssetsData(entity1, entity2);
        const compareResult2 = service.compareSocietyAssetsData(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
