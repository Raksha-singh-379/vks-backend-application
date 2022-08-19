import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILoanWatapDetails, NewLoanWatapDetails } from '../loan-watap-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILoanWatapDetails for edit and NewLoanWatapDetailsFormGroupInput for create.
 */
type LoanWatapDetailsFormGroupInput = ILoanWatapDetails | PartialWithRequiredKeyOf<NewLoanWatapDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILoanWatapDetails | NewLoanWatapDetails> = Omit<T, 'loanWatapDate' | 'lastModified'> & {
  loanWatapDate?: string | null;
  lastModified?: string | null;
};

type LoanWatapDetailsFormRawValue = FormValueOf<ILoanWatapDetails>;

type NewLoanWatapDetailsFormRawValue = FormValueOf<NewLoanWatapDetails>;

type LoanWatapDetailsFormDefaults = Pick<NewLoanWatapDetails, 'id' | 'loanWatapDate' | 'isDeleted' | 'lastModified'>;

type LoanWatapDetailsFormGroupContent = {
  id: FormControl<LoanWatapDetailsFormRawValue['id'] | NewLoanWatapDetails['id']>;
  loanWatapDate: FormControl<LoanWatapDetailsFormRawValue['loanWatapDate']>;
  cropLandInHector: FormControl<LoanWatapDetailsFormRawValue['cropLandInHector']>;
  slotNumber: FormControl<LoanWatapDetailsFormRawValue['slotNumber']>;
  loanAmount: FormControl<LoanWatapDetailsFormRawValue['loanAmount']>;
  season: FormControl<LoanWatapDetailsFormRawValue['season']>;
  status: FormControl<LoanWatapDetailsFormRawValue['status']>;
  year: FormControl<LoanWatapDetailsFormRawValue['year']>;
  isDeleted: FormControl<LoanWatapDetailsFormRawValue['isDeleted']>;
  lastModified: FormControl<LoanWatapDetailsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<LoanWatapDetailsFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<LoanWatapDetailsFormRawValue['freeField1']>;
  freeField2: FormControl<LoanWatapDetailsFormRawValue['freeField2']>;
  freeField3: FormControl<LoanWatapDetailsFormRawValue['freeField3']>;
  loanDemand: FormControl<LoanWatapDetailsFormRawValue['loanDemand']>;
};

export type LoanWatapDetailsFormGroup = FormGroup<LoanWatapDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LoanWatapDetailsFormService {
  createLoanWatapDetailsFormGroup(loanWatapDetails: LoanWatapDetailsFormGroupInput = { id: null }): LoanWatapDetailsFormGroup {
    const loanWatapDetailsRawValue = this.convertLoanWatapDetailsToLoanWatapDetailsRawValue({
      ...this.getFormDefaults(),
      ...loanWatapDetails,
    });
    return new FormGroup<LoanWatapDetailsFormGroupContent>({
      id: new FormControl(
        { value: loanWatapDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      loanWatapDate: new FormControl(loanWatapDetailsRawValue.loanWatapDate),
      cropLandInHector: new FormControl(loanWatapDetailsRawValue.cropLandInHector),
      slotNumber: new FormControl(loanWatapDetailsRawValue.slotNumber),
      loanAmount: new FormControl(loanWatapDetailsRawValue.loanAmount),
      season: new FormControl(loanWatapDetailsRawValue.season),
      status: new FormControl(loanWatapDetailsRawValue.status),
      year: new FormControl(loanWatapDetailsRawValue.year),
      isDeleted: new FormControl(loanWatapDetailsRawValue.isDeleted),
      lastModified: new FormControl(loanWatapDetailsRawValue.lastModified),
      lastModifiedBy: new FormControl(loanWatapDetailsRawValue.lastModifiedBy),
      freeField1: new FormControl(loanWatapDetailsRawValue.freeField1),
      freeField2: new FormControl(loanWatapDetailsRawValue.freeField2),
      freeField3: new FormControl(loanWatapDetailsRawValue.freeField3),
      loanDemand: new FormControl(loanWatapDetailsRawValue.loanDemand),
    });
  }

  getLoanWatapDetails(form: LoanWatapDetailsFormGroup): ILoanWatapDetails | NewLoanWatapDetails {
    return this.convertLoanWatapDetailsRawValueToLoanWatapDetails(
      form.getRawValue() as LoanWatapDetailsFormRawValue | NewLoanWatapDetailsFormRawValue
    );
  }

  resetForm(form: LoanWatapDetailsFormGroup, loanWatapDetails: LoanWatapDetailsFormGroupInput): void {
    const loanWatapDetailsRawValue = this.convertLoanWatapDetailsToLoanWatapDetailsRawValue({
      ...this.getFormDefaults(),
      ...loanWatapDetails,
    });
    form.reset(
      {
        ...loanWatapDetailsRawValue,
        id: { value: loanWatapDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LoanWatapDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      loanWatapDate: currentTime,
      isDeleted: false,
      lastModified: currentTime,
    };
  }

  private convertLoanWatapDetailsRawValueToLoanWatapDetails(
    rawLoanWatapDetails: LoanWatapDetailsFormRawValue | NewLoanWatapDetailsFormRawValue
  ): ILoanWatapDetails | NewLoanWatapDetails {
    return {
      ...rawLoanWatapDetails,
      loanWatapDate: dayjs(rawLoanWatapDetails.loanWatapDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawLoanWatapDetails.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertLoanWatapDetailsToLoanWatapDetailsRawValue(
    loanWatapDetails: ILoanWatapDetails | (Partial<NewLoanWatapDetails> & LoanWatapDetailsFormDefaults)
  ): LoanWatapDetailsFormRawValue | PartialWithRequiredKeyOf<NewLoanWatapDetailsFormRawValue> {
    return {
      ...loanWatapDetails,
      loanWatapDate: loanWatapDetails.loanWatapDate ? loanWatapDetails.loanWatapDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: loanWatapDetails.lastModified ? loanWatapDetails.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
