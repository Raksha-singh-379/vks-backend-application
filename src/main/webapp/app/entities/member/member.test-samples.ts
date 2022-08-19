import dayjs from 'dayjs/esm';

import { Gender } from 'app/entities/enumerations/gender.model';
import { Status } from 'app/entities/enumerations/status.model';
import { LoanStatus } from 'app/entities/enumerations/loan-status.model';

import { IMember, NewMember } from './member.model';

export const sampleWithRequiredData: IMember = {
  id: 27280,
};

export const sampleWithPartialData: IMember = {
  id: 54435,
  middleName: 'Utah system-worthy array',
  memberUniqueId: 'hacking dedicated Oregon',
  dob: dayjs('2022-08-18'),
  religion: 'cross-platform',
  cast: 'array Bedfordshire wireless',
  panCard: 'Car programming XML',
  occupation: 'circuit Industrial Senior',
  status: Status['LOAN_DEMAND'],
  kmpStatus: true,
  boardResolutionDate: dayjs('2022-08-18'),
  loanStatus: LoanStatus['CLOSED'],
  memberType: 'vortals Buckinghamshire Books',
  isactive: false,
  lastModifiedBy: 'Intelligent bypassing teal',
  createdBy: 'Refined',
  createdOn: dayjs('2022-08-18T20:21'),
  freeField1: 'Barthelemy Central digital',
};

export const sampleWithFullData: IMember = {
  id: 44102,
  firstName: 'Tavares',
  middleName: 'copy',
  lastName: 'Lockman',
  memberUniqueId: 'Massachusetts',
  fatherName: 'content Soap',
  motherName: 'Automotive',
  gender: Gender['FEMALE'],
  dob: dayjs('2022-08-18'),
  email: 'Brandt_Rodriguez@gmail.com',
  mobileNo: 'Micronesia parsing Jewelery',
  religion: 'Persistent Lead Investor',
  category: 'generate Analyst Markets',
  cast: 'Facilitator Lempira',
  aadharCard: 'ROI',
  panCard: 'Credit',
  rationCard: 'generate',
  familyMemberCount: 69922,
  occupation: 'e-tailers',
  applicationDate: dayjs('2022-08-18T11:10'),
  status: Status['KMP_GENREATED'],
  kmpStatus: false,
  boardResolutionNo: 'Assistant Stream infrastructures',
  boardResolutionDate: dayjs('2022-08-18'),
  loanStatus: LoanStatus['DISBURSED'],
  memberType: 'Walk Operative',
  isactive: true,
  lastModified: dayjs('2022-08-18T14:38'),
  lastModifiedBy: 'Rubber Up-sized',
  createdBy: 'Swiss District',
  createdOn: dayjs('2022-08-18T14:23'),
  freeField1: 'Awesome',
  freeField2: 'Chips',
  freeField3: 'withdrawal Dynamic',
};

export const sampleWithNewData: NewMember = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
