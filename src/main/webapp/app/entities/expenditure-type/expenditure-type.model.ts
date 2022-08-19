import dayjs from 'dayjs/esm';
import { ISociety } from 'app/entities/society/society.model';

export interface IExpenditureType {
  id: number;
  expenditureDesc?: string | null;
  expenditureType?: string | null;
  expenditureCategory?: boolean | null;
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

export type NewExpenditureType = Omit<IExpenditureType, 'id'> & { id: null };
