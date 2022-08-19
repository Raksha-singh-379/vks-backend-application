import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISocietyLoanProduct, NewSocietyLoanProduct } from '../society-loan-product.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISocietyLoanProduct for edit and NewSocietyLoanProductFormGroupInput for create.
 */
type SocietyLoanProductFormGroupInput = ISocietyLoanProduct | PartialWithRequiredKeyOf<NewSocietyLoanProduct>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISocietyLoanProduct | NewSocietyLoanProduct> = Omit<
  T,
  'lastDateOfRepayment' | 'resolutionDate' | 'validFrom' | 'validTo' | 'createdOn' | 'lastModified'
> & {
  lastDateOfRepayment?: string | null;
  resolutionDate?: string | null;
  validFrom?: string | null;
  validTo?: string | null;
  createdOn?: string | null;
  lastModified?: string | null;
};

type SocietyLoanProductFormRawValue = FormValueOf<ISocietyLoanProduct>;

type NewSocietyLoanProductFormRawValue = FormValueOf<NewSocietyLoanProduct>;

type SocietyLoanProductFormDefaults = Pick<
  NewSocietyLoanProduct,
  'id' | 'lastDateOfRepayment' | 'resolutionDate' | 'validFrom' | 'validTo' | 'createdOn' | 'isActivate' | 'lastModified'
>;

type SocietyLoanProductFormGroupContent = {
  id: FormControl<SocietyLoanProductFormRawValue['id'] | NewSocietyLoanProduct['id']>;
  productName: FormControl<SocietyLoanProductFormRawValue['productName']>;
  accHeadCode: FormControl<SocietyLoanProductFormRawValue['accHeadCode']>;
  borrowingInterestRate: FormControl<SocietyLoanProductFormRawValue['borrowingInterestRate']>;
  duration: FormControl<SocietyLoanProductFormRawValue['duration']>;
  interestRate: FormControl<SocietyLoanProductFormRawValue['interestRate']>;
  lastDateOfRepayment: FormControl<SocietyLoanProductFormRawValue['lastDateOfRepayment']>;
  loanNumber: FormControl<SocietyLoanProductFormRawValue['loanNumber']>;
  maxLoanAmt: FormControl<SocietyLoanProductFormRawValue['maxLoanAmt']>;
  noOfDisbursement: FormControl<SocietyLoanProductFormRawValue['noOfDisbursement']>;
  noOfInstallment: FormControl<SocietyLoanProductFormRawValue['noOfInstallment']>;
  parentAccHeadCode: FormControl<SocietyLoanProductFormRawValue['parentAccHeadCode']>;
  parentAccHeadId: FormControl<SocietyLoanProductFormRawValue['parentAccHeadId']>;
  penaltyInterest: FormControl<SocietyLoanProductFormRawValue['penaltyInterest']>;
  productType: FormControl<SocietyLoanProductFormRawValue['productType']>;
  resolutionDate: FormControl<SocietyLoanProductFormRawValue['resolutionDate']>;
  resolutionNo: FormControl<SocietyLoanProductFormRawValue['resolutionNo']>;
  status: FormControl<SocietyLoanProductFormRawValue['status']>;
  surcharge: FormControl<SocietyLoanProductFormRawValue['surcharge']>;
  unitSize: FormControl<SocietyLoanProductFormRawValue['unitSize']>;
  validFrom: FormControl<SocietyLoanProductFormRawValue['validFrom']>;
  validTo: FormControl<SocietyLoanProductFormRawValue['validTo']>;
  createdOn: FormControl<SocietyLoanProductFormRawValue['createdOn']>;
  createdBy: FormControl<SocietyLoanProductFormRawValue['createdBy']>;
  isActivate: FormControl<SocietyLoanProductFormRawValue['isActivate']>;
  lastModified: FormControl<SocietyLoanProductFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<SocietyLoanProductFormRawValue['lastModifiedBy']>;
  freeField1: FormControl<SocietyLoanProductFormRawValue['freeField1']>;
  freeField2: FormControl<SocietyLoanProductFormRawValue['freeField2']>;
  freeField3: FormControl<SocietyLoanProductFormRawValue['freeField3']>;
  society: FormControl<SocietyLoanProductFormRawValue['society']>;
  bankDhoranDetails: FormControl<SocietyLoanProductFormRawValue['bankDhoranDetails']>;
  gRInterestDetails: FormControl<SocietyLoanProductFormRawValue['gRInterestDetails']>;
};

