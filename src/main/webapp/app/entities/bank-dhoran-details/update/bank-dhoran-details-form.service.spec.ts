import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../bank-dhoran-details.test-samples';

import { BankDhoranDetailsFormService } from './bank-dhoran-details-form.service';

describe('BankDhoranDetails Form Service', () => {
  let service: BankDhoranDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BankDhoranDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createBankDhoranDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBankDhoranDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dhoranName: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            year: expect.any(Object),
            isActivate: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            society: expect.any(Object),
          })
        );
      });

      it('passing IBankDhoranDetails should create a new form with FormGroup', () => {
        const formGroup = service.createBankDhoranDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dhoranName: expect.any(Object),
            startDate: expect.any(Object),
            endDate: expect.any(Object),
            year: expect.any(Object),
            isActivate: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            society: expect.any(Object),
          })
        );
      });
    });

    describe('getBankDhoranDetails', () => {
      it('should return NewBankDhoranDetails for default BankDhoranDetails initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createBankDhoranDetailsFormGroup(sampleWithNewData);

        const bankDhoranDetails = service.getBankDhoranDetails(formGroup) as any;

        expect(bankDhoranDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewBankDhoranDetails for empty BankDhoranDetails initial value', () => {
        const formGroup = service.createBankDhoranDetailsFormGroup();

        const bankDhoranDetails = service.getBankDhoranDetails(formGroup) as any;

        expect(bankDhoranDetails).toMatchObject({});
      });

      it('should return IBankDhoranDetails', () => {
        const formGroup = service.createBankDhoranDetailsFormGroup(sampleWithRequiredData);

        const bankDhoranDetails = service.getBankDhoranDetails(formGroup) as any;

        expect(bankDhoranDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBankDhoranDetails should not enable id FormControl', () => {
        const formGroup = service.createBankDhoranDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBankDhoranDetails should disable id FormControl', () => {
        const formGroup = service.createBankDhoranDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
