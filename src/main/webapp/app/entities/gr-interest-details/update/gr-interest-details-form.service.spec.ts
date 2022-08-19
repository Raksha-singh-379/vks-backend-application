import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../gr-interest-details.test-samples';

import { GRInterestDetailsFormService } from './gr-interest-details-form.service';

describe('GRInterestDetails Form Service', () => {
  let service: GRInterestDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GRInterestDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createGRInterestDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGRInterestDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            loanGrName: expect.any(Object),
            criteria: expect.any(Object),
            productType: expect.any(Object),
            isActivated: expect.any(Object),
            borrowingInterestRate: expect.any(Object),
            interestOnLoan: expect.any(Object),
            penaltyInterest: expect.any(Object),
            surcharge: expect.any(Object),
            loanDuration: expect.any(Object),
            numberOFInstallment: expect.any(Object),
            extendedInterstRate: expect.any(Object),
            centralGovSubsidyInterest: expect.any(Object),
            distBankSubsidyInterest: expect.any(Object),
            borrowerInterest: expect.any(Object),
            stateGovSubsidyInterest: expect.any(Object),
            year: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            society: expect.any(Object),
          })
        );
      });

      it('passing IGRInterestDetails should create a new form with FormGroup', () => {
        const formGroup = service.createGRInterestDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            loanGrName: expect.any(Object),
            criteria: expect.any(Object),
            productType: expect.any(Object),
            isActivated: expect.any(Object),
            borrowingInterestRate: expect.any(Object),
            interestOnLoan: expect.any(Object),
            penaltyInterest: expect.any(Object),
            surcharge: expect.any(Object),
            loanDuration: expect.any(Object),
            numberOFInstallment: expect.any(Object),
            extendedInterstRate: expect.any(Object),
            centralGovSubsidyInterest: expect.any(Object),
            distBankSubsidyInterest: expect.any(Object),
            borrowerInterest: expect.any(Object),
            stateGovSubsidyInterest: expect.any(Object),
            year: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            society: expect.any(Object),
          })
        );
      });
    });

    describe('getGRInterestDetails', () => {
      it('should return NewGRInterestDetails for default GRInterestDetails initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createGRInterestDetailsFormGroup(sampleWithNewData);

        const gRInterestDetails = service.getGRInterestDetails(formGroup) as any;

        expect(gRInterestDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewGRInterestDetails for empty GRInterestDetails initial value', () => {
        const formGroup = service.createGRInterestDetailsFormGroup();

        const gRInterestDetails = service.getGRInterestDetails(formGroup) as any;

        expect(gRInterestDetails).toMatchObject({});
      });

      it('should return IGRInterestDetails', () => {
        const formGroup = service.createGRInterestDetailsFormGroup(sampleWithRequiredData);

        const gRInterestDetails = service.getGRInterestDetails(formGroup) as any;

        expect(gRInterestDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGRInterestDetails should not enable id FormControl', () => {
        const formGroup = service.createGRInterestDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGRInterestDetails should disable id FormControl', () => {
        const formGroup = service.createGRInterestDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
