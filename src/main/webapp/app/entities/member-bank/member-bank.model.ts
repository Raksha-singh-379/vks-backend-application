import dayjs from 'dayjs/esm';
import { IMember } from 'app/entities/member/member.model';

export interface IMemberBank {
  id: number;
  bankName?: string | null;
  branchName?: string | null;
  accountNumber?: number | null;
  ifsccode?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  member?: Pick<IMember, 'id'> | null;
  member?: Pick<IMember, 'id'> | null;
}

export type NewMemberBank = Omit<IMemberBank, 'id'> & { id: null };
