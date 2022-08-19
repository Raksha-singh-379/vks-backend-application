import dayjs from 'dayjs/esm';

import { IVillage, NewVillage } from './village.model';

export const sampleWithRequiredData: IVillage = {
  id: 90840,
  name: 'Executive',
  lastModified: dayjs('2022-08-18T14:10'),
  lastModifiedBy: 'Table Pound Bedfordshire',
};

export const sampleWithPartialData: IVillage = {
  id: 95843,
  name: 'transmit',
  lgdCode: 90337,
  lastModified: dayjs('2022-08-18T13:09'),
  lastModifiedBy: 'Frozen Ball incentivize',
};

export const sampleWithFullData: IVillage = {
  id: 93861,
  name: 'Dakota green',
  deleted: true,
  lgdCode: 16496,
  lastModified: dayjs('2022-08-18T19:05'),
  lastModifiedBy: 'plum',
};

export const sampleWithNewData: NewVillage = {
  name: 'Steel Tugrik Virtual',
  lastModified: dayjs('2022-08-18T23:33'),
  lastModifiedBy: 'seize Berkshire Crossing',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
