import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISocietyCropRegistration } from '../society-crop-registration.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../society-crop-registration.test-samples';

import { SocietyCropRegistrationService, RestSocietyCropRegistration } from './society-crop-registration.service';

const requireRestSample: RestSocietyCropRegistration = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('SocietyCropRegistration Service', () => {
  let service: SocietyCropRegistrationService;
  let httpMock: HttpTestingController;
  let expectedResult: ISocietyCropRegistration | ISocietyCropRegistration[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SocietyCropRegistrationService);
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

    it('should create a SocietyCropRegistration', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const societyCropRegistration = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(societyCropRegistration).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SocietyCropRegistration', () => {
      const societyCropRegistration = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(societyCropRegistration).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SocietyCropRegistration', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SocietyCropRegistration', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SocietyCropRegistration', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSocietyCropRegistrationToCollectionIfMissing', () => {
      it('should add a SocietyCropRegistration to an empty array', () => {
        const societyCropRegistration: ISocietyCropRegistration = sampleWithRequiredData;
        expectedResult = service.addSocietyCropRegistrationToCollectionIfMissing([], societyCropRegistration);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societyCropRegistration);
      });

      it('should not add a SocietyCropRegistration to an array that contains it', () => {
        const societyCropRegistration: ISocietyCropRegistration = sampleWithRequiredData;
        const societyCropRegistrationCollection: ISocietyCropRegistration[] = [
          {
            ...societyCropRegistration,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSocietyCropRegistrationToCollectionIfMissing(
          societyCropRegistrationCollection,
          societyCropRegistration
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SocietyCropRegistration to an array that doesn't contain it", () => {
        const societyCropRegistration: ISocietyCropRegistration = sampleWithRequiredData;
        const societyCropRegistrationCollection: ISocietyCropRegistration[] = [sampleWithPartialData];
        expectedResult = service.addSocietyCropRegistrationToCollectionIfMissing(
          societyCropRegistrationCollection,
          societyCropRegistration
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societyCropRegistration);
      });

      it('should add only unique SocietyCropRegistration to an array', () => {
        const societyCropRegistrationArray: ISocietyCropRegistration[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const societyCropRegistrationCollection: ISocietyCropRegistration[] = [sampleWithRequiredData];
        expectedResult = service.addSocietyCropRegistrationToCollectionIfMissing(
          societyCropRegistrationCollection,
          ...societyCropRegistrationArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const societyCropRegistration: ISocietyCropRegistration = sampleWithRequiredData;
        const societyCropRegistration2: ISocietyCropRegistration = sampleWithPartialData;
        expectedResult = service.addSocietyCropRegistrationToCollectionIfMissing([], societyCropRegistration, societyCropRegistration2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societyCropRegistration);
        expect(expectedResult).toContain(societyCropRegistration2);
      });

      it('should accept null and undefined values', () => {
        const societyCropRegistration: ISocietyCropRegistration = sampleWithRequiredData;
        expectedResult = service.addSocietyCropRegistrationToCollectionIfMissing([], null, societyCropRegistration, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societyCropRegistration);
      });

      it('should return initial array if no SocietyCropRegistration is added', () => {
        const societyCropRegistrationCollection: ISocietyCropRegistration[] = [sampleWithRequiredData];
        expectedResult = service.addSocietyCropRegistrationToCollectionIfMissing(societyCropRegistrationCollection, undefined, null);
        expect(expectedResult).toEqual(societyCropRegistrationCollection);
      });
    });

    describe('compareSocietyCropRegistration', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSocietyCropRegistration(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSocietyCropRegistration(entity1, entity2);
        const compareResult2 = service.compareSocietyCropRegistration(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSocietyCropRegistration(entity1, entity2);
        const compareResult2 = service.compareSocietyCropRegistration(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSocietyCropRegistration(entity1, entity2);
        const compareResult2 = service.compareSocietyCropRegistration(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
