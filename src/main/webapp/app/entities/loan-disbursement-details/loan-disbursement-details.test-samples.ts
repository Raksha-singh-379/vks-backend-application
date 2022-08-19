import dayjs from 'dayjs/esm';

import { PaymentMode } from 'app/entities/enumerations/payment-mode.model';
import { LoanType } from 'app/entities/enumerations/loan-type.model';

import { ILoanDisbursementDetails, NewLoanDisbursementDetails } from './loan-disbursement-details.model';

export const sampleWithRequiredData: ILoanDisbursementDetails = {
  id: 82850,
};

export const sampleWithPartialData: ILoanDisbursementDetails = {
  id: 40305,
  disbursementDate: dayjs('2022-08-18T17:20'),
  disbursementStatus: 'parse Credit New',
  paymentMode: PaymentMode['CASH'],
  type: LoanType['LONG_TERM'],
  freeField1: 'Gloves user-facing',
  freeField3: 'Rubber SMTP',
};

export const sampleWithFullData: ILoanDisbursementDetails = {
  id: 35024,
  disbursementDate: dayjs('2022-08-18T05:39'),
  disbursementAmount: 30608,
  disbursementNumber: 17292,
  disbursementStatus: 'Money networks infrastructures',
  paymentMode: PaymentMode['ONLINE'],
  type: LoanType['LONG_TERM'],
  lastModified: dayjs('2022-08-18T15:58'),
  lastModifiedBy: 'maroon Utah',
  freeField1: 'Texas Dollar',
  freeField2: 'Cotton synergy',
  freeField3: 'blue compress Movies',
};

export const sampleWithNewData: NewLoanDisbursementDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
