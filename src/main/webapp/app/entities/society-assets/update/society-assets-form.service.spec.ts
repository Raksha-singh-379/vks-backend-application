import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../society-assets.test-samples';

import { SocietyAssetsFormService } from './society-assets-form.service';

describe('SocietyAssets Form Service', () => {
  let service: SocietyAssetsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SocietyAssetsFormService);
  });

  describe('Service methods', () => {
    describe('createSocietyAssetsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSocietyAssetsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            societyAssetsName: expect.any(Object),
            type: expect.any(Object),
            category: expect.any(Object),
            depreciation: expect.any(Object),
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

      it('passing ISocietyAssets should create a new form with FormGroup', () => {
        const formGroup = service.createSocietyAssetsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            societyAssetsName: expect.any(Object),
            type: expect.any(Object),
            category: expect.any(Object),
            depreciation: expect.any(Object),
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

    describe('getSocietyAssets', () => {
      it('should return NewSocietyAssets for default SocietyAssets initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSocietyAssetsFormGroup(sampleWithNewData);

        const societyAssets = service.getSocietyAssets(formGroup) as any;

        expect(societyAssets).toMatchObject(sampleWithNewData);
      });

      it('should return NewSocietyAssets for empty SocietyAssets initial value', () => {
        const formGroup = service.createSocietyAssetsFormGroup();

        const societyAssets = service.getSocietyAssets(formGroup) as any;

        expect(societyAssets).toMatchObject({});
      });

      it('should return ISocietyAssets', () => {
        const formGroup = service.createSocietyAssetsFormGroup(sampleWithRequiredData);

        const societyAssets = service.getSocietyAssets(formGroup) as any;

        expect(societyAssets).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISocietyAssets should not enable id FormControl', () => {
        const formGroup = service.createSocietyAssetsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSocietyAssets should disable id FormControl', () => {
        const formGroup = service.createSocietyAssetsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
