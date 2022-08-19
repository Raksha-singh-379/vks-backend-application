import dayjs from 'dayjs/esm';
import { ISociety } from 'app/entities/society/society.model';

export interface IBankDhoranDetails {
  id: number;
  dhoranName?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  year?: string | null;
  isActivate?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  society?: Pick<ISociety, 'id'> | null;
}

export type NewBankDhoranDetails = Omit<IBankDhoranDetails, 'id'> & { id: null };
