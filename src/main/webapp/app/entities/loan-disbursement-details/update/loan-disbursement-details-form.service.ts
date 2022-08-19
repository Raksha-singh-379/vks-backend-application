import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILoanDisbursementDetails, NewLoanDisbursementDetails } from '../loan-disbursement-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILoanDisbursementDetails for edit and NewLoanDisbursementDetailsFormGroupInput for create.
 */
type LoanDisbursementDetailsFormGroupInput = ILoanDisbursementDetails | PartialWithRequiredKeyOf<NewLoanDisbursementDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILoanDisbursementDetails | NewLoanDisbursementDetails> = Omit<T, 'disbursementDate' | 'lastModified'> & {
  disbursementDate?: string | null;
  lastModified?: string | null;
};

type LoanDisbursementDetailsFormRawValue = FormValueOf<ILoanDisbursementDetails>;

type NewLoanDisbursementDetailsFormRawValue = FormValueOf<NewLoanDisbursementDetails>;

type LoanDisbursementDetailsFormDefaults = Pick<NewLoanDisbursementDetails, 'id' | 'disbursementDate' | 'lastModified'>;

type LoanDisbursementDetailsFormGroupContent = {
  id: FormControl<LoanDisbursementDetailsFormRawValue['id'] | NewLoanDisbursementDetails['id']>;
  disbursementDate: FormControl<LoanDisbursementDetailsFormRawValue['disbursementDate']>;
  disbursementAmount: FormControl<LoanDisbursementDetailsFormRawValue['disbursementAmount']>;
  disbursementNumber: FormControl<LoanDisbursementDetailsFormRawValue['disbursementNumber']>;
  disbursementStatus: FormControl<LoanDisbursementDetailsFormRawValue['disbursementStatus']>;
  paymentMode: FormControl<LoanDisbursementDetailsFormRawValue['paymentMode']>;
  type: FormControl<LoanDisbursementDetailsFormRawValue['type']>;
  lastModified: FormControl<LoanDisbursementDetailsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<LoanDisbursementDetailsFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<LoanDisbursementDetailsFormRawValue['freeField1']>;
  freeField2: FormControl<LoanDisbursementDetailsFormRawValue['freeField2']>;
  freeField3: FormControl<LoanDisbursementDetailsFormRawValue['freeField3']>;
  loanDetails: FormControl<LoanDisbursementDetailsFormRawValue['loanDetails']>;
};

export type LoanDisbursementDetailsFormGroup = FormGroup<LoanDisbursementDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LoanDisbursementDetailsFormService {
  createLoanDisbursementDetailsFormGroup(
    loanDisbursementDetails: LoanDisbursementDetailsFormGroupInput = { id: null }
  ): LoanDisbursementDetailsFormGroup {
    const loanDisbursementDetailsRawValue = this.convertLoanDisbursementDetailsToLoanDisbursementDetailsRawValue({
      ...this.getFormDefaults(),
      ...loanDisbursementDetails,
    });
    return new FormGroup<LoanDisbursementDetailsFormGroupContent>({
      id: new FormControl(
        { value: loanDisbursementDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      disbursementDate: new FormControl(loanDisbursementDetailsRawValue.disbursementDate),
      disbursementAmount: new FormControl(loanDisbursementDetailsRawValue.disbursementAmount),
      disbursementNumber: new FormControl(loanDisbursementDetailsRawValue.disbursementNumber),
      disbursementStatus: new FormControl(loanDisbursementDetailsRawValue.disbursementStatus),
      paymentMode: new FormControl(loanDisbursementDetailsRawValue.paymentMode),
      type: new FormControl(loanDisbursementDetailsRawValue.type),
      lastModified: new FormControl(loanDisbursementDetailsRawValue.lastModified),
      lastModifiedBy: new FormControl(loanDisbursementDetailsRawValue.lastModifiedBy),
      freeField1: new FormControl(loanDisbursementDetailsRawValue.freeField1),
      freeField2: new FormControl(loanDisbursementDetailsRawValue.freeField2),
      freeField3: new FormControl(loanDisbursementDetailsRawValue.freeField3),
      loanDetails: new FormControl(loanDisbursementDetailsRawValue.loanDetails),
    });
  }

  getLoanDisbursementDetails(form: LoanDisbursementDetailsFormGroup): ILoanDisbursementDetails | NewLoanDisbursementDetails {
    return this.convertLoanDisbursementDetailsRawValueToLoanDisbursementDetails(
      form.getRawValue() as LoanDisbursementDetailsFormRawValue | NewLoanDisbursementDetailsFormRawValue
    );
  }

  resetForm(form: LoanDisbursementDetailsFormGroup, loanDisbursementDetails: LoanDisbursementDetailsFormGroupInput): void {
    const loanDisbursementDetailsRawValue = this.convertLoanDisbursementDetailsToLoanDisbursementDetailsRawValue({
      ...this.getFormDefaults(),
      ...loanDisbursementDetails,
    });
    form.reset(
      {
        ...loanDisbursementDetailsRawValue,
        id: { value: loanDisbursementDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LoanDisbursementDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      disbursementDate: currentTime,
      lastModified: currentTime,
    };
  }

  private convertLoanDisbursementDetailsRawValueToLoanDisbursementDetails(
    rawLoanDisbursementDetails: LoanDisbursementDetailsFormRawValue | NewLoanDisbursementDetailsFormRawValue
  ): ILoanDisbursementDetails | NewLoanDisbursementDetails {
    return {
      ...rawLoanDisbursementDetails,
      disbursementDate: dayjs(rawLoanDisbursementDetails.disbursementDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawLoanDisbursementDetails.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertLoanDisbursementDetailsToLoanDisbursementDetailsRawValue(
    loanDisbursementDetails: ILoanDisbursementDetails | (Partial<NewLoanDisbursementDetails> & LoanDisbursementDetailsFormDefaults)
  ): LoanDisbursementDetailsFormRawValue | PartialWithRequiredKeyOf<NewLoanDisbursementDetailsFormRawValue> {
    return {
      ...loanDisbursementDetails,
      disbursementDate: loanDisbursementDetails.disbursementDate
        ? loanDisbursementDetails.disbursementDate.format(DATE_TIME_FORMAT)
        : undefined,
      lastModified: loanDisbursementDetails.lastModified ? loanDisbursementDetails.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
