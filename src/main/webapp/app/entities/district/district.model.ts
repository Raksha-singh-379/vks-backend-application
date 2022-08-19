import dayjs from 'dayjs/esm';

export interface IDistrict {
  id: number;
  name?: string | null;
  deleted?: boolean | null;
  lgdCode?: number | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
}

export type NewDistrict = Omit<IDistrict, 'id'> & { id: null };
