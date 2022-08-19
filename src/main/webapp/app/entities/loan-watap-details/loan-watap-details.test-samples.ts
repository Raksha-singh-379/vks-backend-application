import dayjs from 'dayjs/esm';

import { LoanStatus } from 'app/entities/enumerations/loan-status.model';

import { ILoanWatapDetails, NewLoanWatapDetails } from './loan-watap-details.model';

export const sampleWithRequiredData: ILoanWatapDetails = {
  id: 21111,
};

export const sampleWithPartialData: ILoanWatapDetails = {
  id: 18578,
  slotNumber: 4017,
  status: LoanStatus['ACTIVE'],
  lastModified: dayjs('2022-08-18T23:32'),
  freeField2: 'schemas',
};

export const sampleWithFullData: ILoanWatapDetails = {
  id: 3155,
  loanWatapDate: dayjs('2022-08-18T13:51'),
  cropLandInHector: 13674,
  slotNumber: 8759,
  loanAmount: 12901,
  season: 'Salad invoice Sausages',
  status: LoanStatus['CHART_GENRATED'],
  year: 'programming',
  isDeleted: false,
  lastModified: dayjs('2022-08-18T11:39'),
  lastModifiedBy: 'target',
  freeField1: 'exuding Research Home',
  freeField2: 'panel',
  freeField3: 'Organized',
};

export const sampleWithNewData: NewLoanWatapDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
