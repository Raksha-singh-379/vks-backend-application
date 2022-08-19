import dayjs from 'dayjs/esm';

import { IDistrict, NewDistrict } from './district.model';

export const sampleWithRequiredData: IDistrict = {
  id: 85653,
  name: 'Soap invoice Dong',
  lastModified: dayjs('2022-08-18T17:11'),
  lastModifiedBy: 'Fresh real-time gold',
};

export const sampleWithPartialData: IDistrict = {
  id: 82766,
  name: 'intuitive',
  deleted: false,
  lgdCode: 84089,
  lastModified: dayjs('2022-08-18T23:20'),
  lastModifiedBy: 'Generic transmitting Checking',
};

export const sampleWithFullData: IDistrict = {
  id: 9788,
  name: 'overriding ivory Dollar',
  deleted: true,
  lgdCode: 53685,
  lastModified: dayjs('2022-08-18T18:44'),
  lastModifiedBy: 'Frozen defect Table',
};

export const sampleWithNewData: NewDistrict = {
  name: 'killer efficient Cotton',
  lastModified: dayjs('2022-08-18T17:04'),
  lastModifiedBy: 'matrix',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
