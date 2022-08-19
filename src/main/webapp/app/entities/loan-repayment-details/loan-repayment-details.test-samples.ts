import dayjs from 'dayjs/esm';

import { PaymentMode } from 'app/entities/enumerations/payment-mode.model';

import { ILoanRepaymentDetails, NewLoanRepaymentDetails } from './loan-repayment-details.model';

export const sampleWithRequiredData: ILoanRepaymentDetails = {
  id: 2660,
};

export const sampleWithPartialData: ILoanRepaymentDetails = {
  id: 97490,
  totalRepaymentAmt: 62240,
  principlePaidAmt: 31218,
  interestPaidAmt: 95571,
  surChargeAmt: 73667,
  foreClosureChargeAmt: 52063,
  lastModified: dayjs('2022-08-18T05:11'),
  lastModifiedBy: 'Baby',
  freeField1: 'installation quantify',
  freeField2: 'Mall Account',
  freeField3: 'Electronics Maryland Massachusetts',
};

export const sampleWithFullData: ILoanRepaymentDetails = {
  id: 55285,
  repaymentDate: dayjs('2022-08-18T19:10'),
  totalRepaymentAmt: 63396,
  installmentNumber: 67065,
  principlePaidAmt: 63214,
  interestPaidAmt: 62959,
  surChargeAmt: 58596,
  overDueAmt: 55483,
  balanceInterestAmt: 34946,
  balancePrincipleAmt: 35944,
  paymentMode: PaymentMode['CHEQUE'],
  foreClosureChargeAmt: 65465,
  lastModified: dayjs('2022-08-18T23:14'),
  lastModifiedBy: 'deploy Credit Account',
  freeField1: 'state hack architectures',
  freeField2: 'plum SMTP',
  freeField3: 'Hat Shoes Malaysian',
};

export const sampleWithNewData: NewLoanRepaymentDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
