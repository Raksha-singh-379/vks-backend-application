import dayjs from 'dayjs/esm';
import { ISociety } from 'app/entities/society/society.model';
import { Season } from 'app/entities/enumerations/season.model';

export interface ISocietyCropRegistration {
  id: number;
  cropName?: string | null;
  monthDuration?: number | null;
  season?: Season | null;
  cropLimit?: number | null;
  year?: number | null;
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

export type NewSocietyCropRegistration = Omit<ISocietyCropRegistration, 'id'> & { id: null };
