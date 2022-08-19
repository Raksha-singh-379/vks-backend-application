import dayjs from 'dayjs/esm';

import { IMemberBank, NewMemberBank } from './member-bank.model';

export const sampleWithRequiredData: IMemberBank = {
  id: 24673,
};

export const sampleWithPartialData: IMemberBank = {
  id: 30763,
  ifsccode: 'parse transmit Steel',
  lastModified: dayjs('2022-08-18T16:18'),
  createdBy: 'invoice transmitting Tasty',
  createdOn: dayjs('2022-08-18T14:25'),
  isDeleted: false,
  freeField1: 'channels 1080p',
  freeField2: 'Fish cross-platform Bedfordshire',
};

export const sampleWithFullData: IMemberBank = {
  id: 67700,
  bankName: 'tangible',
  branchName: 'Industrial',
  accountNumber: 1045,
  ifsccode: 'reboot Cheese',
  lastModified: dayjs('2022-08-19T01:40'),
  lastModifiedBy: 'Applications',
  createdBy: 'FTP',
  createdOn: dayjs('2022-08-18T05:14'),
  isDeleted: false,
  freeField1: 'Producer',
  freeField2: 'override generating Bedfordshire',
};

export const sampleWithNewData: NewMemberBank = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
