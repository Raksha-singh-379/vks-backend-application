import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IGRInterestDetails, NewGRInterestDetails } from '../gr-interest-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGRInterestDetails for edit and NewGRInterestDetailsFormGroupInput for create.
 */
type GRInterestDetailsFormGroupInput = IGRInterestDetails | PartialWithRequiredKeyOf<NewGRInterestDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IGRInterestDetails | NewGRInterestDetails> = Omit<T, 'startDate' | 'endDate' | 'lastModified' | 'createdOn'> & {
  startDate?: string | null;
  endDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

type GRInterestDetailsFormRawValue = FormValueOf<IGRInterestDetails>;

type NewGRInterestDetailsFormRawValue = FormValueOf<NewGRInterestDetails>;

type GRInterestDetailsFormDefaults = Pick<
  NewGRInterestDetails,
  'id' | 'isActivated' | 'startDate' | 'endDate' | 'lastModified' | 'createdOn' | 'isDeleted'
>;

type GRInterestDetailsFormGroupContent = {
  id: FormControl<GRInterestDetailsFormRawValue['id'] | NewGRInterestDetails['id']>;
  loanGrName: FormControl<GRInterestDetailsFormRawValue['loanGrName']>;
  criteria: FormControl<GRInterestDetailsFormRawValue['criteria']>;
  productType: FormControl<GRInterestDetailsFormRawValue['productType']>;
  isActivated: FormControl<GRInterestDetailsFormRawValue['isActivated']>;
  borrowingInterestRate: FormControl<GRInterestDetailsFormRawValue['borrowingInterestRate']>;
  interestOnLoan: FormControl<GRInterestDetailsFormRawValue['interestOnLoan']>;
  penaltyInterest: FormControl<GRInterestDetailsFormRawValue['penaltyInterest']>;
  surcharge: FormControl<GRInterestDetailsFormRawValue['surcharge']>;
  loanDuration: FormControl<GRInterestDetailsFormRawValue['loanDuration']>;
  numberOFInstallment: FormControl<GRInterestDetailsFormRawValue['numberOFInstallment']>;
  extendedInterstRate: FormControl<GRInterestDetailsFormRawValue['extendedInterstRate']>;
  centralGovSubsidyInterest: FormControl<GRInterestDetailsFormRawValue['centralGovSubsidyInterest']>;
  distBankSubsidyInterest: FormControl<GRInterestDetailsFormRawValue['distBankSubsidyInterest']>;
  borrowerInterest: FormControl<GRInterestDetailsFormRawValue['borrowerInterest']>;
  stateGovSubsidyInterest: FormControl<GRInterestDetailsFormRawValue['stateGovSubsidyInterest']>;
  year: FormControl<GRInterestDetailsFormRawValue['year']>;
  startDate: FormControl<GRInterestDetailsFormRawValue['startDate']>;
  endDate: FormControl<GRInterestDetailsFormRawValue['endDate']>;
  lastModified: FormControl<GRInterestDetailsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<GRInterestDetailsFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<GRInterestDetailsFormRawValue['createdBy']>;
  createdOn: FormControl<GRInterestDetailsFormRawValue['createdOn']>;
  isDeleted: FormControl<GRInterestDetailsFormRawValue['isDeleted']>;
  freeField1: FormControl<GRInterestDetailsFormRawValue['freeField1']>;
  freeField2: FormControl<GRInterestDetailsFormRawValue['freeField2']>;
  freeField3: FormControl<GRInterestDetailsFormRawValue['freeField3']>;
  society: FormControl<GRInterestDetailsFormRawValue['society']>;
};

export type GRInterestDetailsFormGroup = FormGroup<GRInterestDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GRInterestDetailsFormService {
  createGRInterestDetailsFormGroup(gRInterestDetails: GRInterestDetailsFormGroupInput = { id: null }): GRInterestDetailsFormGroup {
    const gRInterestDetailsRawValue = this.convertGRInterestDetailsToGRInterestDetailsRawValue({
      ...this.getFormDefaults(),
      ...gRInterestDetails,
    });
    return new FormGroup<GRInterestDetailsFormGroupContent>({
      id: new FormControl(
        { value: gRInterestDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      loanGrName: new FormControl(gRInterestDetailsRawValue.loanGrName),
      criteria: new FormControl(gRInterestDetailsRawValue.criteria),
      productType: new FormControl(gRInterestDetailsRawValue.productType),
      isActivated: new FormControl(gRInterestDetailsRawValue.isActivated),
      borrowingInterestRate: new FormControl(gRInterestDetailsRawValue.borrowingInterestRate),
      interestOnLoan: new FormControl(gRInterestDetailsRawValue.interestOnLoan),
      penaltyInterest: new FormControl(gRInterestDetailsRawValue.penaltyInterest),
      surcharge: new FormControl(gRInterestDetailsRawValue.surcharge),
      loanDuration: new FormControl(gRInterestDetailsRawValue.loanDuration),
      numberOFInstallment: new FormControl(gRInterestDetailsRawValue.numberOFInstallment),
      extendedInterstRate: new FormControl(gRInterestDetailsRawValue.extendedInterstRate),
      centralGovSubsidyInterest: new FormControl(gRInterestDetailsRawValue.centralGovSubsidyInterest),
      distBankSubsidyInterest: new FormControl(gRInterestDetailsRawValue.distBankSubsidyInterest),
      borrowerInterest: new FormControl(gRInterestDetailsRawValue.borrowerInterest),
      stateGovSubsidyInterest: new FormControl(gRInterestDetailsRawValue.stateGovSubsidyInterest),
      year: new FormControl(gRInterestDetailsRawValue.year),
      startDate: new FormControl(gRInterestDetailsRawValue.startDate),
      endDate: new FormControl(gRInterestDetailsRawValue.endDate),
      lastModified: new FormControl(gRInterestDetailsRawValue.lastModified),
      lastModifiedBy: new FormControl(gRInterestDetailsRawValue.lastModifiedBy),
      createdBy: new FormControl(gRInterestDetailsRawValue.createdBy),
      createdOn: new FormControl(gRInterestDetailsRawValue.createdOn),
      isDeleted: new FormControl(gRInterestDetailsRawValue.isDeleted),
      freeField1: new FormControl(gRInterestDetailsRawValue.freeField1),
      freeField2: new FormControl(gRInterestDetailsRawValue.freeField2),
      freeField3: new FormControl(gRInterestDetailsRawValue.freeField3),
      society: new FormControl(gRInterestDetailsRawValue.society),
    });
  }

  getGRInterestDetails(form: GRInterestDetailsFormGroup): IGRInterestDetails | NewGRInterestDetails {
    return this.convertGRInterestDetailsRawValueToGRInterestDetails(
      form.getRawValue() as GRInterestDetailsFormRawValue | NewGRInterestDetailsFormRawValue
    );
  }

  resetForm(form: GRInterestDetailsFormGroup, gRInterestDetails: GRInterestDetailsFormGroupInput): void {
    const gRInterestDetailsRawValue = this.convertGRInterestDetailsToGRInterestDetailsRawValue({
      ...this.getFormDefaults(),
      ...gRInterestDetails,
    });
    form.reset(
      {
        ...gRInterestDetailsRawValue,
        id: { value: gRInterestDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): GRInterestDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isActivated: false,
      startDate: currentTime,
      endDate: currentTime,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertGRInterestDetailsRawValueToGRInterestDetails(
    rawGRInterestDetails: GRInterestDetailsFormRawValue | NewGRInterestDetailsFormRawValue
  ): IGRInterestDetails | NewGRInterestDetails {
    return {
      ...rawGRInterestDetails,
      startDate: dayjs(rawGRInterestDetails.startDate, DATE_TIME_FORMAT),
      endDate: dayjs(rawGRInterestDetails.endDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawGRInterestDetails.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawGRInterestDetails.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertGRInterestDetailsToGRInterestDetailsRawValue(
    gRInterestDetails: IGRInterestDetails | (Partial<NewGRInterestDetails> & GRInterestDetailsFormDefaults)
  ): GRInterestDetailsFormRawValue | PartialWithRequiredKeyOf<NewGRInterestDetailsFormRawValue> {
    return {
      ...gRInterestDetails,
      startDate: gRInterestDetails.startDate ? gRInterestDetails.startDate.format(DATE_TIME_FORMAT) : undefined,
      endDate: gRInterestDetails.endDate ? gRInterestDetails.endDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: gRInterestDetails.lastModified ? gRInterestDetails.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: gRInterestDetails.createdOn ? gRInterestDetails.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
