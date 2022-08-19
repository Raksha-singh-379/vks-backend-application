import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SocietyAssetsFormService, SocietyAssetsFormGroup } from './society-assets-form.service';
import { ISocietyAssets } from '../society-assets.model';
import { SocietyAssetsService } from '../service/society-assets.service';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';
import { SocietyAssetsType } from 'app/entities/enumerations/society-assets-type.model';

@Component({
  selector: 'jhi-society-assets-update',
  templateUrl: './society-assets-update.component.html',
})
export class SocietyAssetsUpdateComponent implements OnInit {
  isSaving = false;
  societyAssets: ISocietyAssets | null = null;
  societyAssetsTypeValues = Object.keys(SocietyAssetsType);

  societiesSharedCollection: ISociety[] = [];

  editForm: SocietyAssetsFormGroup = this.societyAssetsFormService.createSocietyAssetsFormGroup();

  constructor(
    protected societyAssetsService: SocietyAssetsService,
    protected societyAssetsFormService: SocietyAssetsFormService,
    protected societyService: SocietyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSociety = (o1: ISociety | null, o2: ISociety | null): boolean => this.societyService.compareSociety(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ societyAssets }) => {
      this.societyAssets = societyAssets;
      if (societyAssets) {
        this.updateForm(societyAssets);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const societyAssets = this.societyAssetsFormService.getSocietyAssets(this.editForm);
    if (societyAssets.id !== null) {
      this.subscribeToSaveResponse(this.societyAssetsService.update(societyAssets));
    } else {
      this.subscribeToSaveResponse(this.societyAssetsService.create(societyAssets));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISocietyAssets>>): void {
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

  protected updateForm(societyAssets: ISocietyAssets): void {
    this.societyAssets = societyAssets;
    this.societyAssetsFormService.resetForm(this.editForm, societyAssets);

    this.societiesSharedCollection = this.societyService.addSocietyToCollectionIfMissing<ISociety>(
      this.societiesSharedCollection,
      societyAssets.society
    );
  }

  protected loadRelationshipsOptions(): void {
    this.societyService
      .query()
      .pipe(map((res: HttpResponse<ISociety[]>) => res.body ?? []))
      .pipe(
        map((societies: ISociety[]) =>
          this.societyService.addSocietyToCollectionIfMissing<ISociety>(societies, this.societyAssets?.society)
        )
      )
      .subscribe((societies: ISociety[]) => (this.societiesSharedCollection = societies));
  }
}
