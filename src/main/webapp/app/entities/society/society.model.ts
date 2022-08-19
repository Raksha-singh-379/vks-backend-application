import dayjs from 'dayjs/esm';
import { IVillage } from 'app/entities/village/village.model';
import { IState } from 'app/entities/state/state.model';
import { IDistrict } from 'app/entities/district/district.model';
import { ITaluka } from 'app/entities/taluka/taluka.model';

export interface ISociety {
  id: number;
  societyName?: string | null;
  address?: string | null;
  village?: string | null;
  tahsil?: string | null;
  state?: string | null;
  district?: string | null;
  registrationNumber?: number | null;
  gstinNumber?: number | null;
  panNumber?: number | null;
  tanNumber?: number | null;
  phoneNumber?: number | null;
  emailAddress?: string | null;
  pinCode?: number | null;
  createdOn?: dayjs.Dayjs | null;
  createdBy?: string | null;
  description?: string | null;
  isActivate?: boolean | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  city?: Pick<IVillage, 'id'> | null;
  state?: Pick<IState, 'id'> | null;
  district?: Pick<IDistrict, 'id'> | null;
  taluka?: Pick<ITaluka, 'id'> | null;
  society?: Pick<ISociety, 'id'> | null;
}

export type NewSociety = Omit<ISociety, 'id'> & { id: null };
