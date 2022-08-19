import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../expenditure-type.test-samples';

import { ExpenditureTypeFormService } from './expenditure-type-form.service';

describe('ExpenditureType Form Service', () => {
  let service: ExpenditureTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExpenditureTypeFormService);
  });

  describe('Service methods', () => {
    describe('createExpenditureTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createExpenditureTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            expenditureDesc: expect.any(Object),
            expenditureType: expect.any(Object),
            expenditureCategory: expect.any(Object),
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

      it('passing IExpenditureType should create a new form with FormGroup', () => {
        const formGroup = service.createExpenditureTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            expenditureDesc: expect.any(Object),
            expenditureType: expect.any(Object),
            expenditureCategory: expect.any(Object),
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

    describe('getExpenditureType', () => {
      it('should return NewExpenditureType for default ExpenditureType initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createExpenditureTypeFormGroup(sampleWithNewData);

        const expenditureType = service.getExpenditureType(formGroup) as any;

        expect(expenditureType).toMatchObject(sampleWithNewData);
      });

      it('should return NewExpenditureType for empty ExpenditureType initial value', () => {
        const formGroup = service.createExpenditureTypeFormGroup();

        const expenditureType = service.getExpenditureType(formGroup) as any;

        expect(expenditureType).toMatchObject({});
      });

      it('should return IExpenditureType', () => {
        const formGroup = service.createExpenditureTypeFormGroup(sampleWithRequiredData);

        const expenditureType = service.getExpenditureType(formGroup) as any;

        expect(expenditureType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IExpenditureType should not enable id FormControl', () => {
        const formGroup = service.createExpenditureTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewExpenditureType should disable id FormControl', () => {
        const formGroup = service.createExpenditureTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
