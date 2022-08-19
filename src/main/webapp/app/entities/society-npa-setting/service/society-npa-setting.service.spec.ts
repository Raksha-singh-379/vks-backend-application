import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISocietyNpaSetting } from '../society-npa-setting.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../society-npa-setting.test-samples';

import { SocietyNpaSettingService, RestSocietyNpaSetting } from './society-npa-setting.service';

const requireRestSample: RestSocietyNpaSetting = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('SocietyNpaSetting Service', () => {
  let service: SocietyNpaSettingService;
  let httpMock: HttpTestingController;
  let expectedResult: ISocietyNpaSetting | ISocietyNpaSetting[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SocietyNpaSettingService);
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

    it('should create a SocietyNpaSetting', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const societyNpaSetting = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(societyNpaSetting).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SocietyNpaSetting', () => {
      const societyNpaSetting = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(societyNpaSetting).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SocietyNpaSetting', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SocietyNpaSetting', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SocietyNpaSetting', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSocietyNpaSettingToCollectionIfMissing', () => {
      it('should add a SocietyNpaSetting to an empty array', () => {
        const societyNpaSetting: ISocietyNpaSetting = sampleWithRequiredData;
        expectedResult = service.addSocietyNpaSettingToCollectionIfMissing([], societyNpaSetting);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societyNpaSetting);
      });

      it('should not add a SocietyNpaSetting to an array that contains it', () => {
        const societyNpaSetting: ISocietyNpaSetting = sampleWithRequiredData;
        const societyNpaSettingCollection: ISocietyNpaSetting[] = [
          {
            ...societyNpaSetting,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSocietyNpaSettingToCollectionIfMissing(societyNpaSettingCollection, societyNpaSetting);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SocietyNpaSetting to an array that doesn't contain it", () => {
        const societyNpaSetting: ISocietyNpaSetting = sampleWithRequiredData;
        const societyNpaSettingCollection: ISocietyNpaSetting[] = [sampleWithPartialData];
        expectedResult = service.addSocietyNpaSettingToCollectionIfMissing(societyNpaSettingCollection, societyNpaSetting);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societyNpaSetting);
      });

      it('should add only unique SocietyNpaSetting to an array', () => {
        const societyNpaSettingArray: ISocietyNpaSetting[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const societyNpaSettingCollection: ISocietyNpaSetting[] = [sampleWithRequiredData];
        expectedResult = service.addSocietyNpaSettingToCollectionIfMissing(societyNpaSettingCollection, ...societyNpaSettingArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const societyNpaSetting: ISocietyNpaSetting = sampleWithRequiredData;
        const societyNpaSetting2: ISocietyNpaSetting = sampleWithPartialData;
        expectedResult = service.addSocietyNpaSettingToCollectionIfMissing([], societyNpaSetting, societyNpaSetting2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societyNpaSetting);
        expect(expectedResult).toContain(societyNpaSetting2);
      });

      it('should accept null and undefined values', () => {
        const societyNpaSetting: ISocietyNpaSetting = sampleWithRequiredData;
        expectedResult = service.addSocietyNpaSettingToCollectionIfMissing([], null, societyNpaSetting, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societyNpaSetting);
      });

      it('should return initial array if no SocietyNpaSetting is added', () => {
        const societyNpaSettingCollection: ISocietyNpaSetting[] = [sampleWithRequiredData];
        expectedResult = service.addSocietyNpaSettingToCollectionIfMissing(societyNpaSettingCollection, undefined, null);
        expect(expectedResult).toEqual(societyNpaSettingCollection);
      });
    });

    describe('compareSocietyNpaSetting', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSocietyNpaSetting(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSocietyNpaSetting(entity1, entity2);
        const compareResult2 = service.compareSocietyNpaSetting(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSocietyNpaSetting(entity1, entity2);
        const compareResult2 = service.compareSocietyNpaSetting(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSocietyNpaSetting(entity1, entity2);
        const compareResult2 = service.compareSocietyNpaSetting(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
