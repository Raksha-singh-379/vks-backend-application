import dayjs from 'dayjs/esm';

import { DocumentType } from 'app/entities/enumerations/document-type.model';

import { IDocuments, NewDocuments } from './documents.model';

export const sampleWithRequiredData: IDocuments = {
  id: 7443,
};

export const sampleWithPartialData: IDocuments = {
  id: 58524,
  filePath: 'Cheese',
  fileUrl: 'Buckinghamshire XSS',
  lastModified: dayjs('2022-08-18T16:52'),
  lastModifiedBy: 'Borders capability',
  createdBy: 'architect',
  createdOn: dayjs('2022-08-19T00:25'),
  freeField1: 'SCSI convergence',
  freeField2: 'Synchronised',
  freeField3: 'Engineer Re-engineered quantify',
};

export const sampleWithFullData: IDocuments = {
  id: 86116,
  type: DocumentType['DHORAN_DOC'],
  fileName: 'Lodge',
  filePath: 'Oklahoma morph',
  fileUrl: 'User-centric',
  lastModified: dayjs('2022-08-19T02:27'),
  lastModifiedBy: 'Manager Unbranded',
  createdBy: 'exuding',
  createdOn: dayjs('2022-08-19T04:59'),
  isDeleted: false,
  freeField1: 'Malagasy Alaska morph',
  freeField2: 'silver plum primary',
  freeField3: 'Malagasy South withdrawal',
};

export const sampleWithNewData: NewDocuments = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
