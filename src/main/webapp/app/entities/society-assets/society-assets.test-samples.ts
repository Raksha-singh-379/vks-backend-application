import dayjs from 'dayjs/esm';

import { SocietyAssetsType } from 'app/entities/enumerations/society-assets-type.model';

import { ISocietyAssets, NewSocietyAssets } from './society-assets.model';

export const sampleWithRequiredData: ISocietyAssets = {
  id: 23786,
};

export const sampleWithPartialData: ISocietyAssets = {
  id: 68058,
  societyAssetsName: 'Avon',
  type: SocietyAssetsType['FURNITURE'],
  category: 'Product',
  depreciation: 95459,
  lastModified: dayjs('2022-08-18T20:37'),
  lastModifiedBy: 'Architect',
  createdBy: 'Guarani Rubber Steel',
  isDeleted: false,
  freeField3: 'Multi-channelled portals',
};

export const sampleWithFullData: ISocietyAssets = {
  id: 53155,
  societyAssetsName: 'compressing Center intuitive',
  type: SocietyAssetsType['EQUIPMENT'],
  category: 'Solutions interactive',
  depreciation: 68488,
  lastModified: dayjs('2022-08-18T12:50'),
  lastModifiedBy: 'Fresh',
  createdBy: 'Jersey',
  createdOn: dayjs('2022-08-18T06:47'),
  isDeleted: false,
  freeField1: 'lime Fresh',
  freeField2: 'Soft Engineer override',
  freeField3: 'Cambridgeshire',
};

export const sampleWithNewData: NewSocietyAssets = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
