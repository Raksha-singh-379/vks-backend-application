import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IExpenditureType, NewExpenditureType } from '../expenditure-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IExpenditureType for edit and NewExpenditureTypeFormGroupInput for create.
 */
type ExpenditureTypeFormGroupInput = IExpenditureType | PartialWithRequiredKeyOf<NewExpenditureType>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IExpenditureType | NewExpenditureType> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type ExpenditureTypeFormRawValue = FormValueOf<IExpenditureType>;

type NewExpenditureTypeFormRawValue = FormValueOf<NewExpenditureType>;

type ExpenditureTypeFormDefaults = Pick<NewExpenditureType, 'id' | 'expenditureCategory' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type ExpenditureTypeFormGroupContent = {
  id: FormControl<ExpenditureTypeFormRawValue['id'] | NewExpenditureType['id']>;
  expenditureDesc: FormControl<ExpenditureTypeFormRawValue['expenditureDesc']>;
  expenditureType: FormControl<ExpenditureTypeFormRawValue['expenditureType']>;
  expenditureCategory: FormControl<ExpenditureTypeFormRawValue['expenditureCategory']>;
  lastModified: FormControl<ExpenditureTypeFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<ExpenditureTypeFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<ExpenditureTypeFormRawValue['createdBy']>;
  createdOn: FormControl<ExpenditureTypeFormRawValue['createdOn']>;
  isDeleted: FormControl<ExpenditureTypeFormRawValue['isDeleted']>;
  freeField1: FormControl<ExpenditureTypeFormRawValue['freeField1']>;
  freeField2: FormControl<ExpenditureTypeFormRawValue['freeField2']>;
  freeField3: FormControl<ExpenditureTypeFormRawValue['freeField3']>;
  society: FormControl<ExpenditureTypeFormRawValue['society']>;
};

export type ExpenditureTypeFormGroup = FormGroup<ExpenditureTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ExpenditureTypeFormService {
  createExpenditureTypeFormGroup(expenditureType: ExpenditureTypeFormGroupInput = { id: null }): ExpenditureTypeFormGroup {
    const expenditureTypeRawValue = this.convertExpenditureTypeToExpenditureTypeRawValue({
      ...this.getFormDefaults(),
      ...expenditureType,
    });
    return new FormGroup<ExpenditureTypeFormGroupContent>({
      id: new FormControl(
        { value: expenditureTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      expenditureDesc: new FormControl(expenditureTypeRawValue.expenditureDesc),
      expenditureType: new FormControl(expenditureTypeRawValue.expenditureType),
      expenditureCategory: new FormControl(expenditureTypeRawValue.expenditureCategory),
      lastModified: new FormControl(expenditureTypeRawValue.lastModified),
      lastModifiedBy: new FormControl(expenditureTypeRawValue.lastModifiedBy),
      createdBy: new FormControl(expenditureTypeRawValue.createdBy),
      createdOn: new FormControl(expenditureTypeRawValue.createdOn),
      isDeleted: new FormControl(expenditureTypeRawValue.isDeleted),
      freeField1: new FormControl(expenditureTypeRawValue.freeField1),
      freeField2: new FormControl(expenditureTypeRawValue.freeField2),
      freeField3: new FormControl(expenditureTypeRawValue.freeField3),
      society: new FormControl(expenditureTypeRawValue.society),
    });
  }

  getExpenditureType(form: ExpenditureTypeFormGroup): IExpenditureType | NewExpenditureType {
    return this.convertExpenditureTypeRawValueToExpenditureType(
      form.getRawValue() as ExpenditureTypeFormRawValue | NewExpenditureTypeFormRawValue
    );
  }

  resetForm(form: ExpenditureTypeFormGroup, expenditureType: ExpenditureTypeFormGroupInput): void {
    const expenditureTypeRawValue = this.convertExpenditureTypeToExpenditureTypeRawValue({ ...this.getFormDefaults(), ...expenditureType });
    form.reset(
      {
        ...expenditureTypeRawValue,
        id: { value: expenditureTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ExpenditureTypeFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      expenditureCategory: false,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertExpenditureTypeRawValueToExpenditureType(
    rawExpenditureType: ExpenditureTypeFormRawValue | NewExpenditureTypeFormRawValue
  ): IExpenditureType | NewExpenditureType {
    return {
      ...rawExpenditureType,
      lastModified: dayjs(rawExpenditureType.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawExpenditureType.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertExpenditureTypeToExpenditureTypeRawValue(
    expenditureType: IExpenditureType | (Partial<NewExpenditureType> & ExpenditureTypeFormDefaults)
  ): ExpenditureTypeFormRawValue | PartialWithRequiredKeyOf<NewExpenditureTypeFormRawValue> {
    return {
      ...expenditureType,
      lastModified: expenditureType.lastModified ? expenditureType.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: expenditureType.createdOn ? expenditureType.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
