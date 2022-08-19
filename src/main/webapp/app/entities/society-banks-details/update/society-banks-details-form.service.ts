import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISocietyBanksDetails, NewSocietyBanksDetails } from '../society-banks-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISocietyBanksDetails for edit and NewSocietyBanksDetailsFormGroupInput for create.
 */
type SocietyBanksDetailsFormGroupInput = ISocietyBanksDetails | PartialWithRequiredKeyOf<NewSocietyBanksDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISocietyBanksDetails | NewSocietyBanksDetails> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type SocietyBanksDetailsFormRawValue = FormValueOf<ISocietyBanksDetails>;

type NewSocietyBanksDetailsFormRawValue = FormValueOf<NewSocietyBanksDetails>;

type SocietyBanksDetailsFormDefaults = Pick<NewSocietyBanksDetails, 'id' | 'isActive' | 'lastModified' | 'createdOn'>;

type SocietyBanksDetailsFormGroupContent = {
  id: FormControl<SocietyBanksDetailsFormRawValue['id'] | NewSocietyBanksDetails['id']>;
  bankName: FormControl<SocietyBanksDetailsFormRawValue['bankName']>;
  ifsccode: FormControl<SocietyBanksDetailsFormRawValue['ifsccode']>;
  branchName: FormControl<SocietyBanksDetailsFormRawValue['branchName']>;
  accountNumber: FormControl<SocietyBanksDetailsFormRawValue['accountNumber']>;
  isActive: FormControl<SocietyBanksDetailsFormRawValue['isActive']>;
  accountType: FormControl<SocietyBanksDetailsFormRawValue['accountType']>;
  accHeadCode: FormControl<SocietyBanksDetailsFormRawValue['accHeadCode']>;
  parentAccHeadCode: FormControl<SocietyBanksDetailsFormRawValue['parentAccHeadCode']>;
  accountName: FormControl<SocietyBanksDetailsFormRawValue['accountName']>;
  lastModified: FormControl<SocietyBanksDetailsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<SocietyBanksDetailsFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<SocietyBanksDetailsFormRawValue['createdBy']>;
  createdOn: FormControl<SocietyBanksDetailsFormRawValue['createdOn']>;
  freeField1: FormControl<SocietyBanksDetailsFormRawValue['freeField1']>;
  freeField2: FormControl<SocietyBanksDetailsFormRawValue['freeField2']>;
  freeField3: FormControl<SocietyBanksDetailsFormRawValue['freeField3']>;
  society: FormControl<SocietyBanksDetailsFormRawValue['society']>;
};

export type SocietyBanksDetailsFormGroup = FormGroup<SocietyBanksDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SocietyBanksDetailsFormService {
  createSocietyBanksDetailsFormGroup(societyBanksDetails: SocietyBanksDetailsFormGroupInput = { id: null }): SocietyBanksDetailsFormGroup {
    const societyBanksDetailsRawValue = this.convertSocietyBanksDetailsToSocietyBanksDetailsRawValue({
      ...this.getFormDefaults(),
      ...societyBanksDetails,
    });
    return new FormGroup<SocietyBanksDetailsFormGroupContent>({
      id: new FormControl(
        { value: societyBanksDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      bankName: new FormControl(societyBanksDetailsRawValue.bankName),
      ifsccode: new FormControl(societyBanksDetailsRawValue.ifsccode),
      branchName: new FormControl(societyBanksDetailsRawValue.branchName),
      accountNumber: new FormControl(societyBanksDetailsRawValue.accountNumber, {
        validators: [Validators.required],
      }),
      isActive: new FormControl(societyBanksDetailsRawValue.isActive),
      accountType: new FormControl(societyBanksDetailsRawValue.accountType),
      accHeadCode: new FormControl(societyBanksDetailsRawValue.accHeadCode),
      parentAccHeadCode: new FormControl(societyBanksDetailsRawValue.parentAccHeadCode),
      accountName: new FormControl(societyBanksDetailsRawValue.accountName),
      lastModified: new FormControl(societyBanksDetailsRawValue.lastModified),
      lastModifiedBy: new FormControl(societyBanksDetailsRawValue.lastModifiedBy),
      createdBy: new FormControl(societyBanksDetailsRawValue.createdBy),
      createdOn: new FormControl(societyBanksDetailsRawValue.createdOn),
      freeField1: new FormControl(societyBanksDetailsRawValue.freeField1),
      freeField2: new FormControl(societyBanksDetailsRawValue.freeField2),
      freeField3: new FormControl(societyBanksDetailsRawValue.freeField3),
      society: new FormControl(societyBanksDetailsRawValue.society),
    });
  }

  getSocietyBanksDetails(form: SocietyBanksDetailsFormGroup): ISocietyBanksDetails | NewSocietyBanksDetails {
    return this.convertSocietyBanksDetailsRawValueToSocietyBanksDetails(
      form.getRawValue() as SocietyBanksDetailsFormRawValue | NewSocietyBanksDetailsFormRawValue
    );
  }

  resetForm(form: SocietyBanksDetailsFormGroup, societyBanksDetails: SocietyBanksDetailsFormGroupInput): void {
    const societyBanksDetailsRawValue = this.convertSocietyBanksDetailsToSocietyBanksDetailsRawValue({
      ...this.getFormDefaults(),
      ...societyBanksDetails,
    });
    form.reset(
      {
        ...societyBanksDetailsRawValue,
        id: { value: societyBanksDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SocietyBanksDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isActive: false,
      lastModified: currentTime,
      createdOn: currentTime,
    };
  }

  private convertSocietyBanksDetailsRawValueToSocietyBanksDetails(
    rawSocietyBanksDetails: SocietyBanksDetailsFormRawValue | NewSocietyBanksDetailsFormRawValue
  ): ISocietyBanksDetails | NewSocietyBanksDetails {
    return {
      ...rawSocietyBanksDetails,
      lastModified: dayjs(rawSocietyBanksDetails.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawSocietyBanksDetails.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertSocietyBanksDetailsToSocietyBanksDetailsRawValue(
    societyBanksDetails: ISocietyBanksDetails | (Partial<NewSocietyBanksDetails> & SocietyBanksDetailsFormDefaults)
  ): SocietyBanksDetailsFormRawValue | PartialWithRequiredKeyOf<NewSocietyBanksDetailsFormRawValue> {
    return {
      ...societyBanksDetails,
      lastModified: societyBanksDetails.lastModified ? societyBanksDetails.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: societyBanksDetails.createdOn ? societyBanksDetails.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
