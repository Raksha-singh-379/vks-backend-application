import dayjs from 'dayjs/esm';

import { ISocietyConfig, NewSocietyConfig } from './society-config.model';

export const sampleWithRequiredData: ISocietyConfig = {
  id: 54826,
};

export const sampleWithPartialData: ISocietyConfig = {
  id: 18369,
  status: 'Island neural',
  year: 'transmitter',
  lastModified: dayjs('2022-08-18T12:54'),
  createdBy: 'programming',
  isDeleted: true,
};

export const sampleWithFullData: ISocietyConfig = {
  id: 93211,
  configName: 'matrix Grenada generating',
  configType: 'Program blockchains hard',
  status: 'Small asynchronous Vermont',
  value: 99910,
  year: 'Investment protocol',
  lastModified: dayjs('2022-08-18T17:43'),
  lastModifiedBy: 'Nevada',
  createdBy: 'Hat migration leverage',
  createdOn: dayjs('2022-08-18T17:08'),
  isDeleted: true,
  freeField1: 'programming bypassing District',
  freeField2: 'Keyboard',
  freeField3: 'ivory Consultant Wooden',
};

export const sampleWithNewData: NewSocietyConfig = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
