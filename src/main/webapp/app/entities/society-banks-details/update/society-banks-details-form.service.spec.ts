import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../society-banks-details.test-samples';

import { SocietyBanksDetailsFormService } from './society-banks-details-form.service';

describe('SocietyBanksDetails Form Service', () => {
  let service: SocietyBanksDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SocietyBanksDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createSocietyBanksDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSocietyBanksDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            bankName: expect.any(Object),
            ifsccode: expect.any(Object),
            branchName: expect.any(Object),
            accountNumber: expect.any(Object),
            isActive: expect.any(Object),
            accountType: expect.any(Object),
            accHeadCode: expect.any(Object),
            parentAccHeadCode: expect.any(Object),
            accountName: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            society: expect.any(Object),
          })
        );
      });

      it('passing ISocietyBanksDetails should create a new form with FormGroup', () => {
        const formGroup = service.createSocietyBanksDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            bankName: expect.any(Object),
            ifsccode: expect.any(Object),
            branchName: expect.any(Object),
            accountNumber: expect.any(Object),
            isActive: expect.any(Object),
            accountType: expect.any(Object),
            accHeadCode: expect.any(Object),
            parentAccHeadCode: expect.any(Object),
            accountName: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            society: expect.any(Object),
          })
        );
      });
    });

    describe('getSocietyBanksDetails', () => {
      it('should return NewSocietyBanksDetails for default SocietyBanksDetails initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSocietyBanksDetailsFormGroup(sampleWithNewData);

        const societyBanksDetails = service.getSocietyBanksDetails(formGroup) as any;

        expect(societyBanksDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewSocietyBanksDetails for empty SocietyBanksDetails initial value', () => {
        const formGroup = service.createSocietyBanksDetailsFormGroup();

        const societyBanksDetails = service.getSocietyBanksDetails(formGroup) as any;

        expect(societyBanksDetails).toMatchObject({});
      });

      it('should return ISocietyBanksDetails', () => {
        const formGroup = service.createSocietyBanksDetailsFormGroup(sampleWithRequiredData);

        const societyBanksDetails = service.getSocietyBanksDetails(formGroup) as any;

        expect(societyBanksDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISocietyBanksDetails should not enable id FormControl', () => {
        const formGroup = service.createSocietyBanksDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSocietyBanksDetails should disable id FormControl', () => {
        const formGroup = service.createSocietyBanksDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
