import dayjs from 'dayjs/esm';
import { ISociety } from 'app/entities/society/society.model';

export interface ISocietyBanksDetails {
  id: number;
  bankName?: string | null;
  ifsccode?: string | null;
  branchName?: string | null;
  accountNumber?: string | null;
  isActive?: boolean | null;
  accountType?: string | null;
  accHeadCode?: string | null;
  parentAccHeadCode?: string | null;
  accountName?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  society?: Pick<ISociety, 'id'> | null;
}

export type NewSocietyBanksDetails = Omit<ISocietyBanksDetails, 'id'> & { id: null };
