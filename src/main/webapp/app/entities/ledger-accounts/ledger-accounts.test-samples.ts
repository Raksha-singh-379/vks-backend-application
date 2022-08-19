import dayjs from 'dayjs/esm';

import { LedgerClassification } from 'app/entities/enumerations/ledger-classification.model';

import { ILedgerAccounts, NewLedgerAccounts } from './ledger-accounts.model';

export const sampleWithRequiredData: ILedgerAccounts = {
  id: 17025,
};

export const sampleWithPartialData: ILedgerAccounts = {
  id: 32953,
  accountName: 'Auto Loan Account',
  appCode: 'Estonia calculate Account',
  ledgerClassification: LedgerClassification['TRADING_ACCOUNT'],
  category: 'SSL Rial multi-tasking',
  level: 89942,
  lastModified: dayjs('2022-08-18T12:52'),
  lastModifiedBy: 'Kids functionalities',
  createdBy: 'Manager Integration optical',
  isDeleted: false,
  freeField1: 'overriding input Table',
};

export const sampleWithFullData: ILedgerAccounts = {
  id: 23514,
  accountNo: 99576,
  accountName: 'Credit Card Account',
  accBalance: 32773,
  accHeadCode: 'mobile',
  ledgerCode: 'Wyoming',
  appCode: 'circuit Georgia',
  ledgerClassification: LedgerClassification['BALANCE_SHEET'],
  category: "Pa'anga JSON",
  level: 14936,
  year: 'Marketing',
  accountType: 'Identity',
  lastModified: dayjs('2022-08-18T05:16'),
  lastModifiedBy: 'tan customer Salad',
  createdBy: 'Bhutanese Orchestrator quantify',
  createdOn: dayjs('2022-08-18T09:18'),
  isDeleted: true,
  freeField1: 'payment',
  freeField2: 'plum',
};

export const sampleWithNewData: NewLedgerAccounts = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
