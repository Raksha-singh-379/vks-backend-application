import dayjs from 'dayjs/esm';
import { ISociety } from 'app/entities/society/society.model';
import { ParameterLookupType } from 'app/entities/enumerations/parameter-lookup-type.model';

export interface IParameterLookup {
  id: number;
  type?: ParameterLookupType | null;
  name?: string | null;
  description?: string | null;
  value?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  society?: Pick<ISociety, 'id'> | null;
}

export type NewParameterLookup = Omit<IParameterLookup, 'id'> & { id: null };
