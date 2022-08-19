import dayjs from 'dayjs/esm';
import { ISociety } from 'app/entities/society/society.model';
import { LoanType } from 'app/entities/enumerations/loan-type.model';

export interface ISocietyPrerequisite {
  id: number;
  docType?: string | null;
  documentDesc?: string | null;
  documentName?: string | null;
  loanType?: LoanType | null;
  mandatory?: string | null;
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

export type NewSocietyPrerequisite = Omit<ISocietyPrerequisite, 'id'> & { id: null };
