import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISocietyAssets } from '../society-assets.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../society-assets.test-samples';

import { SocietyAssetsService, RestSocietyAssets } from './society-assets.service';

const requireRestSample: RestSocietyAssets = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('SocietyAssets Service', () => {
  let service: SocietyAssetsService;
  let httpMock: HttpTestingController;
  let expectedResult: ISocietyAssets | ISocietyAssets[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SocietyAssetsService);
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

    it('should create a SocietyAssets', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const societyAssets = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(societyAssets).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SocietyAssets', () => {
      const societyAssets = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(societyAssets).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SocietyAssets', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SocietyAssets', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SocietyAssets', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSocietyAssetsToCollectionIfMissing', () => {
      it('should add a SocietyAssets to an empty array', () => {
        const societyAssets: ISocietyAssets = sampleWithRequiredData;
        expectedResult = service.addSocietyAssetsToCollectionIfMissing([], societyAssets);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societyAssets);
      });

      it('should not add a SocietyAssets to an array that contains it', () => {
        const societyAssets: ISocietyAssets = sampleWithRequiredData;
        const societyAssetsCollection: ISocietyAssets[] = [
          {
            ...societyAssets,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSocietyAssetsToCollectionIfMissing(societyAssetsCollection, societyAssets);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SocietyAssets to an array that doesn't contain it", () => {
        const societyAssets: ISocietyAssets = sampleWithRequiredData;
        const societyAssetsCollection: ISocietyAssets[] = [sampleWithPartialData];
        expectedResult = service.addSocietyAssetsToCollectionIfMissing(societyAssetsCollection, societyAssets);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societyAssets);
      });

      it('should add only unique SocietyAssets to an array', () => {
        const societyAssetsArray: ISocietyAssets[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const societyAssetsCollection: ISocietyAssets[] = [sampleWithRequiredData];
        expectedResult = service.addSocietyAssetsToCollectionIfMissing(societyAssetsCollection, ...societyAssetsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const societyAssets: ISocietyAssets = sampleWithRequiredData;
        const societyAssets2: ISocietyAssets = sampleWithPartialData;
        expectedResult = service.addSocietyAssetsToCollectionIfMissing([], societyAssets, societyAssets2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societyAssets);
        expect(expectedResult).toContain(societyAssets2);
      });

      it('should accept null and undefined values', () => {
        const societyAssets: ISocietyAssets = sampleWithRequiredData;
        expectedResult = service.addSocietyAssetsToCollectionIfMissing([], null, societyAssets, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societyAssets);
      });

      it('should return initial array if no SocietyAssets is added', () => {
        const societyAssetsCollection: ISocietyAssets[] = [sampleWithRequiredData];
        expectedResult = service.addSocietyAssetsToCollectionIfMissing(societyAssetsCollection, undefined, null);
        expect(expectedResult).toEqual(societyAssetsCollection);
      });
    });

    describe('compareSocietyAssets', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSocietyAssets(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSocietyAssets(entity1, entity2);
        const compareResult2 = service.compareSocietyAssets(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSocietyAssets(entity1, entity2);
        const compareResult2 = service.compareSocietyAssets(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSocietyAssets(entity1, entity2);
        const compareResult2 = service.compareSocietyAssets(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
