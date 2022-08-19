import dayjs from 'dayjs/esm';

import { IState, NewState } from './state.model';

export const sampleWithRequiredData: IState = {
  id: 97627,
  name: 'Games microchip',
  lastModified: dayjs('2022-08-18T13:50'),
  lastModifiedBy: 'customized',
};

export const sampleWithPartialData: IState = {
  id: 14638,
  name: 'synergies',
  deleted: true,
  lgdCode: 45507,
  lastModified: dayjs('2022-08-18T08:35'),
  lastModifiedBy: 'generate',
};

export const sampleWithFullData: IState = {
  id: 78380,
  name: 'Buckinghamshire green',
  deleted: false,
  lgdCode: 94671,
  lastModified: dayjs('2022-08-18T18:34'),
  lastModifiedBy: 'XSS',
};

export const sampleWithNewData: NewState = {
  name: 'olive Cambridgeshire',
  lastModified: dayjs('2022-08-19T00:29'),
  lastModifiedBy: 'Light Small',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
