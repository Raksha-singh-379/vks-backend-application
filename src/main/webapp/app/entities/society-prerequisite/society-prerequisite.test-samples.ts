import dayjs from 'dayjs/esm';

import { LoanType } from 'app/entities/enumerations/loan-type.model';

import { ISocietyPrerequisite, NewSocietyPrerequisite } from './society-prerequisite.model';

export const sampleWithRequiredData: ISocietyPrerequisite = {
  id: 65188,
};

export const sampleWithPartialData: ISocietyPrerequisite = {
  id: 41856,
  documentDesc: 'utilisation Money Uganda',
  loanType: LoanType['SHORT_TERM'],
  mandatory: 'wireless Awesome SMTP',
  lastModifiedBy: 'Investment Keyboard Kroon',
  createdBy: 'Specialist Metal',
  freeField1: 'Computers',
  freeField2: 'firewall Awesome Russian',
  freeField3: 'e-business',
};

export const sampleWithFullData: ISocietyPrerequisite = {
  id: 26698,
  docType: 'Balboa withdrawal plum',
  documentDesc: 'seize Solomon',
  documentName: 'streamline SQL Usability',
  loanType: LoanType['SHORT_TERM'],
  mandatory: 'Cedi virtual',
  lastModified: dayjs('2022-08-19T01:18'),
  lastModifiedBy: 'Bedfordshire Granite alarm',
  createdBy: 'Cambridgeshire disintermediate quantifying',
  createdOn: dayjs('2022-08-19T03:08'),
  isDeleted: true,
  freeField1: 'program',
  freeField2: 'Creative',
  freeField3: 'Group navigating',
};

export const sampleWithNewData: NewSocietyPrerequisite = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
