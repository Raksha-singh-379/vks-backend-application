import dayjs from 'dayjs/esm';
import { ISociety } from 'app/entities/society/society.model';
import { LedgerClassification } from 'app/entities/enumerations/ledger-classification.model';

export interface ILedgerAccounts {
  id: number;
  accountNo?: number | null;
  accountName?: string | null;
  accBalance?: number | null;
  accHeadCode?: string | null;
  ledgerCode?: string | null;
  appCode?: string | null;
  ledgerClassification?: LedgerClassification | null;
  category?: string | null;
  level?: number | null;
  year?: string | null;
  accountType?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  society?: Pick<ISociety, 'id'> | null;
  ledgerAccounts?: Pick<ILedgerAccounts, 'id'> | null;
}

export type NewLedgerAccounts = Omit<ILedgerAccounts, 'id'> & { id: null };
