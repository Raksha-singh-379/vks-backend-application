import dayjs from 'dayjs/esm';

import { VoucherCode } from 'app/entities/enumerations/voucher-code.model';
import { PaymentMode } from 'app/entities/enumerations/payment-mode.model';

import { IVouchers, NewVouchers } from './vouchers.model';

export const sampleWithRequiredData: IVouchers = {
  id: 36341,
};

export const sampleWithPartialData: IVouchers = {
  id: 39849,
  code: VoucherCode['LOAN'],
  mode: PaymentMode['TRANSFER'],
  lastModified: dayjs('2022-08-19T03:33'),
  lastModifiedBy: 'technologies interactive Utah',
  freeField2: 'wireless vortals customized',
  freeField3: 'impactful',
};

export const sampleWithFullData: IVouchers = {
  id: 76620,
  voucherDate: dayjs('2022-08-18T22:58'),
  voucherNo: 'magnetic expedite',
  preparedBy: 'Customer',
  code: VoucherCode['PURCHASE'],
  narration: 'Namibia Supervisor Chicken',
  authorisedBy: 'Corporate Burkina',
  mode: PaymentMode['CASH'],
  isDeleted: false,
  lastModified: dayjs('2022-08-19T04:12'),
  lastModifiedBy: 'Fresh transmit',
  freeField1: 'TCP architectures',
  freeField2: 'Garden',
  freeField3: 'bypassing Small navigate',
};

export const sampleWithNewData: NewVouchers = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
