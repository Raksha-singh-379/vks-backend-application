import dayjs from 'dayjs/esm';
import { ISociety } from 'app/entities/society/society.model';
import { NpaClassification } from 'app/entities/enumerations/npa-classification.model';

export interface ISocietyNpaSetting {
  id: number;
  npaClassification?: NpaClassification | null;
  durationStart?: number | null;
  durationEnd?: number | null;
  provision?: number | null;
  year?: number | null;
  interestRate?: number | null;
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

export type NewSocietyNpaSetting = Omit<ISocietyNpaSetting, 'id'> & { id: null };
