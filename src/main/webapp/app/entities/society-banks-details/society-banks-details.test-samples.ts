import dayjs from 'dayjs/esm';

import { ISocietyBanksDetails, NewSocietyBanksDetails } from './society-banks-details.model';

export const sampleWithRequiredData: ISocietyBanksDetails = {
  id: 58959,
  accountNumber: 'services',
};

export const sampleWithPartialData: ISocietyBanksDetails = {
  id: 9173,
  bankName: 'Gloves Account',
  ifsccode: 'aggregate Avon',
  accountNumber: 'calculating synthesizing',
  isActive: true,
  accHeadCode: 'withdrawal groupware',
  lastModified: dayjs('2022-08-18T15:52'),
  lastModifiedBy: 'target Synergized Squares',
  createdBy: 'transform Chad',
  createdOn: dayjs('2022-08-18T07:43'),
  freeField1: 'adapter',
  freeField3: 'Electronics connect Roads',
};

export const sampleWithFullData: ISocietyBanksDetails = {
  id: 5072,
  bankName: 'Pizza',
  ifsccode: 'Directives USB XSS',
  branchName: 'Money navigating white',
  accountNumber: 'Palau Buckinghamshire',
  isActive: false,
  accountType: 'Account tertiary Mississippi',
  accHeadCode: 'digital connect unleash',
  parentAccHeadCode: 'firewall',
  accountName: 'Checking Account',
  lastModified: dayjs('2022-08-18T15:31'),
  lastModifiedBy: 'eyeballs Hat back-end',
  createdBy: 'Principal Iranian Intelligent',
  createdOn: dayjs('2022-08-18T07:42'),
  freeField1: 'Central',
  freeField2: 'encoding',
  freeField3: 'Consultant',
};

export const sampleWithNewData: NewSocietyBanksDetails = {
  accountNumber: 'front-end',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
