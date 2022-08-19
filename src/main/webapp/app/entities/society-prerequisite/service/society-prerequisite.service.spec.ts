import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISocietyPrerequisite } from '../society-prerequisite.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../society-prerequisite.test-samples';

import { SocietyPrerequisiteService, RestSocietyPrerequisite } from './society-prerequisite.service';

const requireRestSample: RestSocietyPrerequisite = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('SocietyPrerequisite Service', () => {
  let service: SocietyPrerequisiteService;
  let httpMock: HttpTestingController;
  let expectedResult: ISocietyPrerequisite | ISocietyPrerequisite[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SocietyPrerequisiteService);
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

    it('should create a SocietyPrerequisite', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const societyPrerequisite = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(societyPrerequisite).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SocietyPrerequisite', () => {
      const societyPrerequisite = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(societyPrerequisite).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SocietyPrerequisite', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SocietyPrerequisite', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SocietyPrerequisite', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSocietyPrerequisiteToCollectionIfMissing', () => {
      it('should add a SocietyPrerequisite to an empty array', () => {
        const societyPrerequisite: ISocietyPrerequisite = sampleWithRequiredData;
        expectedResult = service.addSocietyPrerequisiteToCollectionIfMissing([], societyPrerequisite);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societyPrerequisite);
      });

      it('should not add a SocietyPrerequisite to an array that contains it', () => {
        const societyPrerequisite: ISocietyPrerequisite = sampleWithRequiredData;
        const societyPrerequisiteCollection: ISocietyPrerequisite[] = [
          {
            ...societyPrerequisite,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSocietyPrerequisiteToCollectionIfMissing(societyPrerequisiteCollection, societyPrerequisite);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SocietyPrerequisite to an array that doesn't contain it", () => {
        const societyPrerequisite: ISocietyPrerequisite = sampleWithRequiredData;
        const societyPrerequisiteCollection: ISocietyPrerequisite[] = [sampleWithPartialData];
        expectedResult = service.addSocietyPrerequisiteToCollectionIfMissing(societyPrerequisiteCollection, societyPrerequisite);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societyPrerequisite);
      });

      it('should add only unique SocietyPrerequisite to an array', () => {
        const societyPrerequisiteArray: ISocietyPrerequisite[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const societyPrerequisiteCollection: ISocietyPrerequisite[] = [sampleWithRequiredData];
        expectedResult = service.addSocietyPrerequisiteToCollectionIfMissing(societyPrerequisiteCollection, ...societyPrerequisiteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const societyPrerequisite: ISocietyPrerequisite = sampleWithRequiredData;
        const societyPrerequisite2: ISocietyPrerequisite = sampleWithPartialData;
        expectedResult = service.addSocietyPrerequisiteToCollectionIfMissing([], societyPrerequisite, societyPrerequisite2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societyPrerequisite);
        expect(expectedResult).toContain(societyPrerequisite2);
      });

      it('should accept null and undefined values', () => {
        const societyPrerequisite: ISocietyPrerequisite = sampleWithRequiredData;
        expectedResult = service.addSocietyPrerequisiteToCollectionIfMissing([], null, societyPrerequisite, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societyPrerequisite);
      });

      it('should return initial array if no SocietyPrerequisite is added', () => {
        const societyPrerequisiteCollection: ISocietyPrerequisite[] = [sampleWithRequiredData];
        expectedResult = service.addSocietyPrerequisiteToCollectionIfMissing(societyPrerequisiteCollection, undefined, null);
        expect(expectedResult).toEqual(societyPrerequisiteCollection);
      });
    });

    describe('compareSocietyPrerequisite', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSocietyPrerequisite(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSocietyPrerequisite(entity1, entity2);
        const compareResult2 = service.compareSocietyPrerequisite(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSocietyPrerequisite(entity1, entity2);
        const compareResult2 = service.compareSocietyPrerequisite(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSocietyPrerequisite(entity1, entity2);
        const compareResult2 = service.compareSocietyPrerequisite(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
