import dayjs from 'dayjs/esm';

import { LoanStatus } from 'app/entities/enumerations/loan-status.model';

import { ILoanDemand, NewLoanDemand } from './loan-demand.model';

export const sampleWithRequiredData: ILoanDemand = {
  id: 42460,
};

export const sampleWithPartialData: ILoanDemand = {
  id: 18293,
  adjustedDemand: 28141,
  demandedLandAreaInHector: 21950,
  status: LoanStatus['APPLIED'],
  freeField3: 'Savings Avon gold',
};

export const sampleWithFullData: ILoanDemand = {
  id: 22982,
  loanDemandAmount: 66154,
  maxAllowedAmount: 54711,
  adjustedDemand: 17402,
  annualIncome: 60186,
  costOfInvestment: 51020,
  demandedLandAreaInHector: 8209,
  status: LoanStatus['PENDING'],
  seasonOfCropLoan: 'optimizing clicks-and-mortar',
  year: 'Awesome',
  lastModified: dayjs('2022-08-18T18:57'),
  lastModifiedBy: 'Avon Buckinghamshire content',
  freeField1: 'multi-byte matrix Aruban',
  freeField2: 'Honduras Customizable',
  freeField3: 'maroon navigating',
};

export const sampleWithNewData: NewLoanDemand = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
