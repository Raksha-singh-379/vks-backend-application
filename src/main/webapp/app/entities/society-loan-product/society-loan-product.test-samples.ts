import dayjs from 'dayjs/esm';

import { ISocietyLoanProduct, NewSocietyLoanProduct } from './society-loan-product.model';

export const sampleWithRequiredData: ISocietyLoanProduct = {
  id: 54425,
};

export const sampleWithPartialData: ISocietyLoanProduct = {
  id: 88331,
  productName: 'invoice transition copying',
  accHeadCode: 'Avenue Handcrafted',
  borrowingInterestRate: 22212,
  maxLoanAmt: 28884,
  noOfInstallment: 33588,
  parentAccHeadCode: 'supply-chains harness',
  unitSize: 87574,
  validFrom: dayjs('2022-08-18T23:43'),
  lastModifiedBy: 'Avon',
};

export const sampleWithFullData: ISocietyLoanProduct = {
  id: 18391,
  productName: 'matrix West',
  accHeadCode: 'Legacy Shoes',
  borrowingInterestRate: 71475,
  duration: 91711,
  interestRate: 61685,
  lastDateOfRepayment: dayjs('2022-08-18T06:54'),
  loanNumber: 'Group',
  maxLoanAmt: 34504,
  noOfDisbursement: 54956,
  noOfInstallment: 73035,
  parentAccHeadCode: '5th Pike bandwidth',
  parentAccHeadId: 67346,
  penaltyInterest: 81173,
  productType: 'Functionality Concrete user-facing',
  resolutionDate: dayjs('2022-08-18T14:48'),
  resolutionNo: 'Virtual',
  status: 'red Place Cotton',
  surcharge: 57490,
  unitSize: 53072,
  validFrom: dayjs('2022-08-18T08:00'),
  validTo: dayjs('2022-08-18T21:26'),
  createdOn: dayjs('2022-08-18T05:27'),
  createdBy: 'program Facilitator',
  isActivate: true,
  lastModified: dayjs('2022-08-19T00:53'),
  lastModifiedBy: 'Springs e-services Gorgeous',
  freeField1: 'generate compressing',
  freeField2: 'Dalasi parse',
  freeField3: 'explicit payment payment',
};

export const sampleWithNewData: NewSocietyLoanProduct = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
