import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISocietyPrerequisite, NewSocietyPrerequisite } from '../society-prerequisite.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISocietyPrerequisite for edit and NewSocietyPrerequisiteFormGroupInput for create.
 */
type SocietyPrerequisiteFormGroupInput = ISocietyPrerequisite | PartialWithRequiredKeyOf<NewSocietyPrerequisite>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISocietyPrerequisite | NewSocietyPrerequisite> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type SocietyPrerequisiteFormRawValue = FormValueOf<ISocietyPrerequisite>;

type NewSocietyPrerequisiteFormRawValue = FormValueOf<NewSocietyPrerequisite>;

type SocietyPrerequisiteFormDefaults = Pick<NewSocietyPrerequisite, 'id' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type SocietyPrerequisiteFormGroupContent = {
  id: FormControl<SocietyPrerequisiteFormRawValue['id'] | NewSocietyPrerequisite['id']>;
  docType: FormControl<SocietyPrerequisiteFormRawValue['docType']>;
  documentDesc: FormControl<SocietyPrerequisiteFormRawValue['documentDesc']>;
  documentName: FormControl<SocietyPrerequisiteFormRawValue['documentName']>;
  loanType: FormControl<SocietyPrerequisiteFormRawValue['loanType']>;
  mandatory: FormControl<SocietyPrerequisiteFormRawValue['mandatory']>;
  lastModified: FormControl<SocietyPrerequisiteFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<SocietyPrerequisiteFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<SocietyPrerequisiteFormRawValue['createdBy']>;
  createdOn: FormControl<SocietyPrerequisiteFormRawValue['createdOn']>;
  isDeleted: FormControl<SocietyPrerequisiteFormRawValue['isDeleted']>;
  freeField1: FormControl<SocietyPrerequisiteFormRawValue['freeField1']>;
  freeField2: FormControl<SocietyPrerequisiteFormRawValue['freeField2']>;
  freeField3: FormControl<SocietyPrerequisiteFormRawValue['freeField3']>;
  society: FormControl<SocietyPrerequisiteFormRawValue['society']>;
};

export type SocietyPrerequisiteFormGroup = FormGroup<SocietyPrerequisiteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SocietyPrerequisiteFormService {
  createSocietyPrerequisiteFormGroup(societyPrerequisite: SocietyPrerequisiteFormGroupInput = { id: null }): SocietyPrerequisiteFormGroup {
    const societyPrerequisiteRawValue = this.convertSocietyPrerequisiteToSocietyPrerequisiteRawValue({
      ...this.getFormDefaults(),
      ...societyPrerequisite,
    });
    return new FormGroup<SocietyPrerequisiteFormGroupContent>({
      id: new FormControl(
        { value: societyPrerequisiteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      docType: new FormControl(societyPrerequisiteRawValue.docType),
      documentDesc: new FormControl(societyPrerequisiteRawValue.documentDesc),
      documentName: new FormControl(societyPrerequisiteRawValue.documentName),
      loanType: new FormControl(societyPrerequisiteRawValue.loanType),
      mandatory: new FormControl(societyPrerequisiteRawValue.mandatory),
      lastModified: new FormControl(societyPrerequisiteRawValue.lastModified),
      lastModifiedBy: new FormControl(societyPrerequisiteRawValue.lastModifiedBy),
      createdBy: new FormControl(societyPrerequisiteRawValue.createdBy),
      createdOn: new FormControl(societyPrerequisiteRawValue.createdOn),
      isDeleted: new FormControl(societyPrerequisiteRawValue.isDeleted),
      freeField1: new FormControl(societyPrerequisiteRawValue.freeField1),
      freeField2: new FormControl(societyPrerequisiteRawValue.freeField2),
      freeField3: new FormControl(societyPrerequisiteRawValue.freeField3),
      society: new FormControl(societyPrerequisiteRawValue.society),
    });
  }

  getSocietyPrerequisite(form: SocietyPrerequisiteFormGroup): ISocietyPrerequisite | NewSocietyPrerequisite {
    return this.convertSocietyPrerequisiteRawValueToSocietyPrerequisite(
      form.getRawValue() as SocietyPrerequisiteFormRawValue | NewSocietyPrerequisiteFormRawValue
    );
  }

  resetForm(form: SocietyPrerequisiteFormGroup, societyPrerequisite: SocietyPrerequisiteFormGroupInput): void {
    const societyPrerequisiteRawValue = this.convertSocietyPrerequisiteToSocietyPrerequisiteRawValue({
      ...this.getFormDefaults(),
      ...societyPrerequisite,
    });
    form.reset(
      {
        ...societyPrerequisiteRawValue,
        id: { value: societyPrerequisiteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SocietyPrerequisiteFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertSocietyPrerequisiteRawValueToSocietyPrerequisite(
    rawSocietyPrerequisite: SocietyPrerequisiteFormRawValue | NewSocietyPrerequisiteFormRawValue
  ): ISocietyPrerequisite | NewSocietyPrerequisite {
    return {
      ...rawSocietyPrerequisite,
      lastModified: dayjs(rawSocietyPrerequisite.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawSocietyPrerequisite.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertSocietyPrerequisiteToSocietyPrerequisiteRawValue(
    societyPrerequisite: ISocietyPrerequisite | (Partial<NewSocietyPrerequisite> & SocietyPrerequisiteFormDefaults)
  ): SocietyPrerequisiteFormRawValue | PartialWithRequiredKeyOf<NewSocietyPrerequisiteFormRawValue> {
    return {
      ...societyPrerequisite,
      lastModified: societyPrerequisite.lastModified ? societyPrerequisite.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: societyPrerequisite.createdOn ? societyPrerequisite.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
