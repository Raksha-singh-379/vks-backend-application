import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISocietyConfig } from '../society-config.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../society-config.test-samples';

import { SocietyConfigService, RestSocietyConfig } from './society-config.service';

const requireRestSample: RestSocietyConfig = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('SocietyConfig Service', () => {
  let service: SocietyConfigService;
  let httpMock: HttpTestingController;
  let expectedResult: ISocietyConfig | ISocietyConfig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SocietyConfigService);
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

    it('should create a SocietyConfig', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const societyConfig = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(societyConfig).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SocietyConfig', () => {
      const societyConfig = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(societyConfig).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SocietyConfig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SocietyConfig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SocietyConfig', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSocietyConfigToCollectionIfMissing', () => {
      it('should add a SocietyConfig to an empty array', () => {
        const societyConfig: ISocietyConfig = sampleWithRequiredData;
        expectedResult = service.addSocietyConfigToCollectionIfMissing([], societyConfig);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societyConfig);
      });

      it('should not add a SocietyConfig to an array that contains it', () => {
        const societyConfig: ISocietyConfig = sampleWithRequiredData;
        const societyConfigCollection: ISocietyConfig[] = [
          {
            ...societyConfig,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSocietyConfigToCollectionIfMissing(societyConfigCollection, societyConfig);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SocietyConfig to an array that doesn't contain it", () => {
        const societyConfig: ISocietyConfig = sampleWithRequiredData;
        const societyConfigCollection: ISocietyConfig[] = [sampleWithPartialData];
        expectedResult = service.addSocietyConfigToCollectionIfMissing(societyConfigCollection, societyConfig);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societyConfig);
      });

      it('should add only unique SocietyConfig to an array', () => {
        const societyConfigArray: ISocietyConfig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const societyConfigCollection: ISocietyConfig[] = [sampleWithRequiredData];
        expectedResult = service.addSocietyConfigToCollectionIfMissing(societyConfigCollection, ...societyConfigArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const societyConfig: ISocietyConfig = sampleWithRequiredData;
        const societyConfig2: ISocietyConfig = sampleWithPartialData;
        expectedResult = service.addSocietyConfigToCollectionIfMissing([], societyConfig, societyConfig2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societyConfig);
        expect(expectedResult).toContain(societyConfig2);
      });

      it('should accept null and undefined values', () => {
        const societyConfig: ISocietyConfig = sampleWithRequiredData;
        expectedResult = service.addSocietyConfigToCollectionIfMissing([], null, societyConfig, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societyConfig);
      });

      it('should return initial array if no SocietyConfig is added', () => {
        const societyConfigCollection: ISocietyConfig[] = [sampleWithRequiredData];
        expectedResult = service.addSocietyConfigToCollectionIfMissing(societyConfigCollection, undefined, null);
        expect(expectedResult).toEqual(societyConfigCollection);
      });
    });

    describe('compareSocietyConfig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSocietyConfig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSocietyConfig(entity1, entity2);
        const compareResult2 = service.compareSocietyConfig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSocietyConfig(entity1, entity2);
        const compareResult2 = service.compareSocietyConfig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSocietyConfig(entity1, entity2);
        const compareResult2 = service.compareSocietyConfig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
