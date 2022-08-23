import dayjs from 'dayjs/esm';

import { ParameterLookupType } from 'app/entities/enumerations/parameter-lookup-type.model';

import { IParameterLookup, NewParameterLookup } from './parameter-lookup.model';

export const sampleWithRequiredData: IParameterLookup = {
  id: 5221,
};

export const sampleWithPartialData: IParameterLookup = {
  id: 41978,
  description: 'compressing Soap efficient',
  value: 'Re-contextualized Heard Triple-buffered',
  lastModified: dayjs('2022-08-18T17:42'),
  createdOn: dayjs('2022-08-19T03:52'),
};

export const sampleWithFullData: IParameterLookup = {
  id: 57513,
  type: ParameterLookupType['RESOLUTION'],
  name: 'Checking architecture Forward',
  description: 'portal Rustic Berkshire',
  value: 'algorithm Small',
  lastModified: dayjs('2022-08-18T16:58'),
  lastModifiedBy: 'Bedfordshire Toys',
  createdBy: 'Bedfordshire Shilling front-end',
  createdOn: dayjs('2022-08-18T13:07'),
  isDeleted: true,
  freeField1: 'Coordinator',
  freeField2: 'Dynamic',
};

export const sampleWithNewData: NewParameterLookup = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
