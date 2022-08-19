import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../society-prerequisite.test-samples';

import { SocietyPrerequisiteFormService } from './society-prerequisite-form.service';

describe('SocietyPrerequisite Form Service', () => {
  let service: SocietyPrerequisiteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SocietyPrerequisiteFormService);
  });

  describe('Service methods', () => {
    describe('createSocietyPrerequisiteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSocietyPrerequisiteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            docType: expect.any(Object),
            documentDesc: expect.any(Object),
            documentName: expect.any(Object),
            loanType: expect.any(Object),
            mandatory: expect.any(Object),
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

      it('passing ISocietyPrerequisite should create a new form with FormGroup', () => {
        const formGroup = service.createSocietyPrerequisiteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            docType: expect.any(Object),
            documentDesc: expect.any(Object),
            documentName: expect.any(Object),
            loanType: expect.any(Object),
            mandatory: expect.any(Object),
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

    describe('getSocietyPrerequisite', () => {
      it('should return NewSocietyPrerequisite for default SocietyPrerequisite initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSocietyPrerequisiteFormGroup(sampleWithNewData);

        const societyPrerequisite = service.getSocietyPrerequisite(formGroup) as any;

        expect(societyPrerequisite).toMatchObject(sampleWithNewData);
      });

      it('should return NewSocietyPrerequisite for empty SocietyPrerequisite initial value', () => {
        const formGroup = service.createSocietyPrerequisiteFormGroup();

        const societyPrerequisite = service.getSocietyPrerequisite(formGroup) as any;

        expect(societyPrerequisite).toMatchObject({});
      });

      it('should return ISocietyPrerequisite', () => {
        const formGroup = service.createSocietyPrerequisiteFormGroup(sampleWithRequiredData);

        const societyPrerequisite = service.getSocietyPrerequisite(formGroup) as any;

        expect(societyPrerequisite).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISocietyPrerequisite should not enable id FormControl', () => {
        const formGroup = service.createSocietyPrerequisiteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSocietyPrerequisite should disable id FormControl', () => {
        const formGroup = service.createSocietyPrerequisiteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
