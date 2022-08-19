import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILoanDetails, NewLoanDetails } from '../loan-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILoanDetails for edit and NewLoanDetailsFormGroupInput for create.
 */
type LoanDetailsFormGroupInput = ILoanDetails | PartialWithRequiredKeyOf<NewLoanDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILoanDetails | NewLoanDetails> = Omit<
  T,
  | 'loanStartDate'
  | 'loanEndDate'
  | 'loanPlannedClosureDate'
  | 'loanCloserDate'
  | 'loanEffectiveDate'
  | 'resolutionDate'
  | 'mortgageDate'
  | 'lastModified'
> & {
  loanStartDate?: string | null;
  loanEndDate?: string | null;
  loanPlannedClosureDate?: string | null;
  loanCloserDate?: string | null;
  loanEffectiveDate?: string | null;
  resolutionDate?: string | null;
  mortgageDate?: string | null;
  lastModified?: string | null;
};

type LoanDetailsFormRawValue = FormValueOf<ILoanDetails>;

type NewLoanDetailsFormRawValue = FormValueOf<NewLoanDetails>;

type LoanDetailsFormDefaults = Pick<
  NewLoanDetails,
  | 'id'
  | 'loanStartDate'
  | 'loanEndDate'
  | 'loanPlannedClosureDate'
  | 'loanCloserDate'
  | 'loanEffectiveDate'
  | 'resolutionDate'
  | 'mortgageDate'
  | 'lastModified'
>;

type LoanDetailsFormGroupContent = {
  id: FormControl<LoanDetailsFormRawValue['id'] | NewLoanDetails['id']>;
  loanAmount: FormControl<LoanDetailsFormRawValue['loanAmount']>;
  loanAccountNo: FormControl<LoanDetailsFormRawValue['loanAccountNo']>;
  loanType: FormControl<LoanDetailsFormRawValue['loanType']>;
  status: FormControl<LoanDetailsFormRawValue['status']>;
  loanStartDate: FormControl<LoanDetailsFormRawValue['loanStartDate']>;
  loanEndDate: FormControl<LoanDetailsFormRawValue['loanEndDate']>;
  loanPlannedClosureDate: FormControl<LoanDetailsFormRawValue['loanPlannedClosureDate']>;
  loanCloserDate: FormControl<LoanDetailsFormRawValue['loanCloserDate']>;
  loanEffectiveDate: FormControl<LoanDetailsFormRawValue['loanEffectiveDate']>;
  loanClassification: FormControl<LoanDetailsFormRawValue['loanClassification']>;
  resolutionNo: FormControl<LoanDetailsFormRawValue['resolutionNo']>;
  resolutionDate: FormControl<LoanDetailsFormRawValue['resolutionDate']>;
  costOfInvestment: FormControl<LoanDetailsFormRawValue['costOfInvestment']>;
  loanBenefitingArea: FormControl<LoanDetailsFormRawValue['loanBenefitingArea']>;
  dccbLoanNo: FormControl<LoanDetailsFormRawValue['dccbLoanNo']>;
  mortgageDeedNo: FormControl<LoanDetailsFormRawValue['mortgageDeedNo']>;
  mortgageDate: FormControl<LoanDetailsFormRawValue['mortgageDate']>;
  extentMorgageValue: FormControl<LoanDetailsFormRawValue['extentMorgageValue']>;
  parentAccHeadCode: FormControl<LoanDetailsFormRawValue['parentAccHeadCode']>;
  loanAccountName: FormControl<LoanDetailsFormRawValue['loanAccountName']>;
  disbursementAmt: FormControl<LoanDetailsFormRawValue['disbursementAmt']>;
  disbursementStatus: FormControl<LoanDetailsFormRawValue['disbursementStatus']>;
  year: FormControl<LoanDetailsFormRawValue['year']>;
  lastModified: FormControl<LoanDetailsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<LoanDetailsFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<LoanDetailsFormRawValue['freeField1']>;
  freeField2: FormControl<LoanDetailsFormRawValue['freeField2']>;
  freeField3: FormControl<LoanDetailsFormRawValue['freeField3']>;
  loanDemand: FormControl<LoanDetailsFormRawValue['loanDemand']>;
  member: FormControl<LoanDetailsFormRawValue['member']>;
  loanDemand: FormControl<LoanDetailsFormRawValue['loanDemand']>;
  societyLoanProduct: FormControl<LoanDetailsFormRawValue['societyLoanProduct']>;
  bankDhoranDetails: FormControl<LoanDetailsFormRawValue['bankDhoranDetails']>;
};

