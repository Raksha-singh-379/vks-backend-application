import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISocietyAssetsData, NewSocietyAssetsData } from '../society-assets-data.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISocietyAssetsData for edit and NewSocietyAssetsDataFormGroupInput for create.
 */
type SocietyAssetsDataFormGroupInput = ISocietyAssetsData | PartialWithRequiredKeyOf<NewSocietyAssetsData>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISocietyAssetsData | NewSocietyAssetsData> = Omit<T, 'transactionDate' | 'lastModified' | 'createdOn'> & {
  transactionDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

type SocietyAssetsDataFormRawValue = FormValueOf<ISocietyAssetsData>;

type NewSocietyAssetsDataFormRawValue = FormValueOf<NewSocietyAssetsData>;

type SocietyAssetsDataFormDefaults = Pick<NewSocietyAssetsData, 'id' | 'transactionDate' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type SocietyAssetsDataFormGroupContent = {
  id: FormControl<SocietyAssetsDataFormRawValue['id'] | NewSocietyAssetsData['id']>;
  amount: FormControl<SocietyAssetsDataFormRawValue['amount']>;
  balanceQuantity: FormControl<SocietyAssetsDataFormRawValue['balanceQuantity']>;
  balanceValue: FormControl<SocietyAssetsDataFormRawValue['balanceValue']>;
  billNo: FormControl<SocietyAssetsDataFormRawValue['billNo']>;
  mode: FormControl<SocietyAssetsDataFormRawValue['mode']>;
  cost: FormControl<SocietyAssetsDataFormRawValue['cost']>;
  transactionType: FormControl<SocietyAssetsDataFormRawValue['transactionType']>;
  transactionDate: FormControl<SocietyAssetsDataFormRawValue['transactionDate']>;
  lastModified: FormControl<SocietyAssetsDataFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<SocietyAssetsDataFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<SocietyAssetsDataFormRawValue['createdBy']>;
  createdOn: FormControl<SocietyAssetsDataFormRawValue['createdOn']>;
  isDeleted: FormControl<SocietyAssetsDataFormRawValue['isDeleted']>;
  freeField1: FormControl<SocietyAssetsDataFormRawValue['freeField1']>;
  freeField2: FormControl<SocietyAssetsDataFormRawValue['freeField2']>;
  freeField3: FormControl<SocietyAssetsDataFormRawValue['freeField3']>;
  societyAssets: FormControl<SocietyAssetsDataFormRawValue['societyAssets']>;
};

export type SocietyAssetsDataFormGroup = FormGroup<SocietyAssetsDataFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SocietyAssetsDataFormService {
  createSocietyAssetsDataFormGroup(societyAssetsData: SocietyAssetsDataFormGroupInput = { id: null }): SocietyAssetsDataFormGroup {
    const societyAssetsDataRawValue = this.convertSocietyAssetsDataToSocietyAssetsDataRawValue({
      ...this.getFormDefaults(),
      ...societyAssetsData,
    });
    return new FormGroup<SocietyAssetsDataFormGroupContent>({
      id: new FormControl(
        { value: societyAssetsDataRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      amount: new FormControl(societyAssetsDataRawValue.amount),
      balanceQuantity: new FormControl(societyAssetsDataRawValue.balanceQuantity),
      balanceValue: new FormControl(societyAssetsDataRawValue.balanceValue),
      billNo: new FormControl(societyAssetsDataRawValue.billNo),
      mode: new FormControl(societyAssetsDataRawValue.mode),
      cost: new FormControl(societyAssetsDataRawValue.cost),
      transactionType: new FormControl(societyAssetsDataRawValue.transactionType),
      transactionDate: new FormControl(societyAssetsDataRawValue.transactionDate),
      lastModified: new FormControl(societyAssetsDataRawValue.lastModified),
      lastModifiedBy: new FormControl(societyAssetsDataRawValue.lastModifiedBy),
      createdBy: new FormControl(societyAssetsDataRawValue.createdBy),
      createdOn: new FormControl(societyAssetsDataRawValue.createdOn),
      isDeleted: new FormControl(societyAssetsDataRawValue.isDeleted),
      freeField1: new FormControl(societyAssetsDataRawValue.freeField1),
      freeField2: new FormControl(societyAssetsDataRawValue.freeField2),
      freeField3: new FormControl(societyAssetsDataRawValue.freeField3),
      societyAssets: new FormControl(societyAssetsDataRawValue.societyAssets),
    });
  }

  getSocietyAssetsData(form: SocietyAssetsDataFormGroup): ISocietyAssetsData | NewSocietyAssetsData {
    return this.convertSocietyAssetsDataRawValueToSocietyAssetsData(
      form.getRawValue() as SocietyAssetsDataFormRawValue | NewSocietyAssetsDataFormRawValue
    );
  }

  resetForm(form: SocietyAssetsDataFormGroup, societyAssetsData: SocietyAssetsDataFormGroupInput): void {
    const societyAssetsDataRawValue = this.convertSocietyAssetsDataToSocietyAssetsDataRawValue({
      ...this.getFormDefaults(),
      ...societyAssetsData,
    });
    form.reset(
      {
        ...societyAssetsDataRawValue,
        id: { value: societyAssetsDataRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SocietyAssetsDataFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      transactionDate: currentTime,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertSocietyAssetsDataRawValueToSocietyAssetsData(
    rawSocietyAssetsData: SocietyAssetsDataFormRawValue | NewSocietyAssetsDataFormRawValue
  ): ISocietyAssetsData | NewSocietyAssetsData {
    return {
      ...rawSocietyAssetsData,
      transactionDate: dayjs(rawSocietyAssetsData.transactionDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawSocietyAssetsData.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawSocietyAssetsData.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertSocietyAssetsDataToSocietyAssetsDataRawValue(
    societyAssetsData: ISocietyAssetsData | (Partial<NewSocietyAssetsData> & SocietyAssetsDataFormDefaults)
  ): SocietyAssetsDataFormRawValue | PartialWithRequiredKeyOf<NewSocietyAssetsDataFormRawValue> {
    return {
      ...societyAssetsData,
      transactionDate: societyAssetsData.transactionDate ? societyAssetsData.transactionDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: societyAssetsData.lastModified ? societyAssetsData.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: societyAssetsData.createdOn ? societyAssetsData.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
