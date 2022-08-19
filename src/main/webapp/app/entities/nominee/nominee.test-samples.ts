import dayjs from 'dayjs/esm';

import { INominee, NewNominee } from './nominee.model';

export const sampleWithRequiredData: INominee = {
  id: 87924,
};

export const sampleWithPartialData: INominee = {
  id: 6948,
  middleName: 'digital',
  motherName: 'withdrawal',
  dob: dayjs('2022-08-18'),
  lastModified: dayjs('2022-08-18T20:26'),
  lastModifiedBy: 'auxiliary Iowa Granite',
  isDeleted: false,
  freeField2: 'deposit',
};

export const sampleWithFullData: INominee = {
  id: 46420,
  firstName: 'Zula',
  middleName: 'Oval Account',
  lastName: 'Beahan',
  fatherName: 'cross-platform',
  motherName: 'Steel',
  guardianName: 'payment',
  gender: 'Cambridgeshire Handcrafted',
  dob: dayjs('2022-08-18'),
  aadharCard: 'ADP payment markets',
  nomineeDeclareDate: dayjs('2022-08-18T22:13'),
  relation: 'turquoise',
  permanentAddr: 'Creek Tactics',
  lastModified: dayjs('2022-08-19T03:49'),
  lastModifiedBy: 'invoice superstructure',
  createdBy: 'networks',
  createdOn: dayjs('2022-08-18T17:01'),
  isDeleted: false,
  freeField1: 'mobile',
  freeField2: 'Investor supply-chains transform',
  freeField3: 'radical heuristic payment',
};

export const sampleWithNewData: NewNominee = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
