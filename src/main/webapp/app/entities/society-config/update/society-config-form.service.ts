import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISocietyConfig, NewSocietyConfig } from '../society-config.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISocietyConfig for edit and NewSocietyConfigFormGroupInput for create.
 */
type SocietyConfigFormGroupInput = ISocietyConfig | PartialWithRequiredKeyOf<NewSocietyConfig>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISocietyConfig | NewSocietyConfig> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type SocietyConfigFormRawValue = FormValueOf<ISocietyConfig>;

type NewSocietyConfigFormRawValue = FormValueOf<NewSocietyConfig>;

type SocietyConfigFormDefaults = Pick<NewSocietyConfig, 'id' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type SocietyConfigFormGroupContent = {
  id: FormControl<SocietyConfigFormRawValue['id'] | NewSocietyConfig['id']>;
  configName: FormControl<SocietyConfigFormRawValue['configName']>;
  configType: FormControl<SocietyConfigFormRawValue['configType']>;
  status: FormControl<SocietyConfigFormRawValue['status']>;
  value: FormControl<SocietyConfigFormRawValue['value']>;
  year: FormControl<SocietyConfigFormRawValue['year']>;
  lastModified: FormControl<SocietyConfigFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<SocietyConfigFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<SocietyConfigFormRawValue['createdBy']>;
  createdOn: FormControl<SocietyConfigFormRawValue['createdOn']>;
  isDeleted: FormControl<SocietyConfigFormRawValue['isDeleted']>;
  freeField1: FormControl<SocietyConfigFormRawValue['freeField1']>;
  freeField2: FormControl<SocietyConfigFormRawValue['freeField2']>;
  freeField3: FormControl<SocietyConfigFormRawValue['freeField3']>;
  society: FormControl<SocietyConfigFormRawValue['society']>;
  bankDhoranDetails: FormControl<SocietyConfigFormRawValue['bankDhoranDetails']>;
};

export type SocietyConfigFormGroup = FormGroup<SocietyConfigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SocietyConfigFormService {
  createSocietyConfigFormGroup(societyConfig: SocietyConfigFormGroupInput = { id: null }): SocietyConfigFormGroup {
    const societyConfigRawValue = this.convertSocietyConfigToSocietyConfigRawValue({
      ...this.getFormDefaults(),
      ...societyConfig,
    });
    return new FormGroup<SocietyConfigFormGroupContent>({
      id: new FormControl(
        { value: societyConfigRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      configName: new FormControl(societyConfigRawValue.configName),
      configType: new FormControl(societyConfigRawValue.configType),
      status: new FormControl(societyConfigRawValue.status),
      value: new FormControl(societyConfigRawValue.value),
      year: new FormControl(societyConfigRawValue.year),
      lastModified: new FormControl(societyConfigRawValue.lastModified),
      lastModifiedBy: new FormControl(societyConfigRawValue.lastModifiedBy),
      createdBy: new FormControl(societyConfigRawValue.createdBy),
      createdOn: new FormControl(societyConfigRawValue.createdOn),
      isDeleted: new FormControl(societyConfigRawValue.isDeleted),
      freeField1: new FormControl(societyConfigRawValue.freeField1),
      freeField2: new FormControl(societyConfigRawValue.freeField2),
      freeField3: new FormControl(societyConfigRawValue.freeField3),
      society: new FormControl(societyConfigRawValue.society),
      bankDhoranDetails: new FormControl(societyConfigRawValue.bankDhoranDetails),
    });
  }

  getSocietyConfig(form: SocietyConfigFormGroup): ISocietyConfig | NewSocietyConfig {
    return this.convertSocietyConfigRawValueToSocietyConfig(form.getRawValue() as SocietyConfigFormRawValue | NewSocietyConfigFormRawValue);
  }

  resetForm(form: SocietyConfigFormGroup, societyConfig: SocietyConfigFormGroupInput): void {
    const societyConfigRawValue = this.convertSocietyConfigToSocietyConfigRawValue({ ...this.getFormDefaults(), ...societyConfig });
    form.reset(
      {
        ...societyConfigRawValue,
        id: { value: societyConfigRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SocietyConfigFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertSocietyConfigRawValueToSocietyConfig(
    rawSocietyConfig: SocietyConfigFormRawValue | NewSocietyConfigFormRawValue
  ): ISocietyConfig | NewSocietyConfig {
    return {
      ...rawSocietyConfig,
      lastModified: dayjs(rawSocietyConfig.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawSocietyConfig.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertSocietyConfigToSocietyConfigRawValue(
    societyConfig: ISocietyConfig | (Partial<NewSocietyConfig> & SocietyConfigFormDefaults)
  ): SocietyConfigFormRawValue | PartialWithRequiredKeyOf<NewSocietyConfigFormRawValue> {
    return {
      ...societyConfig,
      lastModified: societyConfig.lastModified ? societyConfig.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: societyConfig.createdOn ? societyConfig.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
