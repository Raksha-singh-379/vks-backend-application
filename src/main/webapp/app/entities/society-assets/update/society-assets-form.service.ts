import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISocietyAssets, NewSocietyAssets } from '../society-assets.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISocietyAssets for edit and NewSocietyAssetsFormGroupInput for create.
 */
type SocietyAssetsFormGroupInput = ISocietyAssets | PartialWithRequiredKeyOf<NewSocietyAssets>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISocietyAssets | NewSocietyAssets> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type SocietyAssetsFormRawValue = FormValueOf<ISocietyAssets>;

type NewSocietyAssetsFormRawValue = FormValueOf<NewSocietyAssets>;

type SocietyAssetsFormDefaults = Pick<NewSocietyAssets, 'id' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type SocietyAssetsFormGroupContent = {
  id: FormControl<SocietyAssetsFormRawValue['id'] | NewSocietyAssets['id']>;
  societyAssetsName: FormControl<SocietyAssetsFormRawValue['societyAssetsName']>;
  type: FormControl<SocietyAssetsFormRawValue['type']>;
  category: FormControl<SocietyAssetsFormRawValue['category']>;
  depreciation: FormControl<SocietyAssetsFormRawValue['depreciation']>;
  lastModified: FormControl<SocietyAssetsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<SocietyAssetsFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<SocietyAssetsFormRawValue['createdBy']>;
  createdOn: FormControl<SocietyAssetsFormRawValue['createdOn']>;
  isDeleted: FormControl<SocietyAssetsFormRawValue['isDeleted']>;
  freeField1: FormControl<SocietyAssetsFormRawValue['freeField1']>;
  freeField2: FormControl<SocietyAssetsFormRawValue['freeField2']>;
  freeField3: FormControl<SocietyAssetsFormRawValue['freeField3']>;
  society: FormControl<SocietyAssetsFormRawValue['society']>;
};

export type SocietyAssetsFormGroup = FormGroup<SocietyAssetsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SocietyAssetsFormService {
  createSocietyAssetsFormGroup(societyAssets: SocietyAssetsFormGroupInput = { id: null }): SocietyAssetsFormGroup {
    const societyAssetsRawValue = this.convertSocietyAssetsToSocietyAssetsRawValue({
      ...this.getFormDefaults(),
      ...societyAssets,
    });
    return new FormGroup<SocietyAssetsFormGroupContent>({
      id: new FormControl(
        { value: societyAssetsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      societyAssetsName: new FormControl(societyAssetsRawValue.societyAssetsName),
      type: new FormControl(societyAssetsRawValue.type),
      category: new FormControl(societyAssetsRawValue.category),
      depreciation: new FormControl(societyAssetsRawValue.depreciation),
      lastModified: new FormControl(societyAssetsRawValue.lastModified),
      lastModifiedBy: new FormControl(societyAssetsRawValue.lastModifiedBy),
      createdBy: new FormControl(societyAssetsRawValue.createdBy),
      createdOn: new FormControl(societyAssetsRawValue.createdOn),
      isDeleted: new FormControl(societyAssetsRawValue.isDeleted),
      freeField1: new FormControl(societyAssetsRawValue.freeField1),
      freeField2: new FormControl(societyAssetsRawValue.freeField2),
      freeField3: new FormControl(societyAssetsRawValue.freeField3),
      society: new FormControl(societyAssetsRawValue.society),
    });
  }

  getSocietyAssets(form: SocietyAssetsFormGroup): ISocietyAssets | NewSocietyAssets {
    return this.convertSocietyAssetsRawValueToSocietyAssets(form.getRawValue() as SocietyAssetsFormRawValue | NewSocietyAssetsFormRawValue);
  }

  resetForm(form: SocietyAssetsFormGroup, societyAssets: SocietyAssetsFormGroupInput): void {
    const societyAssetsRawValue = this.convertSocietyAssetsToSocietyAssetsRawValue({ ...this.getFormDefaults(), ...societyAssets });
    form.reset(
      {
        ...societyAssetsRawValue,
        id: { value: societyAssetsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SocietyAssetsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertSocietyAssetsRawValueToSocietyAssets(
    rawSocietyAssets: SocietyAssetsFormRawValue | NewSocietyAssetsFormRawValue
  ): ISocietyAssets | NewSocietyAssets {
    return {
      ...rawSocietyAssets,
      lastModified: dayjs(rawSocietyAssets.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawSocietyAssets.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertSocietyAssetsToSocietyAssetsRawValue(
    societyAssets: ISocietyAssets | (Partial<NewSocietyAssets> & SocietyAssetsFormDefaults)
  ): SocietyAssetsFormRawValue | PartialWithRequiredKeyOf<NewSocietyAssetsFormRawValue> {
    return {
      ...societyAssets,
      lastModified: societyAssets.lastModified ? societyAssets.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: societyAssets.createdOn ? societyAssets.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
