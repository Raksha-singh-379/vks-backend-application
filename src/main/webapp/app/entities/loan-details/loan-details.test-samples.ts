import dayjs from 'dayjs/esm';

import { LoanType } from 'app/entities/enumerations/loan-type.model';
import { LoanStatus } from 'app/entities/enumerations/loan-status.model';
import { NpaClassification } from 'app/entities/enumerations/npa-classification.model';

import { ILoanDetails, NewLoanDetails } from './loan-details.model';

export const sampleWithRequiredData: ILoanDetails = {
  id: 96685,
};

export const sampleWithPartialData: ILoanDetails = {
  id: 22583,
  loanAmount: 82644,
  loanAccountNo: 'Buckinghamshire Generic',
  loanType: LoanType['LONG_TERM'],
  loanEndDate: dayjs('2022-08-18T21:50'),
  loanEffectiveDate: dayjs('2022-08-18T13:37'),
  resolutionNo: 'microchip',
  resolutionDate: dayjs('2022-08-19T02:51'),
  costOfInvestment: 14541,
  dccbLoanNo: 82514,
  mortgageDate: dayjs('2022-08-18T06:55'),
  parentAccHeadCode: 'AGP',
  loanAccountName: 'Manat Solutions',
  disbursementAmt: 24729,
  disbursementStatus: 'paradigms Mountains',
  lastModifiedBy: 'methodologies invoice Unbranded',
};

export const sampleWithFullData: ILoanDetails = {
  id: 58990,
  loanAmount: 74704,
  loanAccountNo: 'magenta',
  loanType: LoanType['SHORT_TERM'],
  status: LoanStatus['PENDING'],
  loanStartDate: dayjs('2022-08-18T09:48'),
  loanEndDate: dayjs('2022-08-18T06:48'),
  loanPlannedClosureDate: dayjs('2022-08-18T14:26'),
  loanCloserDate: dayjs('2022-08-18T22:13'),
  loanEffectiveDate: dayjs('2022-08-18T11:28'),
  loanClassification: NpaClassification['SUB_STANDARD_ASSESTS'],
  resolutionNo: 'Realigned',
  resolutionDate: dayjs('2022-08-18T17:40'),
  costOfInvestment: 61648,
  loanBenefitingArea: 38318,
  dccbLoanNo: 16022,
  mortgageDeedNo: 63004,
  mortgageDate: dayjs('2022-08-19T04:01'),
  extentMorgageValue: 91461,
  parentAccHeadCode: 'Sports Hampshire',
  loanAccountName: '60 Incredible Hat',
  disbursementAmt: 67609,
  disbursementStatus: 'relationships',
  year: 'Montana',
  lastModified: dayjs('2022-08-19T01:45'),
  lastModifiedBy: 'human-resource Intelligent',
  freeField1: 'Borders Future',
  freeField2: 'Awesome Oval',
  freeField3: 'Unbranded South Accounts',
};

export const sampleWithNewData: NewLoanDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
