import dayjs from 'dayjs/esm';
import { IMember } from 'app/entities/member/member.model';
import { AssetType } from 'app/entities/enumerations/asset-type.model';

export interface IMemberAssets {
  id: number;
  assetAmount?: number | null;
  assetType?: AssetType | null;
  assetArea?: number | null;
  assetAddress?: string | null;
  numberOfAssets?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  member?: Pick<IMember, 'id'> | null;
}

export type NewMemberAssets = Omit<IMemberAssets, 'id'> & { id: null };
