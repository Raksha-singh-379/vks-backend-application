import dayjs from 'dayjs/esm';
import { ISociety } from 'app/entities/society/society.model';

export interface IGRInterestDetails {
  id: number;
  loanGrName?: string | null;
  criteria?: string | null;
  productType?: string | null;
  isActivated?: boolean | null;
  borrowingInterestRate?: number | null;
  interestOnLoan?: number | null;
  penaltyInterest?: number | null;
  surcharge?: number | null;
  loanDuration?: number | null;
  numberOFInstallment?: number | null;
  extendedInterstRate?: number | null;
  centralGovSubsidyInterest?: number | null;
  distBankSubsidyInterest?: number | null;
  borrowerInterest?: number | null;
  stateGovSubsidyInterest?: number | null;
  year?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  society?: Pick<ISociety, 'id'> | null;
}

export type NewGRInterestDetails = Omit<IGRInterestDetails, 'id'> & { id: null };
