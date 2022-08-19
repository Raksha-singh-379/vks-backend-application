import dayjs from 'dayjs/esm';
import { ISocietyAssets } from 'app/entities/society-assets/society-assets.model';

export interface ISocietyAssetsData {
  id: number;
  amount?: number | null;
  balanceQuantity?: number | null;
  balanceValue?: number | null;
  billNo?: string | null;
  mode?: string | null;
  cost?: number | null;
  transactionType?: string | null;
  transactionDate?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  societyAssets?: Pick<ISocietyAssets, 'id'> | null;
}

export type NewSocietyAssetsData = Omit<ISocietyAssetsData, 'id'> & { id: null };
