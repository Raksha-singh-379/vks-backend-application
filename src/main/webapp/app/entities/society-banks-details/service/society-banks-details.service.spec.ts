import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISocietyBanksDetails } from '../society-banks-details.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../society-banks-details.test-samples';

import { SocietyBanksDetailsService, RestSocietyBanksDetails } from './society-banks-details.service';

const requireRestSample: RestSocietyBanksDetails = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('SocietyBanksDetails Service', () => {
  let service: SocietyBanksDetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: ISocietyBanksDetails | ISocietyBanksDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SocietyBanksDetailsService);
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

    it('should create a SocietyBanksDetails', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const societyBanksDetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(societyBanksDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SocietyBanksDetails', () => {
      const societyBanksDetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(societyBanksDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SocietyBanksDetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SocietyBanksDetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SocietyBanksDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSocietyBanksDetailsToCollectionIfMissing', () => {
      it('should add a SocietyBanksDetails to an empty array', () => {
        const societyBanksDetails: ISocietyBanksDetails = sampleWithRequiredData;
        expectedResult = service.addSocietyBanksDetailsToCollectionIfMissing([], societyBanksDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societyBanksDetails);
      });

      it('should not add a SocietyBanksDetails to an array that contains it', () => {
        const societyBanksDetails: ISocietyBanksDetails = sampleWithRequiredData;
        const societyBanksDetailsCollection: ISocietyBanksDetails[] = [
          {
            ...societyBanksDetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSocietyBanksDetailsToCollectionIfMissing(societyBanksDetailsCollection, societyBanksDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SocietyBanksDetails to an array that doesn't contain it", () => {
        const societyBanksDetails: ISocietyBanksDetails = sampleWithRequiredData;
        const societyBanksDetailsCollection: ISocietyBanksDetails[] = [sampleWithPartialData];
        expectedResult = service.addSocietyBanksDetailsToCollectionIfMissing(societyBanksDetailsCollection, societyBanksDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societyBanksDetails);
      });

      it('should add only unique SocietyBanksDetails to an array', () => {
        const societyBanksDetailsArray: ISocietyBanksDetails[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const societyBanksDetailsCollection: ISocietyBanksDetails[] = [sampleWithRequiredData];
        expectedResult = service.addSocietyBanksDetailsToCollectionIfMissing(societyBanksDetailsCollection, ...societyBanksDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const societyBanksDetails: ISocietyBanksDetails = sampleWithRequiredData;
        const societyBanksDetails2: ISocietyBanksDetails = sampleWithPartialData;
        expectedResult = service.addSocietyBanksDetailsToCollectionIfMissing([], societyBanksDetails, societyBanksDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societyBanksDetails);
        expect(expectedResult).toContain(societyBanksDetails2);
      });

      it('should accept null and undefined values', () => {
        const societyBanksDetails: ISocietyBanksDetails = sampleWithRequiredData;
        expectedResult = service.addSocietyBanksDetailsToCollectionIfMissing([], null, societyBanksDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societyBanksDetails);
      });

      it('should return initial array if no SocietyBanksDetails is added', () => {
        const societyBanksDetailsCollection: ISocietyBanksDetails[] = [sampleWithRequiredData];
        expectedResult = service.addSocietyBanksDetailsToCollectionIfMissing(societyBanksDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(societyBanksDetailsCollection);
      });
    });

    describe('compareSocietyBanksDetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSocietyBanksDetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSocietyBanksDetails(entity1, entity2);
        const compareResult2 = service.compareSocietyBanksDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSocietyBanksDetails(entity1, entity2);
        const compareResult2 = service.compareSocietyBanksDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSocietyBanksDetails(entity1, entity2);
        const compareResult2 = service.compareSocietyBanksDetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
