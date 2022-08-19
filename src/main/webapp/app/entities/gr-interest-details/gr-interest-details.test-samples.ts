import dayjs from 'dayjs/esm';

import { IGRInterestDetails, NewGRInterestDetails } from './gr-interest-details.model';

export const sampleWithRequiredData: IGRInterestDetails = {
  id: 64876,
};

export const sampleWithPartialData: IGRInterestDetails = {
  id: 46321,
  criteria: 'invoice Toys Dollar',
  isActivated: true,
  borrowingInterestRate: 98624,
  interestOnLoan: 68749,
  penaltyInterest: 98169,
  loanDuration: 86098,
  numberOFInstallment: 6164,
  borrowerInterest: 57032,
  isDeleted: false,
  freeField1: 'wireless',
};

export const sampleWithFullData: IGRInterestDetails = {
  id: 3297,
  loanGrName: 'Squares Frozen',
  criteria: 'Salad',
  productType: 'capacity Chair GB',
  isActivated: true,
  borrowingInterestRate: 97222,
  interestOnLoan: 96241,
  penaltyInterest: 79975,
  surcharge: 29115,
  loanDuration: 52086,
  numberOFInstallment: 2149,
  extendedInterstRate: 46610,
  centralGovSubsidyInterest: 89343,
  distBankSubsidyInterest: 36270,
  borrowerInterest: 30544,
  stateGovSubsidyInterest: 82356,
  year: 'Legacy',
  startDate: dayjs('2022-08-18T06:57'),
  endDate: dayjs('2022-08-18T06:20'),
  lastModified: dayjs('2022-08-18T13:44'),
  lastModifiedBy: 'Cambridgeshire',
  createdBy: 'Pizza Personal payment',
  createdOn: dayjs('2022-08-19T02:14'),
  isDeleted: false,
  freeField1: 'up Hong 1080p',
  freeField2: 'Pizza Marketing invoice',
  freeField3: 'calculating',
};

export const sampleWithNewData: NewGRInterestDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
