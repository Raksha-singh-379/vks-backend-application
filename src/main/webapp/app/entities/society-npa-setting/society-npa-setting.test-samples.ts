import dayjs from 'dayjs/esm';

import { NpaClassification } from 'app/entities/enumerations/npa-classification.model';

import { ISocietyNpaSetting, NewSocietyNpaSetting } from './society-npa-setting.model';

export const sampleWithRequiredData: ISocietyNpaSetting = {
  id: 2455,
};

export const sampleWithPartialData: ISocietyNpaSetting = {
  id: 61371,
  npaClassification: NpaClassification['DOUBTFUL_1'],
  durationEnd: 71170,
  provision: 61611,
  year: 46429,
  interestRate: 38422,
  createdOn: dayjs('2022-08-18T23:07'),
  isDeleted: false,
  freeField2: 'Team-oriented',
  freeField3: 'synthesize Rial red',
};

export const sampleWithFullData: ISocietyNpaSetting = {
  id: 60521,
  npaClassification: NpaClassification['STANDARD'],
  durationStart: 38762,
  durationEnd: 91148,
  provision: 71523,
  year: 27317,
  interestRate: 88117,
  lastModified: dayjs('2022-08-19T00:41'),
  lastModifiedBy: 'Jersey neural',
  createdBy: 'Cambridgeshire',
  createdOn: dayjs('2022-08-18T15:28'),
  isDeleted: true,
  freeField1: 'PNG partnerships',
  freeField2: 'transparent Dynamic Utah',
  freeField3: 'Cambridgeshire Research Strategist',
};

export const sampleWithNewData: NewSocietyNpaSetting = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
