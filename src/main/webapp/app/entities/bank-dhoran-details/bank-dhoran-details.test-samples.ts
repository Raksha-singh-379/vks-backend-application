import dayjs from 'dayjs/esm';

import { IBankDhoranDetails, NewBankDhoranDetails } from './bank-dhoran-details.model';

export const sampleWithRequiredData: IBankDhoranDetails = {
  id: 75225,
};

export const sampleWithPartialData: IBankDhoranDetails = {
  id: 34740,
  dhoranName: 'orange Synergized',
  startDate: dayjs('2022-08-18T16:49'),
  year: 'Investment optimizing',
  lastModifiedBy: 'Fresh e-commerce PCI',
  freeField1: 'Ball COM pink',
  freeField2: 'global Soft Course',
};

export const sampleWithFullData: IBankDhoranDetails = {
  id: 79986,
  dhoranName: 'orange systems Focused',
  startDate: dayjs('2022-08-18T14:20'),
  endDate: dayjs('2022-08-18T15:46'),
  year: 'Incredible Kiribati',
  isActivate: true,
  lastModified: dayjs('2022-08-18T13:14'),
  lastModifiedBy: 'Carolina',
  freeField1: 'Plastic haptic',
  freeField2: 'Ergonomic synthesizing',
  freeField3: 'Principal HDD',
};

export const sampleWithNewData: NewBankDhoranDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
