import dayjs from 'dayjs/esm';

import { AssetType } from 'app/entities/enumerations/asset-type.model';

import { IMemberAssets, NewMemberAssets } from './member-assets.model';

export const sampleWithRequiredData: IMemberAssets = {
  id: 35816,
};

export const sampleWithPartialData: IMemberAssets = {
  id: 43635,
  assetAmount: 91101,
  assetArea: 76151,
  numberOfAssets: 12537,
  createdOn: dayjs('2022-08-19T03:51'),
  isDeleted: false,
  freeField3: 'invoice Gibraltar Berkshire',
};

export const sampleWithFullData: IMemberAssets = {
  id: 93863,
  assetAmount: 2773,
  assetType: AssetType['FARM_MACHINERY'],
  assetArea: 83699,
  assetAddress: 'Sausages Handcrafted',
  numberOfAssets: 61267,
  lastModified: dayjs('2022-08-18T20:25'),
  lastModifiedBy: 'Nebraska Southern',
  createdBy: '1080p hacking',
  createdOn: dayjs('2022-08-18T17:24'),
  isDeleted: true,
  freeField1: 'convergence withdrawal',
  freeField2: 'Creek partnerships',
  freeField3: 'encompassing Web Portugal',
};

export const sampleWithNewData: NewMemberAssets = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
