import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IBankDhoranDetails, NewBankDhoranDetails } from '../bank-dhoran-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBankDhoranDetails for edit and NewBankDhoranDetailsFormGroupInput for create.
 */
type BankDhoranDetailsFormGroupInput = IBankDhoranDetails | PartialWithRequiredKeyOf<NewBankDhoranDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IBankDhoranDetails | NewBankDhoranDetails> = Omit<T, 'startDate' | 'endDate' | 'lastModified'> & {
  startDate?: string | null;
  endDate?: string | null;
  lastModified?: string | null;
};

type BankDhoranDetailsFormRawValue = FormValueOf<IBankDhoranDetails>;

type NewBankDhoranDetailsFormRawValue = FormValueOf<NewBankDhoranDetails>;

type BankDhoranDetailsFormDefaults = Pick<NewBankDhoranDetails, 'id' | 'startDate' | 'endDate' | 'isActivate' | 'lastModified'>;

type BankDhoranDetailsFormGroupContent = {
  id: FormControl<BankDhoranDetailsFormRawValue['id'] | NewBankDhoranDetails['id']>;
  dhoranName: FormControl<BankDhoranDetailsFormRawValue['dhoranName']>;
  startDate: FormControl<BankDhoranDetailsFormRawValue['startDate']>;
  endDate: FormControl<BankDhoranDetailsFormRawValue['endDate']>;
  year: FormControl<BankDhoranDetailsFormRawValue['year']>;
  isActivate: FormControl<BankDhoranDetailsFormRawValue['isActivate']>;
  lastModified: FormControl<BankDhoranDetailsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<BankDhoranDetailsFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<BankDhoranDetailsFormRawValue['freeField1']>;
  freeField2: FormControl<BankDhoranDetailsFormRawValue['freeField2']>;
  freeField3: FormControl<BankDhoranDetailsFormRawValue['freeField3']>;
  society: FormControl<BankDhoranDetailsFormRawValue['society']>;
};

export type BankDhoranDetailsFormGroup = FormGroup<BankDhoranDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BankDhoranDetailsFormService {
  createBankDhoranDetailsFormGroup(bankDhoranDetails: BankDhoranDetailsFormGroupInput = { id: null }): BankDhoranDetailsFormGroup {
    const bankDhoranDetailsRawValue = this.convertBankDhoranDetailsToBankDhoranDetailsRawValue({
      ...this.getFormDefaults(),
      ...bankDhoranDetails,
    });
    return new FormGroup<BankDhoranDetailsFormGroupContent>({
      id: new FormControl(
        { value: bankDhoranDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      dhoranName: new FormControl(bankDhoranDetailsRawValue.dhoranName),
      startDate: new FormControl(bankDhoranDetailsRawValue.startDate),
      endDate: new FormControl(bankDhoranDetailsRawValue.endDate),
      year: new FormControl(bankDhoranDetailsRawValue.year),
      isActivate: new FormControl(bankDhoranDetailsRawValue.isActivate),
      lastModified: new FormControl(bankDhoranDetailsRawValue.lastModified),
      lastModifiedBy: new FormControl(bankDhoranDetailsRawValue.lastModifiedBy),
      freeField1: new FormControl(bankDhoranDetailsRawValue.freeField1),
      freeField2: new FormControl(bankDhoranDetailsRawValue.freeField2),
      freeField3: new FormControl(bankDhoranDetailsRawValue.freeField3),
      society: new FormControl(bankDhoranDetailsRawValue.society),
    });
  }

  getBankDhoranDetails(form: BankDhoranDetailsFormGroup): IBankDhoranDetails | NewBankDhoranDetails {
    return this.convertBankDhoranDetailsRawValueToBankDhoranDetails(
      form.getRawValue() as BankDhoranDetailsFormRawValue | NewBankDhoranDetailsFormRawValue
    );
  }

  resetForm(form: BankDhoranDetailsFormGroup, bankDhoranDetails: BankDhoranDetailsFormGroupInput): void {
    const bankDhoranDetailsRawValue = this.convertBankDhoranDetailsToBankDhoranDetailsRawValue({
      ...this.getFormDefaults(),
      ...bankDhoranDetails,
    });
    form.reset(
      {
        ...bankDhoranDetailsRawValue,
        id: { value: bankDhoranDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): BankDhoranDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      startDate: currentTime,
      endDate: currentTime,
      isActivate: false,
      lastModified: currentTime,
    };
  }

  private convertBankDhoranDetailsRawValueToBankDhoranDetails(
    rawBankDhoranDetails: BankDhoranDetailsFormRawValue | NewBankDhoranDetailsFormRawValue
  ): IBankDhoranDetails | NewBankDhoranDetails {
    return {
      ...rawBankDhoranDetails,
      startDate: dayjs(rawBankDhoranDetails.startDate, DATE_TIME_FORMAT),
      endDate: dayjs(rawBankDhoranDetails.endDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawBankDhoranDetails.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertBankDhoranDetailsToBankDhoranDetailsRawValue(
    bankDhoranDetails: IBankDhoranDetails | (Partial<NewBankDhoranDetails> & BankDhoranDetailsFormDefaults)
  ): BankDhoranDetailsFormRawValue | PartialWithRequiredKeyOf<NewBankDhoranDetailsFormRawValue> {
    return {
      ...bankDhoranDetails,
      startDate: bankDhoranDetails.startDate ? bankDhoranDetails.startDate.format(DATE_TIME_FORMAT) : undefined,
      endDate: bankDhoranDetails.endDate ? bankDhoranDetails.endDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: bankDhoranDetails.lastModified ? bankDhoranDetails.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
