import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IGRInterestDetails } from '../gr-interest-details.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../gr-interest-details.test-samples';

import { GRInterestDetailsService, RestGRInterestDetails } from './gr-interest-details.service';

const requireRestSample: RestGRInterestDetails = {
  ...sampleWithRequiredData,
  startDate: sampleWithRequiredData.startDate?.toJSON(),
  endDate: sampleWithRequiredData.endDate?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('GRInterestDetails Service', () => {
  let service: GRInterestDetailsService;
  let httpMock: HttpTestingController;
  let expectedResult: IGRInterestDetails | IGRInterestDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GRInterestDetailsService);
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

    it('should create a GRInterestDetails', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const gRInterestDetails = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(gRInterestDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GRInterestDetails', () => {
      const gRInterestDetails = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(gRInterestDetails).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GRInterestDetails', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GRInterestDetails', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GRInterestDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addGRInterestDetailsToCollectionIfMissing', () => {
      it('should add a GRInterestDetails to an empty array', () => {
        const gRInterestDetails: IGRInterestDetails = sampleWithRequiredData;
        expectedResult = service.addGRInterestDetailsToCollectionIfMissing([], gRInterestDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(gRInterestDetails);
      });

      it('should not add a GRInterestDetails to an array that contains it', () => {
        const gRInterestDetails: IGRInterestDetails = sampleWithRequiredData;
        const gRInterestDetailsCollection: IGRInterestDetails[] = [
          {
            ...gRInterestDetails,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGRInterestDetailsToCollectionIfMissing(gRInterestDetailsCollection, gRInterestDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GRInterestDetails to an array that doesn't contain it", () => {
        const gRInterestDetails: IGRInterestDetails = sampleWithRequiredData;
        const gRInterestDetailsCollection: IGRInterestDetails[] = [sampleWithPartialData];
        expectedResult = service.addGRInterestDetailsToCollectionIfMissing(gRInterestDetailsCollection, gRInterestDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(gRInterestDetails);
      });

      it('should add only unique GRInterestDetails to an array', () => {
        const gRInterestDetailsArray: IGRInterestDetails[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const gRInterestDetailsCollection: IGRInterestDetails[] = [sampleWithRequiredData];
        expectedResult = service.addGRInterestDetailsToCollectionIfMissing(gRInterestDetailsCollection, ...gRInterestDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const gRInterestDetails: IGRInterestDetails = sampleWithRequiredData;
        const gRInterestDetails2: IGRInterestDetails = sampleWithPartialData;
        expectedResult = service.addGRInterestDetailsToCollectionIfMissing([], gRInterestDetails, gRInterestDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(gRInterestDetails);
        expect(expectedResult).toContain(gRInterestDetails2);
      });

      it('should accept null and undefined values', () => {
        const gRInterestDetails: IGRInterestDetails = sampleWithRequiredData;
        expectedResult = service.addGRInterestDetailsToCollectionIfMissing([], null, gRInterestDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(gRInterestDetails);
      });

      it('should return initial array if no GRInterestDetails is added', () => {
        const gRInterestDetailsCollection: IGRInterestDetails[] = [sampleWithRequiredData];
        expectedResult = service.addGRInterestDetailsToCollectionIfMissing(gRInterestDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(gRInterestDetailsCollection);
      });
    });

    describe('compareGRInterestDetails', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGRInterestDetails(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareGRInterestDetails(entity1, entity2);
        const compareResult2 = service.compareGRInterestDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareGRInterestDetails(entity1, entity2);
        const compareResult2 = service.compareGRInterestDetails(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareGRInterestDetails(entity1, entity2);
        const compareResult2 = service.compareGRInterestDetails(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
