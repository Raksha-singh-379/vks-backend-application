import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../society-loan-product.test-samples';

import { SocietyLoanProductFormService } from './society-loan-product-form.service';

describe('SocietyLoanProduct Form Service', () => {
  let service: SocietyLoanProductFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SocietyLoanProductFormService);
  });

  describe('Service methods', () => {
    describe('createSocietyLoanProductFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSocietyLoanProductFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            productName: expect.any(Object),
            accHeadCode: expect.any(Object),
            borrowingInterestRate: expect.any(Object),
            duration: expect.any(Object),
            interestRate: expect.any(Object),
            lastDateOfRepayment: expect.any(Object),
            loanNumber: expect.any(Object),
            maxLoanAmt: expect.any(Object),
            noOfDisbursement: expect.any(Object),
            noOfInstallment: expect.any(Object),
            parentAccHeadCode: expect.any(Object),
            parentAccHeadId: expect.any(Object),
            penaltyInterest: expect.any(Object),
            productType: expect.any(Object),
            resolutionDate: expect.any(Object),
            resolutionNo: expect.any(Object),
            status: expect.any(Object),
            surcharge: expect.any(Object),
            unitSize: expect.any(Object),
            validFrom: expect.any(Object),
            validTo: expect.any(Object),
            createdOn: expect.any(Object),
            createdBy: expect.any(Object),
            isActivate: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            society: expect.any(Object),
            bankDhoranDetails: expect.any(Object),
            gRInterestDetails: expect.any(Object),
          })
        );
      });

      it('passing ISocietyLoanProduct should create a new form with FormGroup', () => {
        const formGroup = service.createSocietyLoanProductFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            productName: expect.any(Object),
            accHeadCode: expect.any(Object),
            borrowingInterestRate: expect.any(Object),
            duration: expect.any(Object),
            interestRate: expect.any(Object),
            lastDateOfRepayment: expect.any(Object),
            loanNumber: expect.any(Object),
            maxLoanAmt: expect.any(Object),
            noOfDisbursement: expect.any(Object),
            noOfInstallment: expect.any(Object),
            parentAccHeadCode: expect.any(Object),
            parentAccHeadId: expect.any(Object),
            penaltyInterest: expect.any(Object),
            productType: expect.any(Object),
            resolutionDate: expect.any(Object),
            resolutionNo: expect.any(Object),
            status: expect.any(Object),
            surcharge: expect.any(Object),
            unitSize: expect.any(Object),
            validFrom: expect.any(Object),
            validTo: expect.any(Object),
            createdOn: expect.any(Object),
            createdBy: expect.any(Object),
            isActivate: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            society: expect.any(Object),
            bankDhoranDetails: expect.any(Object),
            gRInterestDetails: expect.any(Object),
          })
        );
      });
    });

    describe('getSocietyLoanProduct', () => {
      it('should return NewSocietyLoanProduct for default SocietyLoanProduct initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSocietyLoanProductFormGroup(sampleWithNewData);

        const societyLoanProduct = service.getSocietyLoanProduct(formGroup) as any;

        expect(societyLoanProduct).toMatchObject(sampleWithNewData);
      });

      it('should return NewSocietyLoanProduct for empty SocietyLoanProduct initial value', () => {
        const formGroup = service.createSocietyLoanProductFormGroup();

        const societyLoanProduct = service.getSocietyLoanProduct(formGroup) as any;

        expect(societyLoanProduct).toMatchObject({});
      });

      it('should return ISocietyLoanProduct', () => {
        const formGroup = service.createSocietyLoanProductFormGroup(sampleWithRequiredData);

        const societyLoanProduct = service.getSocietyLoanProduct(formGroup) as any;

        expect(societyLoanProduct).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISocietyLoanProduct should not enable id FormControl', () => {
        const formGroup = service.createSocietyLoanProductFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSocietyLoanProduct should disable id FormControl', () => {
        const formGroup = service.createSocietyLoanProductFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
