import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ILoanDemand, NewLoanDemand } from '../loan-demand.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILoanDemand for edit and NewLoanDemandFormGroupInput for create.
 */
type LoanDemandFormGroupInput = ILoanDemand | PartialWithRequiredKeyOf<NewLoanDemand>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ILoanDemand | NewLoanDemand> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

type LoanDemandFormRawValue = FormValueOf<ILoanDemand>;

type NewLoanDemandFormRawValue = FormValueOf<NewLoanDemand>;

type LoanDemandFormDefaults = Pick<NewLoanDemand, 'id' | 'lastModified'>;

type LoanDemandFormGroupContent = {
  id: FormControl<LoanDemandFormRawValue['id'] | NewLoanDemand['id']>;
  loanDemandAmount: FormControl<LoanDemandFormRawValue['loanDemandAmount']>;
  maxAllowedAmount: FormControl<LoanDemandFormRawValue['maxAllowedAmount']>;
  adjustedDemand: FormControl<LoanDemandFormRawValue['adjustedDemand']>;
  annualIncome: FormControl<LoanDemandFormRawValue['annualIncome']>;
  costOfInvestment: FormControl<LoanDemandFormRawValue['costOfInvestment']>;
  demandedLandAreaInHector: FormControl<LoanDemandFormRawValue['demandedLandAreaInHector']>;
  status: FormControl<LoanDemandFormRawValue['status']>;
  seasonOfCropLoan: FormControl<LoanDemandFormRawValue['seasonOfCropLoan']>;
  year: FormControl<LoanDemandFormRawValue['year']>;
  lastModified: FormControl<LoanDemandFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<LoanDemandFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<LoanDemandFormRawValue['freeField1']>;
  freeField2: FormControl<LoanDemandFormRawValue['freeField2']>;
  freeField3: FormControl<LoanDemandFormRawValue['freeField3']>;
  member: FormControl<LoanDemandFormRawValue['member']>;
  societyLoanProduct: FormControl<LoanDemandFormRawValue['societyLoanProduct']>;
  memberLandAssets: FormControl<LoanDemandFormRawValue['memberLandAssets']>;
  societyCropRegistration: FormControl<LoanDemandFormRawValue['societyCropRegistration']>;
};

export type LoanDemandFormGroup = FormGroup<LoanDemandFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LoanDemandFormService {
  createLoanDemandFormGroup(loanDemand: LoanDemandFormGroupInput = { id: null }): LoanDemandFormGroup {
    const loanDemandRawValue = this.convertLoanDemandToLoanDemandRawValue({
      ...this.getFormDefaults(),
      ...loanDemand,
    });
    return new FormGroup<LoanDemandFormGroupContent>({
      id: new FormControl(
        { value: loanDemandRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      loanDemandAmount: new FormControl(loanDemandRawValue.loanDemandAmount),
      maxAllowedAmount: new FormControl(loanDemandRawValue.maxAllowedAmount),
      adjustedDemand: new FormControl(loanDemandRawValue.adjustedDemand),
      annualIncome: new FormControl(loanDemandRawValue.annualIncome),
      costOfInvestment: new FormControl(loanDemandRawValue.costOfInvestment),
      demandedLandAreaInHector: new FormControl(loanDemandRawValue.demandedLandAreaInHector),
      status: new FormControl(loanDemandRawValue.status),
      seasonOfCropLoan: new FormControl(loanDemandRawValue.seasonOfCropLoan),
      year: new FormControl(loanDemandRawValue.year),
      lastModified: new FormControl(loanDemandRawValue.lastModified),
      lastModifiedBy: new FormControl(loanDemandRawValue.lastModifiedBy),
      freeField1: new FormControl(loanDemandRawValue.freeField1),
      freeField2: new FormControl(loanDemandRawValue.freeField2),
      freeField3: new FormControl(loanDemandRawValue.freeField3),
      member: new FormControl(loanDemandRawValue.member),
      societyLoanProduct: new FormControl(loanDemandRawValue.societyLoanProduct),
      memberLandAssets: new FormControl(loanDemandRawValue.memberLandAssets),
      societyCropRegistration: new FormControl(loanDemandRawValue.societyCropRegistration),
    });
  }

  getLoanDemand(form: LoanDemandFormGroup): ILoanDemand | NewLoanDemand {
    return this.convertLoanDemandRawValueToLoanDemand(form.getRawValue() as LoanDemandFormRawValue | NewLoanDemandFormRawValue);
  }

  resetForm(form: LoanDemandFormGroup, loanDemand: LoanDemandFormGroupInput): void {
    const loanDemandRawValue = this.convertLoanDemandToLoanDemandRawValue({ ...this.getFormDefaults(), ...loanDemand });
    form.reset(
      {
        ...loanDemandRawValue,
        id: { value: loanDemandRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LoanDemandFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
    };
  }

  private convertLoanDemandRawValueToLoanDemand(
    rawLoanDemand: LoanDemandFormRawValue | NewLoanDemandFormRawValue
  ): ILoanDemand | NewLoanDemand {
    return {
      ...rawLoanDemand,
      lastModified: dayjs(rawLoanDemand.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertLoanDemandToLoanDemandRawValue(
    loanDemand: ILoanDemand | (Partial<NewLoanDemand> & LoanDemandFormDefaults)
  ): LoanDemandFormRawValue | PartialWithRequiredKeyOf<NewLoanDemandFormRawValue> {
    return {
      ...loanDemand,
      lastModified: loanDemand.lastModified ? loanDemand.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
