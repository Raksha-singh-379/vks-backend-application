import dayjs from 'dayjs/esm';

import { IMemberLandAssets, NewMemberLandAssets } from './member-land-assets.model';

export const sampleWithRequiredData: IMemberLandAssets = {
  id: 94974,
};

export const sampleWithPartialData: IMemberLandAssets = {
  id: 69560,
  landType: 'Engineer',
  jindagiAmount: 59579,
  isDeleted: true,
  lastModified: dayjs('2022-08-18T11:19'),
  lastModifiedBy: 'magenta',
  createdBy: 'Bacon neural',
  createdOn: dayjs('2022-08-19T00:32'),
  freeField1: 'Monitored Mexico',
  freeField2: 'Accounts front-end',
  freeField3: 'parsing parse Rubber',
};

export const sampleWithFullData: IMemberLandAssets = {
  id: 29939,
  landType: 'Cotton',
  landGatNo: 'Tasty Engineer',
  landAreaInHector: 21785,
  jindagiPatrakNo: 'wireless web-readiness strategy',
  jindagiAmount: 97920,
  assetLandAddress: 'e-services Plastic User',
  valueOfLand: 39157,
  assigneeOfLand: true,
  isDeleted: false,
  mLLoanNo: 22133,
  lastModified: dayjs('2022-08-18T23:46'),
  lastModifiedBy: 'global Bulgarian',
  createdBy: 'Soft',
  createdOn: dayjs('2022-08-18T20:31'),
  freeField1: 'open-source Avon',
  freeField2: 'Dollar parse Salvador',
  freeField3: 'Wooden',
};

export const sampleWithNewData: NewMemberLandAssets = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
