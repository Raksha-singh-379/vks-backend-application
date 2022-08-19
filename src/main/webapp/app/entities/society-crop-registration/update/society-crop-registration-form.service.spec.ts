import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../society-crop-registration.test-samples';

import { SocietyCropRegistrationFormService } from './society-crop-registration-form.service';

describe('SocietyCropRegistration Form Service', () => {
  let service: SocietyCropRegistrationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SocietyCropRegistrationFormService);
  });

  describe('Service methods', () => {
    describe('createSocietyCropRegistrationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSocietyCropRegistrationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cropName: expect.any(Object),
            monthDuration: expect.any(Object),
            season: expect.any(Object),
            cropLimit: expect.any(Object),
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
          })
        );
      });

      it('passing ISocietyCropRegistration should create a new form with FormGroup', () => {
        const formGroup = service.createSocietyCropRegistrationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cropName: expect.any(Object),
            monthDuration: expect.any(Object),
            season: expect.any(Object),
            cropLimit: expect.any(Object),
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
          })
        );
      });
    });

    describe('getSocietyCropRegistration', () => {
      it('should return NewSocietyCropRegistration for default SocietyCropRegistration initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSocietyCropRegistrationFormGroup(sampleWithNewData);

        const societyCropRegistration = service.getSocietyCropRegistration(formGroup) as any;

        expect(societyCropRegistration).toMatchObject(sampleWithNewData);
      });

      it('should return NewSocietyCropRegistration for empty SocietyCropRegistration initial value', () => {
        const formGroup = service.createSocietyCropRegistrationFormGroup();

        const societyCropRegistration = service.getSocietyCropRegistration(formGroup) as any;

        expect(societyCropRegistration).toMatchObject({});
      });

      it('should return ISocietyCropRegistration', () => {
        const formGroup = service.createSocietyCropRegistrationFormGroup(sampleWithRequiredData);

        const societyCropRegistration = service.getSocietyCropRegistration(formGroup) as any;

        expect(societyCropRegistration).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISocietyCropRegistration should not enable id FormControl', () => {
        const formGroup = service.createSocietyCropRegistrationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSocietyCropRegistration should disable id FormControl', () => {
        const formGroup = service.createSocietyCropRegistrationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
