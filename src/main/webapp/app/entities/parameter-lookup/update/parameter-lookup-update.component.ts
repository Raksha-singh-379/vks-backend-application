import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ParameterLookupFormService, ParameterLookupFormGroup } from './parameter-lookup-form.service';
import { IParameterLookup } from '../parameter-lookup.model';
import { ParameterLookupService } from '../service/parameter-lookup.service';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';
import { ParameterLookupType } from 'app/entities/enumerations/parameter-lookup-type.model';

@Component({
  selector: 'jhi-parameter-lookup-update',
  templateUrl: './parameter-lookup-update.component.html',
})
export class ParameterLookupUpdateComponent implements OnInit {
  isSaving = false;
  parameterLookup: IParameterLookup | null = null;
  parameterLookupTypeValues = Object.keys(ParameterLookupType);

  societiesSharedCollection: ISociety[] = [];

  editForm: ParameterLookupFormGroup = this.parameterLookupFormService.createParameterLookupFormGroup();

  constructor(
    protected parameterLookupService: ParameterLookupService,
    protected parameterLookupFormService: ParameterLookupFormService,
    protected societyService: SocietyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSociety = (o1: ISociety | null, o2: ISociety | null): boolean => this.societyService.compareSociety(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parameterLookup }) => {
      this.parameterLookup = parameterLookup;
      if (parameterLookup) {
        this.updateForm(parameterLookup);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parameterLookup = this.parameterLookupFormService.getParameterLookup(this.editForm);
    if (parameterLookup.id !== null) {
      this.subscribeToSaveResponse(this.parameterLookupService.update(parameterLookup));
    } else {
      this.subscribeToSaveResponse(this.parameterLookupService.create(parameterLookup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParameterLookup>>): void {
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

  protected updateForm(parameterLookup: IParameterLookup): void {
    this.parameterLookup = parameterLookup;
    this.parameterLookupFormService.resetForm(this.editForm, parameterLookup);

    this.societiesSharedCollection = this.societyService.addSocietyToCollectionIfMissing<ISociety>(
      this.societiesSharedCollection,
      parameterLookup.society
    );
  }

  protected loadRelationshipsOptions(): void {
    this.societyService
      .query()
      .pipe(map((res: HttpResponse<ISociety[]>) => res.body ?? []))
      .pipe(
        map((societies: ISociety[]) =>
          this.societyService.addSocietyToCollectionIfMissing<ISociety>(societies, this.parameterLookup?.society)
        )
      )
      .subscribe((societies: ISociety[]) => (this.societiesSharedCollection = societies));
  }
}
