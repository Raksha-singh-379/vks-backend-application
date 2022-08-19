import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ExpenditureTypeFormService, ExpenditureTypeFormGroup } from './expenditure-type-form.service';
import { IExpenditureType } from '../expenditure-type.model';
import { ExpenditureTypeService } from '../service/expenditure-type.service';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';

@Component({
  selector: 'jhi-expenditure-type-update',
  templateUrl: './expenditure-type-update.component.html',
})
export class ExpenditureTypeUpdateComponent implements OnInit {
  isSaving = false;
  expenditureType: IExpenditureType | null = null;

  societiesSharedCollection: ISociety[] = [];

  editForm: ExpenditureTypeFormGroup = this.expenditureTypeFormService.createExpenditureTypeFormGroup();

  constructor(
    protected expenditureTypeService: ExpenditureTypeService,
    protected expenditureTypeFormService: ExpenditureTypeFormService,
    protected societyService: SocietyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSociety = (o1: ISociety | null, o2: ISociety | null): boolean => this.societyService.compareSociety(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ expenditureType }) => {
      this.expenditureType = expenditureType;
      if (expenditureType) {
        this.updateForm(expenditureType);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const expenditureType = this.expenditureTypeFormService.getExpenditureType(this.editForm);
    if (expenditureType.id !== null) {
      this.subscribeToSaveResponse(this.expenditureTypeService.update(expenditureType));
    } else {
      this.subscribeToSaveResponse(this.expenditureTypeService.create(expenditureType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExpenditureType>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(expenditureType: IExpenditureType): void {
    this.expenditureType = expenditureType;
    this.expenditureTypeFormService.resetForm(this.editForm, expenditureType);

    this.societiesSharedCollection = this.societyService.addSocietyToCollectionIfMissing<ISociety>(
      this.societiesSharedCollection,
      expenditureType.society
    );
  }

  protected loadRelationshipsOptions(): void {
    this.societyService
      .query()
      .pipe(map((res: HttpResponse<ISociety[]>) => res.body ?? []))
      .pipe(
        map((societies: ISociety[]) =>
          this.societyService.addSocietyToCollectionIfMissing<ISociety>(societies, this.expenditureType?.society)
        )
      )
      .subscribe((societies: ISociety[]) => (this.societiesSharedCollection = societies));
  }
}
