import dayjs from 'dayjs/esm';
import { ISociety } from 'app/entities/society/society.model';
import { IBankDhoranDetails } from 'app/entities/bank-dhoran-details/bank-dhoran-details.model';

export interface ISocietyConfig {
  id: number;
  configName?: string | null;
  configType?: string | null;
  status?: string | null;
  value?: number | null;
  year?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  society?: Pick<ISociety, 'id'> | null;
  bankDhoranDetails?: Pick<IBankDhoranDetails, 'id'> | null;
}

export type NewSocietyConfig = Omit<ISocietyConfig, 'id'> & { id: null };
