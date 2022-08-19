import dayjs from 'dayjs/esm';

import { IAmortizationDetails, NewAmortizationDetails } from './amortization-details.model';

export const sampleWithRequiredData: IAmortizationDetails = {
  id: 48347,
};

export const sampleWithPartialData: IAmortizationDetails = {
  id: 62228,
  totalOutstandngInterestAmt: 75625,
  paidPrincipleAmt: 41613,
  paidInterestAmt: 77163,
  balPrincipleAmt: 5843,
  balInterestAmt: 59739,
  loanEmiAmt: 3034,
  principleEMI: 74371,
  paymentStatus: 'enable demand-driven',
  lastModified: dayjs('2022-08-18T16:05'),
  freeField1: 'Cambridgeshire lavender calculate',
};

export const sampleWithFullData: IAmortizationDetails = {
  id: 85187,
  installmentDate: dayjs('2022-08-18T10:29'),
  totalOutstandingPrincipleAmt: 60028,
  totalOutstandngInterestAmt: 6105,
  paidPrincipleAmt: 49454,
  paidInterestAmt: 21557,
  balPrincipleAmt: 67286,
  balInterestAmt: 38553,
  loanEmiAmt: 58715,
  principleEMI: 36940,
  paymentStatus: 'loyalty digital Hat',
  year: 'JBOD',
  lastModified: dayjs('2022-08-18T09:16'),
  lastModifiedBy: 'connect',
  freeField1: 'Bedfordshire HTTP',
  freeField2: 'Awesome Cotton Associate',
  freeField3: 'standardization hacking',
};

export const sampleWithNewData: NewAmortizationDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
