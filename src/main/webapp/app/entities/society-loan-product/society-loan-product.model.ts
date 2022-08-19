import dayjs from 'dayjs/esm';
import { ISociety } from 'app/entities/society/society.model';
import { IBankDhoranDetails } from 'app/entities/bank-dhoran-details/bank-dhoran-details.model';
import { IGRInterestDetails } from 'app/entities/gr-interest-details/gr-interest-details.model';

export interface ISocietyLoanProduct {
  id: number;
  productName?: string | null;
  accHeadCode?: string | null;
  borrowingInterestRate?: number | null;
  duration?: number | null;
  interestRate?: number | null;
  lastDateOfRepayment?: dayjs.Dayjs | null;
  loanNumber?: string | null;
  maxLoanAmt?: number | null;
  noOfDisbursement?: number | null;
  noOfInstallment?: number | null;
  parentAccHeadCode?: string | null;
  parentAccHeadId?: number | null;
  penaltyInterest?: number | null;
  productType?: string | null;
  resolutionDate?: dayjs.Dayjs | null;
  resolutionNo?: string | null;
  status?: string | null;
  surcharge?: number | null;
  unitSize?: number | null;
  validFrom?: dayjs.Dayjs | null;
  validTo?: dayjs.Dayjs | null;
  createdOn?: dayjs.Dayjs | null;
  createdBy?: string | null;
  isActivate?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  society?: Pick<ISociety, 'id'> | null;
  bankDhoranDetails?: Pick<IBankDhoranDetails, 'id'> | null;
  gRInterestDetails?: Pick<IGRInterestDetails, 'id'> | null;
}

export type NewSocietyLoanProduct = Omit<ISocietyLoanProduct, 'id'> & { id: null };
