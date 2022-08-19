import dayjs from 'dayjs/esm';

import { IVoucherDetails, NewVoucherDetails } from './voucher-details.model';

export const sampleWithRequiredData: IVoucherDetails = {
  id: 66695,
};

export const sampleWithPartialData: IVoucherDetails = {
  id: 40324,
  accHeadCode: 'Account Savings',
  transferAmt: 1326,
  freeField2: 'Bedfordshire Clothing',
  freeField3: 'architect superstructure',
};

export const sampleWithFullData: IVoucherDetails = {
  id: 67374,
  accHeadCode: 'Money Soap Practical',
  creditAccount: 'matrix Electronics',
  debitAccount: 'maroon',
  transferAmt: 45718,
  isDeleted: true,
  lastModified: dayjs('2022-08-18T18:42'),
  lastModifiedBy: 'innovative Hawaii',
  freeField1: 'Computer expedite payment',
  freeField2: 'Refined Plastic action-items',
  freeField3: 'overriding alarm architectures',
};

export const sampleWithNewData: NewVoucherDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
