import dayjs from 'dayjs/esm';

import { Season } from 'app/entities/enumerations/season.model';

import { ISocietyCropRegistration, NewSocietyCropRegistration } from './society-crop-registration.model';

export const sampleWithRequiredData: ISocietyCropRegistration = {
  id: 62709,
};

export const sampleWithPartialData: ISocietyCropRegistration = {
  id: 69775,
  monthDuration: 5322,
  season: Season['RABBI'],
  cropLimit: 77457,
  lastModified: dayjs('2022-08-18T16:52'),
  createdOn: dayjs('2022-08-18T16:06'),
  isDeleted: false,
};

export const sampleWithFullData: ISocietyCropRegistration = {
  id: 34975,
  cropName: 'online deposit Fresh',
  monthDuration: 97247,
  season: Season['RABBI'],
  cropLimit: 84564,
  year: 42775,
  lastModified: dayjs('2022-08-18T18:08'),
  lastModifiedBy: 'Cambridgeshire',
  createdBy: 'Rustic',
  createdOn: dayjs('2022-08-18T09:53'),
  isDeleted: false,
  freeField1: 'as communities Paradigm',
  freeField2: 'Streets applications Beauty',
  freeField3: 'Mount',
};

export const sampleWithNewData: NewSocietyCropRegistration = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
