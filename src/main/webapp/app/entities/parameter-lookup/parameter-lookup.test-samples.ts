import dayjs from 'dayjs/esm';

import { ParameterLookupType } from 'app/entities/enumerations/parameter-lookup-type.model';

import { IParameterLookup, NewParameterLookup } from './parameter-lookup.model';

export const sampleWithRequiredData: IParameterLookup = {
  id: 5221,
};

export const sampleWithPartialData: IParameterLookup = {
  id: 37501,
  description: 'metrics Customizable',
  value: 'viral SCSI',
  lastModified: dayjs('2022-08-18T11:51'),
  createdOn: dayjs('2022-08-18T23:38'),
};

export const sampleWithFullData: IParameterLookup = {
  id: 52864,
  type: ParameterLookupType['FARMER'],
  name: 'Triple-buffered',
  description: 'Cotton Movies',
  value: 'Account recontextualize hack',
  lastModified: dayjs('2022-08-19T02:14'),
  lastModifiedBy: 'Berkshire',
  createdBy: 'algorithm Small',
  createdOn: dayjs('2022-08-18T16:58'),
  isDeleted: true,
};

export const sampleWithNewData: NewParameterLookup = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
