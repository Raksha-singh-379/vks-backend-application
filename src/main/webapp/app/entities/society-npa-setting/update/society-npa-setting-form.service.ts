import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISocietyNpaSetting, NewSocietyNpaSetting } from '../society-npa-setting.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISocietyNpaSetting for edit and NewSocietyNpaSettingFormGroupInput for create.
 */
type SocietyNpaSettingFormGroupInput = ISocietyNpaSetting | PartialWithRequiredKeyOf<NewSocietyNpaSetting>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISocietyNpaSetting | NewSocietyNpaSetting> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type SocietyNpaSettingFormRawValue = FormValueOf<ISocietyNpaSetting>;

type NewSocietyNpaSettingFormRawValue = FormValueOf<NewSocietyNpaSetting>;

type SocietyNpaSettingFormDefaults = Pick<NewSocietyNpaSetting, 'id' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type SocietyNpaSettingFormGroupContent = {
  id: FormControl<SocietyNpaSettingFormRawValue['id'] | NewSocietyNpaSetting['id']>;
  npaClassification: FormControl<SocietyNpaSettingFormRawValue['npaClassification']>;
  durationStart: FormControl<SocietyNpaSettingFormRawValue['durationStart']>;
  durationEnd: FormControl<SocietyNpaSettingFormRawValue['durationEnd']>;
  provision: FormControl<SocietyNpaSettingFormRawValue['provision']>;
  year: FormControl<SocietyNpaSettingFormRawValue['year']>;
  interestRate: FormControl<SocietyNpaSettingFormRawValue['interestRate']>;
  lastModified: FormControl<SocietyNpaSettingFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<SocietyNpaSettingFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<SocietyNpaSettingFormRawValue['createdBy']>;
  createdOn: FormControl<SocietyNpaSettingFormRawValue['createdOn']>;
  isDeleted: FormControl<SocietyNpaSettingFormRawValue['isDeleted']>;
  freeField1: FormControl<SocietyNpaSettingFormRawValue['freeField1']>;
  freeField2: FormControl<SocietyNpaSettingFormRawValue['freeField2']>;
  freeField3: FormControl<SocietyNpaSettingFormRawValue['freeField3']>;
  society: FormControl<SocietyNpaSettingFormRawValue['society']>;
};

export type SocietyNpaSettingFormGroup = FormGroup<SocietyNpaSettingFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SocietyNpaSettingFormService {
  createSocietyNpaSettingFormGroup(societyNpaSetting: SocietyNpaSettingFormGroupInput = { id: null }): SocietyNpaSettingFormGroup {
    const societyNpaSettingRawValue = this.convertSocietyNpaSettingToSocietyNpaSettingRawValue({
      ...this.getFormDefaults(),
      ...societyNpaSetting,
    });
    return new FormGroup<SocietyNpaSettingFormGroupContent>({
      id: new FormControl(
        { value: societyNpaSettingRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      npaClassification: new FormControl(societyNpaSettingRawValue.npaClassification),
      durationStart: new FormControl(societyNpaSettingRawValue.durationStart),
      durationEnd: new FormControl(societyNpaSettingRawValue.durationEnd),
      provision: new FormControl(societyNpaSettingRawValue.provision),
      year: new FormControl(societyNpaSettingRawValue.year),
      interestRate: new FormControl(societyNpaSettingRawValue.interestRate),
      lastModified: new FormControl(societyNpaSettingRawValue.lastModified),
      lastModifiedBy: new FormControl(societyNpaSettingRawValue.lastModifiedBy),
      createdBy: new FormControl(societyNpaSettingRawValue.createdBy),
      createdOn: new FormControl(societyNpaSettingRawValue.createdOn),
      isDeleted: new FormControl(societyNpaSettingRawValue.isDeleted),
      freeField1: new FormControl(societyNpaSettingRawValue.freeField1),
      freeField2: new FormControl(societyNpaSettingRawValue.freeField2),
      freeField3: new FormControl(societyNpaSettingRawValue.freeField3),
      society: new FormControl(societyNpaSettingRawValue.society),
    });
  }

  getSocietyNpaSetting(form: SocietyNpaSettingFormGroup): ISocietyNpaSetting | NewSocietyNpaSetting {
    return this.convertSocietyNpaSettingRawValueToSocietyNpaSetting(
      form.getRawValue() as SocietyNpaSettingFormRawValue | NewSocietyNpaSettingFormRawValue
    );
  }

  resetForm(form: SocietyNpaSettingFormGroup, societyNpaSetting: SocietyNpaSettingFormGroupInput): void {
    const societyNpaSettingRawValue = this.convertSocietyNpaSettingToSocietyNpaSettingRawValue({
      ...this.getFormDefaults(),
      ...societyNpaSetting,
    });
    form.reset(
      {
        ...societyNpaSettingRawValue,
        id: { value: societyNpaSettingRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SocietyNpaSettingFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertSocietyNpaSettingRawValueToSocietyNpaSetting(
    rawSocietyNpaSetting: SocietyNpaSettingFormRawValue | NewSocietyNpaSettingFormRawValue
  ): ISocietyNpaSetting | NewSocietyNpaSetting {
    return {
      ...rawSocietyNpaSetting,
      lastModified: dayjs(rawSocietyNpaSetting.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawSocietyNpaSetting.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertSocietyNpaSettingToSocietyNpaSettingRawValue(
    societyNpaSetting: ISocietyNpaSetting | (Partial<NewSocietyNpaSetting> & SocietyNpaSettingFormDefaults)
  ): SocietyNpaSettingFormRawValue | PartialWithRequiredKeyOf<NewSocietyNpaSettingFormRawValue> {
    return {
      ...societyNpaSetting,
      lastModified: societyNpaSetting.lastModified ? societyNpaSetting.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: societyNpaSetting.createdOn ? societyNpaSetting.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
