import dayjs from 'dayjs/esm';
import { IMember } from 'app/entities/member/member.model';
import { DocumentType } from 'app/entities/enumerations/document-type.model';

export interface IDocuments {
  id: number;
  type?: DocumentType | null;
  fileName?: string | null;
  filePath?: string | null;
  fileUrl?: string | null;
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

export type NewDocuments = Omit<IDocuments, 'id'> & { id: null };
