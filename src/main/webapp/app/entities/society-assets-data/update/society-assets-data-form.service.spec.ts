import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../society-assets-data.test-samples';

import { SocietyAssetsDataFormService } from './society-assets-data-form.service';

describe('SocietyAssetsData Form Service', () => {
  let service: SocietyAssetsDataFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SocietyAssetsDataFormService);
  });

  describe('Service methods', () => {
    describe('createSocietyAssetsDataFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSocietyAssetsDataFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            amount: expect.any(Object),
            balanceQuantity: expect.any(Object),
            balanceValue: expect.any(Object),
            billNo: expect.any(Object),
            mode: expect.any(Object),
            cost: expect.any(Object),
            transactionType: expect.any(Object),
            transactionDate: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            societyAssets: expect.any(Object),
          })
        );
      });

      it('passing ISocietyAssetsData should create a new form with FormGroup', () => {
        const formGroup = service.createSocietyAssetsDataFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            amount: expect.any(Object),
            balanceQuantity: expect.any(Object),
            balanceValue: expect.any(Object),
            billNo: expect.any(Object),
            mode: expect.any(Object),
            cost: expect.any(Object),
            transactionType: expect.any(Object),
            transactionDate: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            societyAssets: expect.any(Object),
          })
        );
      });
    });

    describe('getSocietyAssetsData', () => {
      it('should return NewSocietyAssetsData for default SocietyAssetsData initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSocietyAssetsDataFormGroup(sampleWithNewData);

        const societyAssetsData = service.getSocietyAssetsData(formGroup) as any;

        expect(societyAssetsData).toMatchObject(sampleWithNewData);
      });

      it('should return NewSocietyAssetsData for empty SocietyAssetsData initial value', () => {
        const formGroup = service.createSocietyAssetsDataFormGroup();

        const societyAssetsData = service.getSocietyAssetsData(formGroup) as any;

        expect(societyAssetsData).toMatchObject({});
      });

      it('should return ISocietyAssetsData', () => {
        const formGroup = service.createSocietyAssetsDataFormGroup(sampleWithRequiredData);

        const societyAssetsData = service.getSocietyAssetsData(formGroup) as any;

        expect(societyAssetsData).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISocietyAssetsData should not enable id FormControl', () => {
        const formGroup = service.createSocietyAssetsDataFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSocietyAssetsData should disable id FormControl', () => {
        const formGroup = service.createSocietyAssetsDataFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