export type LoanDetailsFormGroup = FormGroup<LoanDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LoanDetailsFormService {
  createLoanDetailsFormGroup(loanDetails: LoanDetailsFormGroupInput = { id: null }): LoanDetailsFormGroup {
    const loanDetailsRawValue = this.convertLoanDetailsToLoanDetailsRawValue({
      ...this.getFormDefaults(),
      ...loanDetails,
    });
    return new FormGroup<LoanDetailsFormGroupContent>({
      id: new FormControl(
        { value: loanDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      loanAmount: new FormControl(loanDetailsRawValue.loanAmount),
      loanAccountNo: new FormControl(loanDetailsRawValue.loanAccountNo),
      loanType: new FormControl(loanDetailsRawValue.loanType),
      status: new FormControl(loanDetailsRawValue.status),
      loanStartDate: new FormControl(loanDetailsRawValue.loanStartDate),
      loanEndDate: new FormControl(loanDetailsRawValue.loanEndDate),
      loanPlannedClosureDate: new FormControl(loanDetailsRawValue.loanPlannedClosureDate),
      loanCloserDate: new FormControl(loanDetailsRawValue.loanCloserDate),
      loanEffectiveDate: new FormControl(loanDetailsRawValue.loanEffectiveDate),
      loanClassification: new FormControl(loanDetailsRawValue.loanClassification),
      resolutionNo: new FormControl(loanDetailsRawValue.resolutionNo),
      resolutionDate: new FormControl(loanDetailsRawValue.resolutionDate),
      costOfInvestment: new FormControl(loanDetailsRawValue.costOfInvestment),
      loanBenefitingArea: new FormControl(loanDetailsRawValue.loanBenefitingArea),
      dccbLoanNo: new FormControl(loanDetailsRawValue.dccbLoanNo),
      mortgageDeedNo: new FormControl(loanDetailsRawValue.mortgageDeedNo),
      mortgageDate: new FormControl(loanDetailsRawValue.mortgageDate),
      extentMorgageValue: new FormControl(loanDetailsRawValue.extentMorgageValue),
      parentAccHeadCode: new FormControl(loanDetailsRawValue.parentAccHeadCode),
      loanAccountName: new FormControl(loanDetailsRawValue.loanAccountName),
      disbursementAmt: new FormControl(loanDetailsRawValue.disbursementAmt),
      disbursementStatus: new FormControl(loanDetailsRawValue.disbursementStatus),
      year: new FormControl(loanDetailsRawValue.year),
      lastModified: new FormControl(loanDetailsRawValue.lastModified),
      lastModifiedBy: new FormControl(loanDetailsRawValue.lastModifiedBy),
      freeField1: new FormControl(loanDetailsRawValue.freeField1),
      freeField2: new FormControl(loanDetailsRawValue.freeField2),
      freeField3: new FormControl(loanDetailsRawValue.freeField3),
      loanDemand: new FormControl(loanDetailsRawValue.loanDemand),
      member: new FormControl(loanDetailsRawValue.member),
      loanDemand: new FormControl(loanDetailsRawValue.loanDemand),
      societyLoanProduct: new FormControl(loanDetailsRawValue.societyLoanProduct),
      bankDhoranDetails: new FormControl(loanDetailsRawValue.bankDhoranDetails),
    });
  }

  getLoanDetails(form: LoanDetailsFormGroup): ILoanDetails | NewLoanDetails {
    return this.convertLoanDetailsRawValueToLoanDetails(form.getRawValue() as LoanDetailsFormRawValue | NewLoanDetailsFormRawValue);
  }

  resetForm(form: LoanDetailsFormGroup, loanDetails: LoanDetailsFormGroupInput): void {
    const loanDetailsRawValue = this.convertLoanDetailsToLoanDetailsRawValue({ ...this.getFormDefaults(), ...loanDetails });
    form.reset(
      {
        ...loanDetailsRawValue,
        id: { value: loanDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LoanDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      loanStartDate: currentTime,
      loanEndDate: currentTime,
      loanPlannedClosureDate: currentTime,
      loanCloserDate: currentTime,
      loanEffectiveDate: currentTime,
      resolutionDate: currentTime,
      mortgageDate: currentTime,
      lastModified: currentTime,
    };
  }

  private convertLoanDetailsRawValueToLoanDetails(
    rawLoanDetails: LoanDetailsFormRawValue | NewLoanDetailsFormRawValue
  ): ILoanDetails | NewLoanDetails {
    return {
      ...rawLoanDetails,
      loanStartDate: dayjs(rawLoanDetails.loanStartDate, DATE_TIME_FORMAT),
      loanEndDate: dayjs(rawLoanDetails.loanEndDate, DATE_TIME_FORMAT),
      loanPlannedClosureDate: dayjs(rawLoanDetails.loanPlannedClosureDate, DATE_TIME_FORMAT),
      loanCloserDate: dayjs(rawLoanDetails.loanCloserDate, DATE_TIME_FORMAT),
      loanEffectiveDate: dayjs(rawLoanDetails.loanEffectiveDate, DATE_TIME_FORMAT),
      resolutionDate: dayjs(rawLoanDetails.resolutionDate, DATE_TIME_FORMAT),
      mortgageDate: dayjs(rawLoanDetails.mortgageDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawLoanDetails.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertLoanDetailsToLoanDetailsRawValue(
    loanDetails: ILoanDetails | (Partial<NewLoanDetails> & LoanDetailsFormDefaults)
  ): LoanDetailsFormRawValue | PartialWithRequiredKeyOf<NewLoanDetailsFormRawValue> {
    return {
      ...loanDetails,
      loanStartDate: loanDetails.loanStartDate ? loanDetails.loanStartDate.format(DATE_TIME_FORMAT) : undefined,
      loanEndDate: loanDetails.loanEndDate ? loanDetails.loanEndDate.format(DATE_TIME_FORMAT) : undefined,
      loanPlannedClosureDate: loanDetails.loanPlannedClosureDate ? loanDetails.loanPlannedClosureDate.format(DATE_TIME_FORMAT) : undefined,
      loanCloserDate: loanDetails.loanCloserDate ? loanDetails.loanCloserDate.format(DATE_TIME_FORMAT) : undefined,
      loanEffectiveDate: loanDetails.loanEffectiveDate ? loanDetails.loanEffectiveDate.format(DATE_TIME_FORMAT) : undefined,
      resolutionDate: loanDetails.resolutionDate ? loanDetails.resolutionDate.format(DATE_TIME_FORMAT) : undefined,
      mortgageDate: loanDetails.mortgageDate ? loanDetails.mortgageDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: loanDetails.lastModified ? loanDetails.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
