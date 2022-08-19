import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../loan-watap-details.test-samples';

import { LoanWatapDetailsFormService } from './loan-watap-details-form.service';

describe('LoanWatapDetails Form Service', () => {
  let service: LoanWatapDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoanWatapDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createLoanWatapDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLoanWatapDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            loanWatapDate: expect.any(Object),
            cropLandInHector: expect.any(Object),
            slotNumber: expect.any(Object),
            loanAmount: expect.any(Object),
            season: expect.any(Object),
            status: expect.any(Object),
            year: expect.any(Object),
            isDeleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            loanDemand: expect.any(Object),
          })
        );
      });

      it('passing ILoanWatapDetails should create a new form with FormGroup', () => {
        const formGroup = service.createLoanWatapDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            loanWatapDate: expect.any(Object),
            cropLandInHector: expect.any(Object),
            slotNumber: expect.any(Object),
            loanAmount: expect.any(Object),
            season: expect.any(Object),
            status: expect.any(Object),
            year: expect.any(Object),
            isDeleted: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            loanDemand: expect.any(Object),
          })
        );
      });
    });

    describe('getLoanWatapDetails', () => {
      it('should return NewLoanWatapDetails for default LoanWatapDetails initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLoanWatapDetailsFormGroup(sampleWithNewData);

        const loanWatapDetails = service.getLoanWatapDetails(formGroup) as any;

        expect(loanWatapDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewLoanWatapDetails for empty LoanWatapDetails initial value', () => {
        const formGroup = service.createLoanWatapDetailsFormGroup();

        const loanWatapDetails = service.getLoanWatapDetails(formGroup) as any;

        expect(loanWatapDetails).toMatchObject({});
      });

      it('should return ILoanWatapDetails', () => {
        const formGroup = service.createLoanWatapDetailsFormGroup(sampleWithRequiredData);

        const loanWatapDetails = service.getLoanWatapDetails(formGroup) as any;

        expect(loanWatapDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILoanWatapDetails should not enable id FormControl', () => {
        const formGroup = service.createLoanWatapDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLoanWatapDetails should disable id FormControl', () => {
        const formGroup = service.createLoanWatapDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
