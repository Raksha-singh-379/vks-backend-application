import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../loan-disbursement-details.test-samples';

import { LoanDisbursementDetailsFormService } from './loan-disbursement-details-form.service';

describe('LoanDisbursementDetails Form Service', () => {
  let service: LoanDisbursementDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoanDisbursementDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createLoanDisbursementDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLoanDisbursementDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            disbursementDate: expect.any(Object),
            disbursementAmount: expect.any(Object),
            disbursementNumber: expect.any(Object),
            disbursementStatus: expect.any(Object),
            paymentMode: expect.any(Object),
            type: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            loanDetails: expect.any(Object),
          })
        );
      });

      it('passing ILoanDisbursementDetails should create a new form with FormGroup', () => {
        const formGroup = service.createLoanDisbursementDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            disbursementDate: expect.any(Object),
            disbursementAmount: expect.any(Object),
            disbursementNumber: expect.any(Object),
            disbursementStatus: expect.any(Object),
            paymentMode: expect.any(Object),
            type: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            loanDetails: expect.any(Object),
          })
        );
      });
    });

    describe('getLoanDisbursementDetails', () => {
      it('should return NewLoanDisbursementDetails for default LoanDisbursementDetails initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLoanDisbursementDetailsFormGroup(sampleWithNewData);

        const loanDisbursementDetails = service.getLoanDisbursementDetails(formGroup) as any;

        expect(loanDisbursementDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewLoanDisbursementDetails for empty LoanDisbursementDetails initial value', () => {
        const formGroup = service.createLoanDisbursementDetailsFormGroup();

        const loanDisbursementDetails = service.getLoanDisbursementDetails(formGroup) as any;

        expect(loanDisbursementDetails).toMatchObject({});
      });

      it('should return ILoanDisbursementDetails', () => {
        const formGroup = service.createLoanDisbursementDetailsFormGroup(sampleWithRequiredData);

        const loanDisbursementDetails = service.getLoanDisbursementDetails(formGroup) as any;

        expect(loanDisbursementDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILoanDisbursementDetails should not enable id FormControl', () => {
        const formGroup = service.createLoanDisbursementDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLoanDisbursementDetails should disable id FormControl', () => {
        const formGroup = service.createLoanDisbursementDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