export type SocietyLoanProductFormGroup = FormGroup<SocietyLoanProductFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SocietyLoanProductFormService {
  createSocietyLoanProductFormGroup(societyLoanProduct: SocietyLoanProductFormGroupInput = { id: null }): SocietyLoanProductFormGroup {
    const societyLoanProductRawValue = this.convertSocietyLoanProductToSocietyLoanProductRawValue({
      ...this.getFormDefaults(),
      ...societyLoanProduct,
    });
    return new FormGroup<SocietyLoanProductFormGroupContent>({
      id: new FormControl(
        { value: societyLoanProductRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      productName: new FormControl(societyLoanProductRawValue.productName),
      accHeadCode: new FormControl(societyLoanProductRawValue.accHeadCode),
      borrowingInterestRate: new FormControl(societyLoanProductRawValue.borrowingInterestRate),
      duration: new FormControl(societyLoanProductRawValue.duration),
      interestRate: new FormControl(societyLoanProductRawValue.interestRate),
      lastDateOfRepayment: new FormControl(societyLoanProductRawValue.lastDateOfRepayment),
      loanNumber: new FormControl(societyLoanProductRawValue.loanNumber),
      maxLoanAmt: new FormControl(societyLoanProductRawValue.maxLoanAmt),
      noOfDisbursement: new FormControl(societyLoanProductRawValue.noOfDisbursement),
      noOfInstallment: new FormControl(societyLoanProductRawValue.noOfInstallment),
      parentAccHeadCode: new FormControl(societyLoanProductRawValue.parentAccHeadCode),
      parentAccHeadId: new FormControl(societyLoanProductRawValue.parentAccHeadId),
      penaltyInterest: new FormControl(societyLoanProductRawValue.penaltyInterest),
      productType: new FormControl(societyLoanProductRawValue.productType),
      resolutionDate: new FormControl(societyLoanProductRawValue.resolutionDate),
      resolutionNo: new FormControl(societyLoanProductRawValue.resolutionNo),
      status: new FormControl(societyLoanProductRawValue.status),
      surcharge: new FormControl(societyLoanProductRawValue.surcharge),
      unitSize: new FormControl(societyLoanProductRawValue.unitSize),
      validFrom: new FormControl(societyLoanProductRawValue.validFrom),
      validTo: new FormControl(societyLoanProductRawValue.validTo),
      createdOn: new FormControl(societyLoanProductRawValue.createdOn),
      createdBy: new FormControl(societyLoanProductRawValue.createdBy),
      isActivate: new FormControl(societyLoanProductRawValue.isActivate),
      lastModified: new FormControl(societyLoanProductRawValue.lastModified),
      lastModifiedBy: new FormControl(societyLoanProductRawValue.lastModifiedBy),
      freeField1: new FormControl(societyLoanProductRawValue.freeField1),
      freeField2: new FormControl(societyLoanProductRawValue.freeField2),
      freeField3: new FormControl(societyLoanProductRawValue.freeField3),
      society: new FormControl(societyLoanProductRawValue.society),
      bankDhoranDetails: new FormControl(societyLoanProductRawValue.bankDhoranDetails),
      gRInterestDetails: new FormControl(societyLoanProductRawValue.gRInterestDetails),
    });
  }

  getSocietyLoanProduct(form: SocietyLoanProductFormGroup): ISocietyLoanProduct | NewSocietyLoanProduct {
    return this.convertSocietyLoanProductRawValueToSocietyLoanProduct(
      form.getRawValue() as SocietyLoanProductFormRawValue | NewSocietyLoanProductFormRawValue
    );
  }

  resetForm(form: SocietyLoanProductFormGroup, societyLoanProduct: SocietyLoanProductFormGroupInput): void {
    const societyLoanProductRawValue = this.convertSocietyLoanProductToSocietyLoanProductRawValue({
      ...this.getFormDefaults(),
      ...societyLoanProduct,
    });
    form.reset(
      {
        ...societyLoanProductRawValue,
        id: { value: societyLoanProductRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SocietyLoanProductFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastDateOfRepayment: currentTime,
      resolutionDate: currentTime,
      validFrom: currentTime,
      validTo: currentTime,
      createdOn: currentTime,
      isActivate: false,
      lastModified: currentTime,
    };
  }

  private convertSocietyLoanProductRawValueToSocietyLoanProduct(
    rawSocietyLoanProduct: SocietyLoanProductFormRawValue | NewSocietyLoanProductFormRawValue
  ): ISocietyLoanProduct | NewSocietyLoanProduct {
    return {
      ...rawSocietyLoanProduct,
      lastDateOfRepayment: dayjs(rawSocietyLoanProduct.lastDateOfRepayment, DATE_TIME_FORMAT),
      resolutionDate: dayjs(rawSocietyLoanProduct.resolutionDate, DATE_TIME_FORMAT),
      validFrom: dayjs(rawSocietyLoanProduct.validFrom, DATE_TIME_FORMAT),
      validTo: dayjs(rawSocietyLoanProduct.validTo, DATE_TIME_FORMAT),
      createdOn: dayjs(rawSocietyLoanProduct.createdOn, DATE_TIME_FORMAT),
      lastModified: dayjs(rawSocietyLoanProduct.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertSocietyLoanProductToSocietyLoanProductRawValue(
    societyLoanProduct: ISocietyLoanProduct | (Partial<NewSocietyLoanProduct> & SocietyLoanProductFormDefaults)
  ): SocietyLoanProductFormRawValue | PartialWithRequiredKeyOf<NewSocietyLoanProductFormRawValue> {
    return {
      ...societyLoanProduct,
      lastDateOfRepayment: societyLoanProduct.lastDateOfRepayment
        ? societyLoanProduct.lastDateOfRepayment.format(DATE_TIME_FORMAT)
        : undefined,
      resolutionDate: societyLoanProduct.resolutionDate ? societyLoanProduct.resolutionDate.format(DATE_TIME_FORMAT) : undefined,
      validFrom: societyLoanProduct.validFrom ? societyLoanProduct.validFrom.format(DATE_TIME_FORMAT) : undefined,
      validTo: societyLoanProduct.validTo ? societyLoanProduct.validTo.format(DATE_TIME_FORMAT) : undefined,
      createdOn: societyLoanProduct.createdOn ? societyLoanProduct.createdOn.format(DATE_TIME_FORMAT) : undefined,
      lastModified: societyLoanProduct.lastModified ? societyLoanProduct.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
