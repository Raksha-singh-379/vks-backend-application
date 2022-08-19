import dayjs from 'dayjs/esm';

import { ITaluka, NewTaluka } from './taluka.model';

export const sampleWithRequiredData: ITaluka = {
  id: 3499,
  name: 'Handmade quantify web-readiness',
  lastModified: dayjs('2022-08-18T16:48'),
  lastModifiedBy: 'Consultant Integration navigating',
};

export const sampleWithPartialData: ITaluka = {
  id: 41351,
  name: 'Plastic Grenada payment',
  lastModified: dayjs('2022-08-19T03:56'),
  lastModifiedBy: 'deposit Sports',
};

export const sampleWithFullData: ITaluka = {
  id: 28509,
  name: 'HDD',
  deleted: false,
  lgdCode: 46966,
  lastModified: dayjs('2022-08-18T18:48'),
  lastModifiedBy: 'Planner primary Keyboard',
};

export const sampleWithNewData: NewTaluka = {
  name: 'haptic',
  lastModified: dayjs('2022-08-19T04:10'),
  lastModifiedBy: 'Borders',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
