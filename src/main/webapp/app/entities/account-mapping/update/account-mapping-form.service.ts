import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAccountMapping, NewAccountMapping } from '../account-mapping.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAccountMapping for edit and NewAccountMappingFormGroupInput for create.
 */
type AccountMappingFormGroupInput = IAccountMapping | PartialWithRequiredKeyOf<NewAccountMapping>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAccountMapping | NewAccountMapping> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type AccountMappingFormRawValue = FormValueOf<IAccountMapping>;

type NewAccountMappingFormRawValue = FormValueOf<NewAccountMapping>;

type AccountMappingFormDefaults = Pick<NewAccountMapping, 'id' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type AccountMappingFormGroupContent = {
  id: FormControl<AccountMappingFormRawValue['id'] | NewAccountMapping['id']>;
  type: FormControl<AccountMappingFormRawValue['type']>;
  mappingName: FormControl<AccountMappingFormRawValue['mappingName']>;
  ledgerAccHeadCode: FormControl<AccountMappingFormRawValue['ledgerAccHeadCode']>;
  ledgerAccountId: FormControl<AccountMappingFormRawValue['ledgerAccountId']>;
  lastModified: FormControl<AccountMappingFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<AccountMappingFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<AccountMappingFormRawValue['createdBy']>;
  createdOn: FormControl<AccountMappingFormRawValue['createdOn']>;
  isDeleted: FormControl<AccountMappingFormRawValue['isDeleted']>;
  freeField1: FormControl<AccountMappingFormRawValue['freeField1']>;
  freeField2: FormControl<AccountMappingFormRawValue['freeField2']>;
  freeField3: FormControl<AccountMappingFormRawValue['freeField3']>;
  accountMapping: FormControl<AccountMappingFormRawValue['accountMapping']>;
};

export type AccountMappingFormGroup = FormGroup<AccountMappingFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AccountMappingFormService {
  createAccountMappingFormGroup(accountMapping: AccountMappingFormGroupInput = { id: null }): AccountMappingFormGroup {
    const accountMappingRawValue = this.convertAccountMappingToAccountMappingRawValue({
      ...this.getFormDefaults(),
      ...accountMapping,
    });
    return new FormGroup<AccountMappingFormGroupContent>({
      id: new FormControl(
        { value: accountMappingRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      type: new FormControl(accountMappingRawValue.type),
      mappingName: new FormControl(accountMappingRawValue.mappingName),
      ledgerAccHeadCode: new FormControl(accountMappingRawValue.ledgerAccHeadCode),
      ledgerAccountId: new FormControl(accountMappingRawValue.ledgerAccountId),
      lastModified: new FormControl(accountMappingRawValue.lastModified),
      lastModifiedBy: new FormControl(accountMappingRawValue.lastModifiedBy),
      createdBy: new FormControl(accountMappingRawValue.createdBy),
      createdOn: new FormControl(accountMappingRawValue.createdOn),
      isDeleted: new FormControl(accountMappingRawValue.isDeleted),
      freeField1: new FormControl(accountMappingRawValue.freeField1),
      freeField2: new FormControl(accountMappingRawValue.freeField2),
      freeField3: new FormControl(accountMappingRawValue.freeField3),
      accountMapping: new FormControl(accountMappingRawValue.accountMapping),
    });
  }

  getAccountMapping(form: AccountMappingFormGroup): IAccountMapping | NewAccountMapping {
    return this.convertAccountMappingRawValueToAccountMapping(
      form.getRawValue() as AccountMappingFormRawValue | NewAccountMappingFormRawValue
    );
  }

  resetForm(form: AccountMappingFormGroup, accountMapping: AccountMappingFormGroupInput): void {
    const accountMappingRawValue = this.convertAccountMappingToAccountMappingRawValue({ ...this.getFormDefaults(), ...accountMapping });
    form.reset(
      {
        ...accountMappingRawValue,
        id: { value: accountMappingRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AccountMappingFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertAccountMappingRawValueToAccountMapping(
    rawAccountMapping: AccountMappingFormRawValue | NewAccountMappingFormRawValue
  ): IAccountMapping | NewAccountMapping {
    return {
      ...rawAccountMapping,
      lastModified: dayjs(rawAccountMapping.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawAccountMapping.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertAccountMappingToAccountMappingRawValue(
    accountMapping: IAccountMapping | (Partial<NewAccountMapping> & AccountMappingFormDefaults)
  ): AccountMappingFormRawValue | PartialWithRequiredKeyOf<NewAccountMappingFormRawValue> {
    return {
      ...accountMapping,
      lastModified: accountMapping.lastModified ? accountMapping.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: accountMapping.createdOn ? accountMapping.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
