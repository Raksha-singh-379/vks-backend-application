import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SocietyNpaSettingFormService, SocietyNpaSettingFormGroup } from './society-npa-setting-form.service';
import { ISocietyNpaSetting } from '../society-npa-setting.model';
import { SocietyNpaSettingService } from '../service/society-npa-setting.service';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';
import { NpaClassification } from 'app/entities/enumerations/npa-classification.model';

@Component({
  selector: 'jhi-society-npa-setting-update',
  templateUrl: './society-npa-setting-update.component.html',
})
export class SocietyNpaSettingUpdateComponent implements OnInit {
  isSaving = false;
  societyNpaSetting: ISocietyNpaSetting | null = null;
  npaClassificationValues = Object.keys(NpaClassification);

  societiesSharedCollection: ISociety[] = [];

  editForm: SocietyNpaSettingFormGroup = this.societyNpaSettingFormService.createSocietyNpaSettingFormGroup();

  constructor(
    protected societyNpaSettingService: SocietyNpaSettingService,
    protected societyNpaSettingFormService: SocietyNpaSettingFormService,
    protected societyService: SocietyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSociety = (o1: ISociety | null, o2: ISociety | null): boolean => this.societyService.compareSociety(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ societyNpaSetting }) => {
      this.societyNpaSetting = societyNpaSetting;
      if (societyNpaSetting) {
        this.updateForm(societyNpaSetting);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const societyNpaSetting = this.societyNpaSettingFormService.getSocietyNpaSetting(this.editForm);
    if (societyNpaSetting.id !== null) {
      this.subscribeToSaveResponse(this.societyNpaSettingService.update(societyNpaSetting));
    } else {
      this.subscribeToSaveResponse(this.societyNpaSettingService.create(societyNpaSetting));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISocietyNpaSetting>>): void {
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

  protected updateForm(societyNpaSetting: ISocietyNpaSetting): void {
    this.societyNpaSetting = societyNpaSetting;
    this.societyNpaSettingFormService.resetForm(this.editForm, societyNpaSetting);

    this.societiesSharedCollection = this.societyService.addSocietyToCollectionIfMissing<ISociety>(
      this.societiesSharedCollection,
      societyNpaSetting.society
    );
  }

  protected loadRelationshipsOptions(): void {
    this.societyService
      .query()
      .pipe(map((res: HttpResponse<ISociety[]>) => res.body ?? []))
      .pipe(
        map((societies: ISociety[]) =>
          this.societyService.addSocietyToCollectionIfMissing<ISociety>(societies, this.societyNpaSetting?.society)
        )
      )
      .subscribe((societies: ISociety[]) => (this.societiesSharedCollection = societies));
  }
}
