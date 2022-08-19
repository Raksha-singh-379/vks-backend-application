import dayjs from 'dayjs/esm';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyAssetsType } from 'app/entities/enumerations/society-assets-type.model';

export interface ISocietyAssets {
  id: number;
  societyAssetsName?: string | null;
  type?: SocietyAssetsType | null;
  category?: string | null;
  depreciation?: number | null;
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

export type NewSocietyAssets = Omit<ISocietyAssets, 'id'> & { id: null };
