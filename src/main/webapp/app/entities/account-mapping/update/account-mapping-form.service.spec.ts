import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../account-mapping.test-samples';

import { AccountMappingFormService } from './account-mapping-form.service';

describe('AccountMapping Form Service', () => {
  let service: AccountMappingFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccountMappingFormService);
  });

  describe('Service methods', () => {
    describe('createAccountMappingFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAccountMappingFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            mappingName: expect.any(Object),
            ledgerAccHeadCode: expect.any(Object),
            ledgerAccountId: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            accountMapping: expect.any(Object),
          })
        );
      });

      it('passing IAccountMapping should create a new form with FormGroup', () => {
        const formGroup = service.createAccountMappingFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            mappingName: expect.any(Object),
            ledgerAccHeadCode: expect.any(Object),
            ledgerAccountId: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            accountMapping: expect.any(Object),
          })
        );
      });
    });

    describe('getAccountMapping', () => {
      it('should return NewAccountMapping for default AccountMapping initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAccountMappingFormGroup(sampleWithNewData);

        const accountMapping = service.getAccountMapping(formGroup) as any;

        expect(accountMapping).toMatchObject(sampleWithNewData);
      });

      it('should return NewAccountMapping for empty AccountMapping initial value', () => {
        const formGroup = service.createAccountMappingFormGroup();

        const accountMapping = service.getAccountMapping(formGroup) as any;

        expect(accountMapping).toMatchObject({});
      });

      it('should return IAccountMapping', () => {
        const formGroup = service.createAccountMappingFormGroup(sampleWithRequiredData);

        const accountMapping = service.getAccountMapping(formGroup) as any;

        expect(accountMapping).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAccountMapping should not enable id FormControl', () => {
        const formGroup = service.createAccountMappingFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAccountMapping should disable id FormControl', () => {
        const formGroup = service.createAccountMappingFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
