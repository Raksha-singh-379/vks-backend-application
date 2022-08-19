import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../society-npa-setting.test-samples';

import { SocietyNpaSettingFormService } from './society-npa-setting-form.service';

describe('SocietyNpaSetting Form Service', () => {
  let service: SocietyNpaSettingFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SocietyNpaSettingFormService);
  });

  describe('Service methods', () => {
    describe('createSocietyNpaSettingFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSocietyNpaSettingFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            npaClassification: expect.any(Object),
            durationStart: expect.any(Object),
            durationEnd: expect.any(Object),
            provision: expect.any(Object),
            year: expect.any(Object),
            interestRate: expect.any(Object),
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

      it('passing ISocietyNpaSetting should create a new form with FormGroup', () => {
        const formGroup = service.createSocietyNpaSettingFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            npaClassification: expect.any(Object),
            durationStart: expect.any(Object),
            durationEnd: expect.any(Object),
            provision: expect.any(Object),
            year: expect.any(Object),
            interestRate: expect.any(Object),
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

    describe('getSocietyNpaSetting', () => {
      it('should return NewSocietyNpaSetting for default SocietyNpaSetting initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSocietyNpaSettingFormGroup(sampleWithNewData);

        const societyNpaSetting = service.getSocietyNpaSetting(formGroup) as any;

        expect(societyNpaSetting).toMatchObject(sampleWithNewData);
      });

      it('should return NewSocietyNpaSetting for empty SocietyNpaSetting initial value', () => {
        const formGroup = service.createSocietyNpaSettingFormGroup();

        const societyNpaSetting = service.getSocietyNpaSetting(formGroup) as any;

        expect(societyNpaSetting).toMatchObject({});
      });

      it('should return ISocietyNpaSetting', () => {
        const formGroup = service.createSocietyNpaSettingFormGroup(sampleWithRequiredData);

        const societyNpaSetting = service.getSocietyNpaSetting(formGroup) as any;

        expect(societyNpaSetting).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISocietyNpaSetting should not enable id FormControl', () => {
        const formGroup = service.createSocietyNpaSettingFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSocietyNpaSetting should disable id FormControl', () => {
        const formGroup = service.createSocietyNpaSettingFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
