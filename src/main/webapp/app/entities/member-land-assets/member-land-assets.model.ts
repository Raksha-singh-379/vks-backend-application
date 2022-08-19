import dayjs from 'dayjs/esm';
import { IMember } from 'app/entities/member/member.model';

export interface IMemberLandAssets {
  id: number;
  landType?: string | null;
  landGatNo?: string | null;
  landAreaInHector?: number | null;
  jindagiPatrakNo?: string | null;
  jindagiAmount?: number | null;
  assetLandAddress?: string | null;
  valueOfLand?: number | null;
  assigneeOfLand?: boolean | null;
  isDeleted?: boolean | null;
  mLLoanNo?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  member?: Pick<IMember, 'id'> | null;
}

export type NewMemberLandAssets = Omit<IMemberLandAssets, 'id'> & { id: null };
