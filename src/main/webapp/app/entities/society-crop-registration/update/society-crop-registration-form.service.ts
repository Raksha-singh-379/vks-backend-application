import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISocietyCropRegistration, NewSocietyCropRegistration } from '../society-crop-registration.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISocietyCropRegistration for edit and NewSocietyCropRegistrationFormGroupInput for create.
 */
type SocietyCropRegistrationFormGroupInput = ISocietyCropRegistration | PartialWithRequiredKeyOf<NewSocietyCropRegistration>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISocietyCropRegistration | NewSocietyCropRegistration> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type SocietyCropRegistrationFormRawValue = FormValueOf<ISocietyCropRegistration>;

type NewSocietyCropRegistrationFormRawValue = FormValueOf<NewSocietyCropRegistration>;

type SocietyCropRegistrationFormDefaults = Pick<NewSocietyCropRegistration, 'id' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type SocietyCropRegistrationFormGroupContent = {
  id: FormControl<SocietyCropRegistrationFormRawValue['id'] | NewSocietyCropRegistration['id']>;
  cropName: FormControl<SocietyCropRegistrationFormRawValue['cropName']>;
  monthDuration: FormControl<SocietyCropRegistrationFormRawValue['monthDuration']>;
  season: FormControl<SocietyCropRegistrationFormRawValue['season']>;
  cropLimit: FormControl<SocietyCropRegistrationFormRawValue['cropLimit']>;
  year: FormControl<SocietyCropRegistrationFormRawValue['year']>;
  lastModified: FormControl<SocietyCropRegistrationFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<SocietyCropRegistrationFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<SocietyCropRegistrationFormRawValue['createdBy']>;
  createdOn: FormControl<SocietyCropRegistrationFormRawValue['createdOn']>;
  isDeleted: FormControl<SocietyCropRegistrationFormRawValue['isDeleted']>;
  freeField1: FormControl<SocietyCropRegistrationFormRawValue['freeField1']>;
  freeField2: FormControl<SocietyCropRegistrationFormRawValue['freeField2']>;
  freeField3: FormControl<SocietyCropRegistrationFormRawValue['freeField3']>;
  society: FormControl<SocietyCropRegistrationFormRawValue['society']>;
};

export type SocietyCropRegistrationFormGroup = FormGroup<SocietyCropRegistrationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SocietyCropRegistrationFormService {
  createSocietyCropRegistrationFormGroup(
    societyCropRegistration: SocietyCropRegistrationFormGroupInput = { id: null }
  ): SocietyCropRegistrationFormGroup {
    const societyCropRegistrationRawValue = this.convertSocietyCropRegistrationToSocietyCropRegistrationRawValue({
      ...this.getFormDefaults(),
      ...societyCropRegistration,
    });
    return new FormGroup<SocietyCropRegistrationFormGroupContent>({
      id: new FormControl(
        { value: societyCropRegistrationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      cropName: new FormControl(societyCropRegistrationRawValue.cropName),
      monthDuration: new FormControl(societyCropRegistrationRawValue.monthDuration),
      season: new FormControl(societyCropRegistrationRawValue.season),
      cropLimit: new FormControl(societyCropRegistrationRawValue.cropLimit),
      year: new FormControl(societyCropRegistrationRawValue.year),
      lastModified: new FormControl(societyCropRegistrationRawValue.lastModified),
      lastModifiedBy: new FormControl(societyCropRegistrationRawValue.lastModifiedBy),
      createdBy: new FormControl(societyCropRegistrationRawValue.createdBy),
      createdOn: new FormControl(societyCropRegistrationRawValue.createdOn),
      isDeleted: new FormControl(societyCropRegistrationRawValue.isDeleted),
      freeField1: new FormControl(societyCropRegistrationRawValue.freeField1),
      freeField2: new FormControl(societyCropRegistrationRawValue.freeField2),
      freeField3: new FormControl(societyCropRegistrationRawValue.freeField3),
      society: new FormControl(societyCropRegistrationRawValue.society),
    });
  }

  getSocietyCropRegistration(form: SocietyCropRegistrationFormGroup): ISocietyCropRegistration | NewSocietyCropRegistration {
    return this.convertSocietyCropRegistrationRawValueToSocietyCropRegistration(
      form.getRawValue() as SocietyCropRegistrationFormRawValue | NewSocietyCropRegistrationFormRawValue
    );
  }

  resetForm(form: SocietyCropRegistrationFormGroup, societyCropRegistration: SocietyCropRegistrationFormGroupInput): void {
    const societyCropRegistrationRawValue = this.convertSocietyCropRegistrationToSocietyCropRegistrationRawValue({
      ...this.getFormDefaults(),
      ...societyCropRegistration,
    });
    form.reset(
      {
        ...societyCropRegistrationRawValue,
        id: { value: societyCropRegistrationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SocietyCropRegistrationFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertSocietyCropRegistrationRawValueToSocietyCropRegistration(
    rawSocietyCropRegistration: SocietyCropRegistrationFormRawValue | NewSocietyCropRegistrationFormRawValue
  ): ISocietyCropRegistration | NewSocietyCropRegistration {
    return {
      ...rawSocietyCropRegistration,
      lastModified: dayjs(rawSocietyCropRegistration.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawSocietyCropRegistration.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertSocietyCropRegistrationToSocietyCropRegistrationRawValue(
    societyCropRegistration: ISocietyCropRegistration | (Partial<NewSocietyCropRegistration> & SocietyCropRegistrationFormDefaults)
  ): SocietyCropRegistrationFormRawValue | PartialWithRequiredKeyOf<NewSocietyCropRegistrationFormRawValue> {
    return {
      ...societyCropRegistration,
      lastModified: societyCropRegistration.lastModified ? societyCropRegistration.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: societyCropRegistration.createdOn ? societyCropRegistration.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
