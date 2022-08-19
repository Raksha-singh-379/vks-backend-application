import dayjs from 'dayjs/esm';
import { IMember } from 'app/entities/member/member.model';
import { ISocietyLoanProduct } from 'app/entities/society-loan-product/society-loan-product.model';
import { IMemberLandAssets } from 'app/entities/member-land-assets/member-land-assets.model';
import { ISocietyCropRegistration } from 'app/entities/society-crop-registration/society-crop-registration.model';
import { LoanStatus } from 'app/entities/enumerations/loan-status.model';

export interface ILoanDemand {
  id: number;
  loanDemandAmount?: number | null;
  maxAllowedAmount?: number | null;
  adjustedDemand?: number | null;
  annualIncome?: number | null;
  costOfInvestment?: number | null;
  demandedLandAreaInHector?: number | null;
  status?: LoanStatus | null;
  seasonOfCropLoan?: string | null;
  year?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  member?: Pick<IMember, 'id'> | null;
  societyLoanProduct?: Pick<ISocietyLoanProduct, 'id'> | null;
  memberLandAssets?: Pick<IMemberLandAssets, 'id'> | null;
  societyCropRegistration?: Pick<ISocietyCropRegistration, 'id'> | null;
}

export type NewLoanDemand = Omit<ILoanDemand, 'id'> & { id: null };
