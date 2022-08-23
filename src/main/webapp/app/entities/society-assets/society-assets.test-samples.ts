import dayjs from 'dayjs/esm';

import { SocietyAssetsType } from 'app/entities/enumerations/society-assets-type.model';

import { ISocietyAssets, NewSocietyAssets } from './society-assets.model';

export const sampleWithRequiredData: ISocietyAssets = {
  id: 23786,
};

export const sampleWithPartialData: ISocietyAssets = {
  id: 22097,
  societyAssetsName: 'Jewelery Triple-buffered',
  type: SocietyAssetsType['FURNITURE'],
  category: 'global Junctions Guarani',
  depreciation: 13626,
  lastModified: dayjs('2022-08-18T14:46'),
  lastModifiedBy: 'Steel e-markets Rest',
  createdBy: 'Designer compressing',
  isDeleted: false,
  freeField3: 'Fantastic',
  freeField4: 'Solutions interactive',
};

export const sampleWithFullData: ISocietyAssets = {
  id: 68488,
  societyAssetsName: 'Wooden initiatives Land',
  type: SocietyAssetsType['EQUIPMENT'],
  category: 'lime Fresh',
  depreciation: 67834,
  lastModified: dayjs('2022-08-19T02:17'),
  lastModifiedBy: 'silver Accounts Books',
  createdBy: 'Granite Technician navigating',
  createdOn: dayjs('2022-08-18T20:05'),
  isDeleted: true,
  freeField1: 'Lanka',
  freeField2: 'infrastructures Sports Persevering',
  freeField3: 'microchip pricing',
  freeField4: 'Planner Licensed',
};

export const sampleWithNewData: NewSocietyAssets = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
