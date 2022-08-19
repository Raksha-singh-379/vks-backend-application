import dayjs from 'dayjs/esm';
import { ILoanDemand } from 'app/entities/loan-demand/loan-demand.model';
import { LoanStatus } from 'app/entities/enumerations/loan-status.model';

export interface ILoanWatapDetails {
  id: number;
  loanWatapDate?: dayjs.Dayjs | null;
  cropLandInHector?: number | null;
  slotNumber?: number | null;
  loanAmount?: number | null;
  season?: string | null;
  status?: LoanStatus | null;
  year?: string | null;
  isDeleted?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  loanDemand?: Pick<ILoanDemand, 'id'> | null;
}

export type NewLoanWatapDetails = Omit<ILoanWatapDetails, 'id'> & { id: null };
