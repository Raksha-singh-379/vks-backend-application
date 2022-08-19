import dayjs from 'dayjs/esm';

import { ISociety, NewSociety } from './society.model';

export const sampleWithRequiredData: ISociety = {
  id: 88772,
  societyName: 'cutting-edge Diverse',
};

export const sampleWithPartialData: ISociety = {
  id: 78633,
  societyName: 'Maryland ubiquitous',
  address: 'cyan',
  village: 'Table',
  registrationNumber: 60996,
  panNumber: 34848,
  tanNumber: 83137,
  emailAddress: 'Operative generate product',
  createdBy: 'lime',
  lastModified: dayjs('2022-08-18T18:54'),
  lastModifiedBy: 'plum',
};

export const sampleWithFullData: ISociety = {
  id: 95695,
  societyName: 'engineer platforms',
  address: 'Polarised Fresh project',
  village: 'recontextualize violet e-business',
  tahsil: 'Movies Grocery',
  state: 'Rubber Mission',
  district: 'extranet Analyst',
  registrationNumber: 44160,
  gstinNumber: 82878,
  panNumber: 60240,
  tanNumber: 98344,
  phoneNumber: 5525,
  emailAddress: 'tertiary',
  pinCode: 53209,
  createdOn: dayjs('2022-08-18T17:26'),
  createdBy: 'Down-sized envisioneer copy',
  description: 'markets',
  isActivate: false,
  lastModified: dayjs('2022-08-19T00:56'),
  lastModifiedBy: 'Avon neutral',
  freeField1: 'Music Riyal',
  freeField2: 'neural e-services monitor',
};

export const sampleWithNewData: NewSociety = {
  societyName: 'local',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
