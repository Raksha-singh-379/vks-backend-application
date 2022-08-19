import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../loan-demand.test-samples';

import { LoanDemandFormService } from './loan-demand-form.service';

describe('LoanDemand Form Service', () => {
  let service: LoanDemandFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoanDemandFormService);
  });

  describe('Service methods', () => {
    describe('createLoanDemandFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLoanDemandFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            loanDemandAmount: expect.any(Object),
            maxAllowedAmount: expect.any(Object),
            adjustedDemand: expect.any(Object),
            annualIncome: expect.any(Object),
            costOfInvestment: expect.any(Object),
            demandedLandAreaInHector: expect.any(Object),
            status: expect.any(Object),
            seasonOfCropLoan: expect.any(Object),
            year: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            member: expect.any(Object),
            societyLoanProduct: expect.any(Object),
            memberLandAssets: expect.any(Object),
            societyCropRegistration: expect.any(Object),
          })
        );
      });

      it('passing ILoanDemand should create a new form with FormGroup', () => {
        const formGroup = service.createLoanDemandFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            loanDemandAmount: expect.any(Object),
            maxAllowedAmount: expect.any(Object),
            adjustedDemand: expect.any(Object),
            annualIncome: expect.any(Object),
            costOfInvestment: expect.any(Object),
            demandedLandAreaInHector: expect.any(Object),
            status: expect.any(Object),
            seasonOfCropLoan: expect.any(Object),
            year: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            member: expect.any(Object),
            societyLoanProduct: expect.any(Object),
            memberLandAssets: expect.any(Object),
            societyCropRegistration: expect.any(Object),
          })
        );
      });
    });

    describe('getLoanDemand', () => {
      it('should return NewLoanDemand for default LoanDemand initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLoanDemandFormGroup(sampleWithNewData);

        const loanDemand = service.getLoanDemand(formGroup) as any;

        expect(loanDemand).toMatchObject(sampleWithNewData);
      });

      it('should return NewLoanDemand for empty LoanDemand initial value', () => {
        const formGroup = service.createLoanDemandFormGroup();

        const loanDemand = service.getLoanDemand(formGroup) as any;

        expect(loanDemand).toMatchObject({});
      });

      it('should return ILoanDemand', () => {
        const formGroup = service.createLoanDemandFormGroup(sampleWithRequiredData);

        const loanDemand = service.getLoanDemand(formGroup) as any;

        expect(loanDemand).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILoanDemand should not enable id FormControl', () => {
        const formGroup = service.createLoanDemandFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLoanDemand should disable id FormControl', () => {
        const formGroup = service.createLoanDemandFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
