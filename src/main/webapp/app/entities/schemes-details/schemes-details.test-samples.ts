import dayjs from 'dayjs/esm';

import { ISchemesDetails, NewSchemesDetails } from './schemes-details.model';

export const sampleWithRequiredData: ISchemesDetails = {
  id: 3559,
};

export const sampleWithPartialData: ISchemesDetails = {
  id: 21915,
  fdDurationDays: 52002,
  rdDurationMonths: 4400,
  reinvestInterest: false,
  lastModifiedBy: 'Handcrafted',
  isDeleted: false,
  freeField2: 'Automotive program',
};

export const sampleWithFullData: ISchemesDetails = {
  id: 23885,
  fdDurationDays: 20510,
  interest: 60571,
  lockInPeriod: 74274,
  schemeName: 'Ameliorated',
  rdDurationMonths: 62434,
  reinvestInterest: false,
  lastModified: dayjs('2022-08-18T08:43'),
  lastModifiedBy: 'Representative Home Down-sized',
  createdBy: 'deposit Concrete',
  createdOn: dayjs('2022-08-18T06:51'),
  isDeleted: false,
  freeField1: 'Security Buckinghamshire Refined',
  freeField2: 'Dynamic',
  freeField3: 'bandwidth-monitored withdrawal Baby',
};

export const sampleWithNewData: NewSchemesDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
