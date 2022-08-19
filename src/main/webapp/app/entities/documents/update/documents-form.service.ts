import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDocuments, NewDocuments } from '../documents.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDocuments for edit and NewDocumentsFormGroupInput for create.
 */
type DocumentsFormGroupInput = IDocuments | PartialWithRequiredKeyOf<NewDocuments>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDocuments | NewDocuments> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type DocumentsFormRawValue = FormValueOf<IDocuments>;

type NewDocumentsFormRawValue = FormValueOf<NewDocuments>;

type DocumentsFormDefaults = Pick<NewDocuments, 'id' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type DocumentsFormGroupContent = {
  id: FormControl<DocumentsFormRawValue['id'] | NewDocuments['id']>;
  type: FormControl<DocumentsFormRawValue['type']>;
  fileName: FormControl<DocumentsFormRawValue['fileName']>;
  filePath: FormControl<DocumentsFormRawValue['filePath']>;
  fileUrl: FormControl<DocumentsFormRawValue['fileUrl']>;
  lastModified: FormControl<DocumentsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<DocumentsFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<DocumentsFormRawValue['createdBy']>;
  createdOn: FormControl<DocumentsFormRawValue['createdOn']>;
  isDeleted: FormControl<DocumentsFormRawValue['isDeleted']>;
  freeField1: FormControl<DocumentsFormRawValue['freeField1']>;
  freeField2: FormControl<DocumentsFormRawValue['freeField2']>;
  freeField3: FormControl<DocumentsFormRawValue['freeField3']>;
  member: FormControl<DocumentsFormRawValue['member']>;
};

export type DocumentsFormGroup = FormGroup<DocumentsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DocumentsFormService {
  createDocumentsFormGroup(documents: DocumentsFormGroupInput = { id: null }): DocumentsFormGroup {
    const documentsRawValue = this.convertDocumentsToDocumentsRawValue({
      ...this.getFormDefaults(),
      ...documents,
    });
    return new FormGroup<DocumentsFormGroupContent>({
      id: new FormControl(
        { value: documentsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      type: new FormControl(documentsRawValue.type),
      fileName: new FormControl(documentsRawValue.fileName),
      filePath: new FormControl(documentsRawValue.filePath),
      fileUrl: new FormControl(documentsRawValue.fileUrl),
      lastModified: new FormControl(documentsRawValue.lastModified),
      lastModifiedBy: new FormControl(documentsRawValue.lastModifiedBy),
      createdBy: new FormControl(documentsRawValue.createdBy),
      createdOn: new FormControl(documentsRawValue.createdOn),
      isDeleted: new FormControl(documentsRawValue.isDeleted),
      freeField1: new FormControl(documentsRawValue.freeField1),
      freeField2: new FormControl(documentsRawValue.freeField2),
      freeField3: new FormControl(documentsRawValue.freeField3),
      member: new FormControl(documentsRawValue.member),
    });
  }

  getDocuments(form: DocumentsFormGroup): IDocuments | NewDocuments {
    return this.convertDocumentsRawValueToDocuments(form.getRawValue() as DocumentsFormRawValue | NewDocumentsFormRawValue);
  }

  resetForm(form: DocumentsFormGroup, documents: DocumentsFormGroupInput): void {
    const documentsRawValue = this.convertDocumentsToDocumentsRawValue({ ...this.getFormDefaults(), ...documents });
    form.reset(
      {
        ...documentsRawValue,
        id: { value: documentsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DocumentsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertDocumentsRawValueToDocuments(rawDocuments: DocumentsFormRawValue | NewDocumentsFormRawValue): IDocuments | NewDocuments {
    return {
      ...rawDocuments,
      lastModified: dayjs(rawDocuments.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawDocuments.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertDocumentsToDocumentsRawValue(
    documents: IDocuments | (Partial<NewDocuments> & DocumentsFormDefaults)
  ): DocumentsFormRawValue | PartialWithRequiredKeyOf<NewDocumentsFormRawValue> {
    return {
      ...documents,
      lastModified: documents.lastModified ? documents.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: documents.createdOn ? documents.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
