import dayjs from 'dayjs/esm';
import { ISociety } from 'app/entities/society/society.model';

export interface ISchemesDetails {
  id: number;
  fdDurationDays?: number | null;
  interest?: number | null;
  lockInPeriod?: number | null;
  schemeName?: string | null;
  rdDurationMonths?: number | null;
  reinvestInterest?: boolean | null;
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

export type NewSchemesDetails = Omit<ISchemesDetails, 'id'> & { id: null };
