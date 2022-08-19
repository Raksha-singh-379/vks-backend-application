import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SocietyPrerequisiteFormService, SocietyPrerequisiteFormGroup } from './society-prerequisite-form.service';
import { ISocietyPrerequisite } from '../society-prerequisite.model';
import { SocietyPrerequisiteService } from '../service/society-prerequisite.service';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';
import { LoanType } from 'app/entities/enumerations/loan-type.model';

@Component({
  selector: 'jhi-society-prerequisite-update',
  templateUrl: './society-prerequisite-update.component.html',
})
export class SocietyPrerequisiteUpdateComponent implements OnInit {
  isSaving = false;
  societyPrerequisite: ISocietyPrerequisite | null = null;
  loanTypeValues = Object.keys(LoanType);

  societiesSharedCollection: ISociety[] = [];

  editForm: SocietyPrerequisiteFormGroup = this.societyPrerequisiteFormService.createSocietyPrerequisiteFormGroup();

  constructor(
    protected societyPrerequisiteService: SocietyPrerequisiteService,
    protected societyPrerequisiteFormService: SocietyPrerequisiteFormService,
    protected societyService: SocietyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSociety = (o1: ISociety | null, o2: ISociety | null): boolean => this.societyService.compareSociety(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ societyPrerequisite }) => {
      this.societyPrerequisite = societyPrerequisite;
      if (societyPrerequisite) {
        this.updateForm(societyPrerequisite);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const societyPrerequisite = this.societyPrerequisiteFormService.getSocietyPrerequisite(this.editForm);
    if (societyPrerequisite.id !== null) {
      this.subscribeToSaveResponse(this.societyPrerequisiteService.update(societyPrerequisite));
    } else {
      this.subscribeToSaveResponse(this.societyPrerequisiteService.create(societyPrerequisite));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISocietyPrerequisite>>): void {
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

  protected updateForm(societyPrerequisite: ISocietyPrerequisite): void {
    this.societyPrerequisite = societyPrerequisite;
    this.societyPrerequisiteFormService.resetForm(this.editForm, societyPrerequisite);

    this.societiesSharedCollection = this.societyService.addSocietyToCollectionIfMissing<ISociety>(
      this.societiesSharedCollection,
      societyPrerequisite.society
    );
  }

  protected loadRelationshipsOptions(): void {
    this.societyService
      .query()
      .pipe(map((res: HttpResponse<ISociety[]>) => res.body ?? []))
      .pipe(
        map((societies: ISociety[]) =>
          this.societyService.addSocietyToCollectionIfMissing<ISociety>(societies, this.societyPrerequisite?.society)
        )
      )
      .subscribe((societies: ISociety[]) => (this.societiesSharedCollection = societies));
  }
}
