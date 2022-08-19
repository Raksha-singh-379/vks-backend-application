import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../society-config.test-samples';

import { SocietyConfigFormService } from './society-config-form.service';

describe('SocietyConfig Form Service', () => {
  let service: SocietyConfigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SocietyConfigFormService);
  });

  describe('Service methods', () => {
    describe('createSocietyConfigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSocietyConfigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            configName: expect.any(Object),
            configType: expect.any(Object),
            status: expect.any(Object),
            value: expect.any(Object),
            year: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            society: expect.any(Object),
            bankDhoranDetails: expect.any(Object),
          })
        );
      });

      it('passing ISocietyConfig should create a new form with FormGroup', () => {
        const formGroup = service.createSocietyConfigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            configName: expect.any(Object),
            configType: expect.any(Object),
            status: expect.any(Object),
            value: expect.any(Object),
            year: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            society: expect.any(Object),
            bankDhoranDetails: expect.any(Object),
          })
        );
      });
    });

    describe('getSocietyConfig', () => {
      it('should return NewSocietyConfig for default SocietyConfig initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSocietyConfigFormGroup(sampleWithNewData);

        const societyConfig = service.getSocietyConfig(formGroup) as any;

        expect(societyConfig).toMatchObject(sampleWithNewData);
      });

      it('should return NewSocietyConfig for empty SocietyConfig initial value', () => {
        const formGroup = service.createSocietyConfigFormGroup();

        const societyConfig = service.getSocietyConfig(formGroup) as any;

        expect(societyConfig).toMatchObject({});
      });

      it('should return ISocietyConfig', () => {
        const formGroup = service.createSocietyConfigFormGroup(sampleWithRequiredData);

        const societyConfig = service.getSocietyConfig(formGroup) as any;

        expect(societyConfig).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISocietyConfig should not enable id FormControl', () => {
        const formGroup = service.createSocietyConfigFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSocietyConfig should disable id FormControl', () => {
        const formGroup = service.createSocietyConfigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
