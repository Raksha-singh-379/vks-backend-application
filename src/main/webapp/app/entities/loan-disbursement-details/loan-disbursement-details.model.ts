import dayjs from 'dayjs/esm';
import { ILoanDetails } from 'app/entities/loan-details/loan-details.model';
import { PaymentMode } from 'app/entities/enumerations/payment-mode.model';
import { LoanType } from 'app/entities/enumerations/loan-type.model';

export interface ILoanDisbursementDetails {
  id: number;
  disbursementDate?: dayjs.Dayjs | null;
  disbursementAmount?: number | null;
  disbursementNumber?: number | null;
  disbursementStatus?: string | null;
  paymentMode?: PaymentMode | null;
  type?: LoanType | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  loanDetails?: Pick<ILoanDetails, 'id'> | null;
}

export type NewLoanDisbursementDetails = Omit<ILoanDisbursementDetails, 'id'> & { id: null };
