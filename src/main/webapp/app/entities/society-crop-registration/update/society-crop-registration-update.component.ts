import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SocietyCropRegistrationFormService, SocietyCropRegistrationFormGroup } from './society-crop-registration-form.service';
import { ISocietyCropRegistration } from '../society-crop-registration.model';
import { SocietyCropRegistrationService } from '../service/society-crop-registration.service';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';
import { Season } from 'app/entities/enumerations/season.model';

@Component({
  selector: 'jhi-society-crop-registration-update',
  templateUrl: './society-crop-registration-update.component.html',
})
export class SocietyCropRegistrationUpdateComponent implements OnInit {
  isSaving = false;
  societyCropRegistration: ISocietyCropRegistration | null = null;
  seasonValues = Object.keys(Season);

  societiesSharedCollection: ISociety[] = [];

  editForm: SocietyCropRegistrationFormGroup = this.societyCropRegistrationFormService.createSocietyCropRegistrationFormGroup();

  constructor(
    protected societyCropRegistrationService: SocietyCropRegistrationService,
    protected societyCropRegistrationFormService: SocietyCropRegistrationFormService,
    protected societyService: SocietyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSociety = (o1: ISociety | null, o2: ISociety | null): boolean => this.societyService.compareSociety(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ societyCropRegistration }) => {
      this.societyCropRegistration = societyCropRegistration;
      if (societyCropRegistration) {
        this.updateForm(societyCropRegistration);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const societyCropRegistration = this.societyCropRegistrationFormService.getSocietyCropRegistration(this.editForm);
    if (societyCropRegistration.id !== null) {
      this.subscribeToSaveResponse(this.societyCropRegistrationService.update(societyCropRegistration));
    } else {
      this.subscribeToSaveResponse(this.societyCropRegistrationService.create(societyCropRegistration));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISocietyCropRegistration>>): void {
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

  protected updateForm(societyCropRegistration: ISocietyCropRegistration): void {
    this.societyCropRegistration = societyCropRegistration;
    this.societyCropRegistrationFormService.resetForm(this.editForm, societyCropRegistration);

    this.societiesSharedCollection = this.societyService.addSocietyToCollectionIfMissing<ISociety>(
      this.societiesSharedCollection,
      societyCropRegistration.society
    );
  }

  protected loadRelationshipsOptions(): void {
    this.societyService
      .query()
      .pipe(map((res: HttpResponse<ISociety[]>) => res.body ?? []))
      .pipe(
        map((societies: ISociety[]) =>
          this.societyService.addSocietyToCollectionIfMissing<ISociety>(societies, this.societyCropRegistration?.society)
        )
      )
      .subscribe((societies: ISociety[]) => (this.societiesSharedCollection = societies));
  }
}
