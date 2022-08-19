import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../loan-details.test-samples';

import { LoanDetailsFormService } from './loan-details-form.service';

describe('LoanDetails Form Service', () => {
  let service: LoanDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoanDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createLoanDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLoanDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            loanAmount: expect.any(Object),
            loanAccountNo: expect.any(Object),
            loanType: expect.any(Object),
            status: expect.any(Object),
            loanStartDate: expect.any(Object),
            loanEndDate: expect.any(Object),
            loanPlannedClosureDate: expect.any(Object),
            loanCloserDate: expect.any(Object),
            loanEffectiveDate: expect.any(Object),
            loanClassification: expect.any(Object),
            resolutionNo: expect.any(Object),
            resolutionDate: expect.any(Object),
            costOfInvestment: expect.any(Object),
            loanBenefitingArea: expect.any(Object),
            dccbLoanNo: expect.any(Object),
            mortgageDeedNo: expect.any(Object),
            mortgageDate: expect.any(Object),
            extentMorgageValue: expect.any(Object),
            parentAccHeadCode: expect.any(Object),
            loanAccountName: expect.any(Object),
            disbursementAmt: expect.any(Object),
            disbursementStatus: expect.any(Object),
            year: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            loanDemand: expect.any(Object),
            member: expect.any(Object),
            loanDemand: expect.any(Object),
            societyLoanProduct: expect.any(Object),
            bankDhoranDetails: expect.any(Object),
          })
        );
      });

      it('passing ILoanDetails should create a new form with FormGroup', () => {
        const formGroup = service.createLoanDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            loanAmount: expect.any(Object),
            loanAccountNo: expect.any(Object),
            loanType: expect.any(Object),
            status: expect.any(Object),
            loanStartDate: expect.any(Object),
            loanEndDate: expect.any(Object),
            loanPlannedClosureDate: expect.any(Object),
            loanCloserDate: expect.any(Object),
            loanEffectiveDate: expect.any(Object),
            loanClassification: expect.any(Object),
            resolutionNo: expect.any(Object),
            resolutionDate: expect.any(Object),
            costOfInvestment: expect.any(Object),
            loanBenefitingArea: expect.any(Object),
            dccbLoanNo: expect.any(Object),
            mortgageDeedNo: expect.any(Object),
            mortgageDate: expect.any(Object),
            extentMorgageValue: expect.any(Object),
            parentAccHeadCode: expect.any(Object),
            loanAccountName: expect.any(Object),
            disbursementAmt: expect.any(Object),
            disbursementStatus: expect.any(Object),
            year: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            loanDemand: expect.any(Object),
            member: expect.any(Object),
            loanDemand: expect.any(Object),
            societyLoanProduct: expect.any(Object),
            bankDhoranDetails: expect.any(Object),
          })
        );
      });
    });

    describe('getLoanDetails', () => {
      it('should return NewLoanDetails for default LoanDetails initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLoanDetailsFormGroup(sampleWithNewData);

        const loanDetails = service.getLoanDetails(formGroup) as any;

        expect(loanDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewLoanDetails for empty LoanDetails initial value', () => {
        const formGroup = service.createLoanDetailsFormGroup();

        const loanDetails = service.getLoanDetails(formGroup) as any;

        expect(loanDetails).toMatchObject({});
      });

      it('should return ILoanDetails', () => {
        const formGroup = service.createLoanDetailsFormGroup(sampleWithRequiredData);

        const loanDetails = service.getLoanDetails(formGroup) as any;

        expect(loanDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILoanDetails should not enable id FormControl', () => {
        const formGroup = service.createLoanDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLoanDetails should disable id FormControl', () => {
        const formGroup = service.createLoanDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
