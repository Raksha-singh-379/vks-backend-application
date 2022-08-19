import dayjs from 'dayjs/esm';

import { IExpenditureType, NewExpenditureType } from './expenditure-type.model';

export const sampleWithRequiredData: IExpenditureType = {
  id: 21248,
};

export const sampleWithPartialData: IExpenditureType = {
  id: 70163,
  expenditureDesc: 'Turnpike Games teal',
  createdBy: 'attitude-oriented Israel',
  createdOn: dayjs('2022-08-18T07:23'),
  isDeleted: false,
  freeField2: 'online Gorgeous Baby',
  freeField3: 'hacking demand-driven',
};

export const sampleWithFullData: IExpenditureType = {
  id: 48700,
  expenditureDesc: 'Niue one-to-one',
  expenditureType: 'Wooden Buckinghamshire',
  expenditureCategory: true,
  lastModified: dayjs('2022-08-18T06:10'),
  lastModifiedBy: 'interactive turn-key',
  createdBy: 'Research deploy Michigan',
  createdOn: dayjs('2022-08-18T23:50'),
  isDeleted: false,
  freeField1: 'hack Kiribati Metal',
  freeField2: 'Locks SAS Frozen',
  freeField3: 'compelling',
};

export const sampleWithNewData: NewExpenditureType = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
