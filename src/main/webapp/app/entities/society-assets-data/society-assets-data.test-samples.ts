import dayjs from 'dayjs/esm';

import { ISocietyAssetsData, NewSocietyAssetsData } from './society-assets-data.model';

export const sampleWithRequiredData: ISocietyAssetsData = {
  id: 38707,
};

export const sampleWithPartialData: ISocietyAssetsData = {
  id: 19897,
  balanceQuantity: 96153,
  balanceValue: 11792,
  billNo: 'XML deliverables help-desk',
  mode: 'Forward Health SQL',
  transactionType: 'infrastructures Cambridgeshire',
  transactionDate: dayjs('2022-08-18T17:27'),
  lastModifiedBy: 'Massachusetts Progressive',
  createdBy: 'generation Rustic front-end',
  freeField2: 'monitor',
  freeField3: 'Mouse',
};

export const sampleWithFullData: ISocietyAssetsData = {
  id: 99817,
  amount: 74446,
  balanceQuantity: 83873,
  balanceValue: 46132,
  billNo: 'Integrated',
  mode: 'Quality',
  cost: 1188,
  transactionType: 'Mobility',
  transactionDate: dayjs('2022-08-18T10:29'),
  lastModified: dayjs('2022-08-18T09:25'),
  lastModifiedBy: 'Web',
  createdBy: 'primary',
  createdOn: dayjs('2022-08-18T18:56'),
  isDeleted: true,
  freeField1: 'Practical Multi-tiered tangible',
  freeField2: 'SQL indexing bus',
  freeField3: 'card programming compress',
};

export const sampleWithNewData: NewSocietyAssetsData = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
