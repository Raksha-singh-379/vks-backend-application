import dayjs from 'dayjs/esm';
import { ILedgerAccounts } from 'app/entities/ledger-accounts/ledger-accounts.model';
import { MappingType } from 'app/entities/enumerations/mapping-type.model';

export interface IAccountMapping {
  id: number;
  type?: MappingType | null;
  mappingName?: string | null;
  ledgerAccHeadCode?: string | null;
  ledgerAccountId?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  accountMapping?: Pick<ILedgerAccounts, 'id'> | null;
}

export type NewAccountMapping = Omit<IAccountMapping, 'id'> & { id: null };
