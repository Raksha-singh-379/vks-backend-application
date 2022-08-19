import dayjs from 'dayjs/esm';

import { VoucherCode } from 'app/entities/enumerations/voucher-code.model';

import { IVouchersHistory, NewVouchersHistory } from './vouchers-history.model';

export const sampleWithRequiredData: IVouchersHistory = {
  id: 84975,
};

export const sampleWithPartialData: IVouchersHistory = {
  id: 57071,
  createdOn: dayjs('2022-08-18T13:10'),
  code: VoucherCode['SALES'],
  freeField2: 'neutral Central',
  freeField3: 'Bedfordshire bandwidth',
};

export const sampleWithFullData: IVouchersHistory = {
  id: 77850,
  createdOn: dayjs('2022-08-18T13:35'),
  createdBy: 'THX Terrace',
  voucherDate: dayjs('2022-08-19T04:30'),
  code: VoucherCode['INVESTMENT'],
  freeField1: 'hack',
  freeField2: 'indexing Utah',
  freeField3: 'platforms Tuna Frozen',
};

export const sampleWithNewData: NewVouchersHistory = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
