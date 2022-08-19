import dayjs from 'dayjs/esm';
import { ILoanDemand } from 'app/entities/loan-demand/loan-demand.model';
import { IMember } from 'app/entities/member/member.model';
import { ISocietyLoanProduct } from 'app/entities/society-loan-product/society-loan-product.model';
import { IBankDhoranDetails } from 'app/entities/bank-dhoran-details/bank-dhoran-details.model';
import { LoanType } from 'app/entities/enumerations/loan-type.model';
import { LoanStatus } from 'app/entities/enumerations/loan-status.model';
import { NpaClassification } from 'app/entities/enumerations/npa-classification.model';

export interface ILoanDetails {
  id: number;
  loanAmount?: number | null;
  loanAccountNo?: string | null;
  loanType?: LoanType | null;
  status?: LoanStatus | null;
  loanStartDate?: dayjs.Dayjs | null;
  loanEndDate?: dayjs.Dayjs | null;
  loanPlannedClosureDate?: dayjs.Dayjs | null;
  loanCloserDate?: dayjs.Dayjs | null;
  loanEffectiveDate?: dayjs.Dayjs | null;
  loanClassification?: NpaClassification | null;
  resolutionNo?: string | null;
  resolutionDate?: dayjs.Dayjs | null;
  costOfInvestment?: number | null;
  loanBenefitingArea?: number | null;
  dccbLoanNo?: number | null;
  mortgageDeedNo?: number | null;
  mortgageDate?: dayjs.Dayjs | null;
  extentMorgageValue?: number | null;
  parentAccHeadCode?: string | null;
  loanAccountName?: string | null;
  disbursementAmt?: number | null;
  disbursementStatus?: string | null;
  year?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  loanDemand?: Pick<ILoanDemand, 'id'> | null;
  member?: Pick<IMember, 'id'> | null;
  loanDemand?: Pick<ILoanDemand, 'id'> | null;
  societyLoanProduct?: Pick<ISocietyLoanProduct, 'id'> | null;
  bankDhoranDetails?: Pick<IBankDhoranDetails, 'id'> | null;
}

export type NewLoanDetails = Omit<ILoanDetails, 'id'> & { id: null };
