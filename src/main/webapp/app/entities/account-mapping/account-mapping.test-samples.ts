import dayjs from 'dayjs/esm';

import { MappingType } from 'app/entities/enumerations/mapping-type.model';

import { IAccountMapping, NewAccountMapping } from './account-mapping.model';

export const sampleWithRequiredData: IAccountMapping = {
  id: 66401,
};

export const sampleWithPartialData: IAccountMapping = {
  id: 92038,
  freeField1: 'Mali Games',
};

export const sampleWithFullData: IAccountMapping = {
  id: 82783,
  type: MappingType['BORROWING'],
  mappingName: 'circuit',
  ledgerAccHeadCode: 'cohesive Ergonomic B2C',
  ledgerAccountId: 9543,
  lastModified: dayjs('2022-08-19T03:03'),
  lastModifiedBy: 'program withdrawal',
  createdBy: 'Analyst',
  createdOn: dayjs('2022-08-18T13:41'),
  isDeleted: false,
  freeField1: 'grow Virtual',
  freeField2: 'Bacon invoice',
  freeField3: 'Lead array',
};

export const sampleWithNewData: NewAccountMapping = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
