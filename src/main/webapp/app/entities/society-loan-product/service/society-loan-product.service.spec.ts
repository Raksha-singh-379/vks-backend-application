import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISocietyLoanProduct } from '../society-loan-product.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../society-loan-product.test-samples';

import { SocietyLoanProductService, RestSocietyLoanProduct } from './society-loan-product.service';

const requireRestSample: RestSocietyLoanProduct = {
  ...sampleWithRequiredData,
  lastDateOfRepayment: sampleWithRequiredData.lastDateOfRepayment?.toJSON(),
  resolutionDate: sampleWithRequiredData.resolutionDate?.toJSON(),
  validFrom: sampleWithRequiredData.validFrom?.toJSON(),
  validTo: sampleWithRequiredData.validTo?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('SocietyLoanProduct Service', () => {
  let service: SocietyLoanProductService;
  let httpMock: HttpTestingController;
  let expectedResult: ISocietyLoanProduct | ISocietyLoanProduct[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SocietyLoanProductService);
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

    it('should create a SocietyLoanProduct', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const societyLoanProduct = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(societyLoanProduct).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SocietyLoanProduct', () => {
      const societyLoanProduct = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(societyLoanProduct).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SocietyLoanProduct', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SocietyLoanProduct', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SocietyLoanProduct', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSocietyLoanProductToCollectionIfMissing', () => {
      it('should add a SocietyLoanProduct to an empty array', () => {
        const societyLoanProduct: ISocietyLoanProduct = sampleWithRequiredData;
        expectedResult = service.addSocietyLoanProductToCollectionIfMissing([], societyLoanProduct);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societyLoanProduct);
      });

      it('should not add a SocietyLoanProduct to an array that contains it', () => {
        const societyLoanProduct: ISocietyLoanProduct = sampleWithRequiredData;
        const societyLoanProductCollection: ISocietyLoanProduct[] = [
          {
            ...societyLoanProduct,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSocietyLoanProductToCollectionIfMissing(societyLoanProductCollection, societyLoanProduct);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SocietyLoanProduct to an array that doesn't contain it", () => {
        const societyLoanProduct: ISocietyLoanProduct = sampleWithRequiredData;
        const societyLoanProductCollection: ISocietyLoanProduct[] = [sampleWithPartialData];
        expectedResult = service.addSocietyLoanProductToCollectionIfMissing(societyLoanProductCollection, societyLoanProduct);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societyLoanProduct);
      });

      it('should add only unique SocietyLoanProduct to an array', () => {
        const societyLoanProductArray: ISocietyLoanProduct[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const societyLoanProductCollection: ISocietyLoanProduct[] = [sampleWithRequiredData];
        expectedResult = service.addSocietyLoanProductToCollectionIfMissing(societyLoanProductCollection, ...societyLoanProductArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const societyLoanProduct: ISocietyLoanProduct = sampleWithRequiredData;
        const societyLoanProduct2: ISocietyLoanProduct = sampleWithPartialData;
        expectedResult = service.addSocietyLoanProductToCollectionIfMissing([], societyLoanProduct, societyLoanProduct2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(societyLoanProduct);
        expect(expectedResult).toContain(societyLoanProduct2);
      });

      it('should accept null and undefined values', () => {
        const societyLoanProduct: ISocietyLoanProduct = sampleWithRequiredData;
        expectedResult = service.addSocietyLoanProductToCollectionIfMissing([], null, societyLoanProduct, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(societyLoanProduct);
      });

      it('should return initial array if no SocietyLoanProduct is added', () => {
        const societyLoanProductCollection: ISocietyLoanProduct[] = [sampleWithRequiredData];
        expectedResult = service.addSocietyLoanProductToCollectionIfMissing(societyLoanProductCollection, undefined, null);
        expect(expectedResult).toEqual(societyLoanProductCollection);
      });
    });

    describe('compareSocietyLoanProduct', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSocietyLoanProduct(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSocietyLoanProduct(entity1, entity2);
        const compareResult2 = service.compareSocietyLoanProduct(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSocietyLoanProduct(entity1, entity2);
        const compareResult2 = service.compareSocietyLoanProduct(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSocietyLoanProduct(entity1, entity2);
        const compareResult2 = service.compareSocietyLoanProduct(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
