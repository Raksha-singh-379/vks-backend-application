import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { SocietyConfigFormService, SocietyConfigFormGroup } from './society-config-form.service';
import { ISocietyConfig } from '../society-config.model';
import { SocietyConfigService } from '../service/society-config.service';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';
import { IBankDhoranDetails } from 'app/entities/bank-dhoran-details/bank-dhoran-details.model';
import { BankDhoranDetailsService } from 'app/entities/bank-dhoran-details/service/bank-dhoran-details.service';

@Component({
  selector: 'jhi-society-config-update',
  templateUrl: './society-config-update.component.html',
})
export class SocietyConfigUpdateComponent implements OnInit {
  isSaving = false;
  societyConfig: ISocietyConfig | null = null;

  societiesSharedCollection: ISociety[] = [];
  bankDhoranDetailsSharedCollection: IBankDhoranDetails[] = [];

  editForm: SocietyConfigFormGroup = this.societyConfigFormService.createSocietyConfigFormGroup();

  constructor(
    protected societyConfigService: SocietyConfigService,
    protected societyConfigFormService: SocietyConfigFormService,
    protected societyService: SocietyService,
    protected bankDhoranDetailsService: BankDhoranDetailsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareSociety = (o1: ISociety | null, o2: ISociety | null): boolean => this.societyService.compareSociety(o1, o2);

  compareBankDhoranDetails = (o1: IBankDhoranDetails | null, o2: IBankDhoranDetails | null): boolean =>
    this.bankDhoranDetailsService.compareBankDhoranDetails(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ societyConfig }) => {
      this.societyConfig = societyConfig;
      if (societyConfig) {
        this.updateForm(societyConfig);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const societyConfig = this.societyConfigFormService.getSocietyConfig(this.editForm);
    if (societyConfig.id !== null) {
      this.subscribeToSaveResponse(this.societyConfigService.update(societyConfig));
    } else {
      this.subscribeToSaveResponse(this.societyConfigService.create(societyConfig));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISocietyConfig>>): void {
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

  protected updateForm(societyConfig: ISocietyConfig): void {
    this.societyConfig = societyConfig;
    this.societyConfigFormService.resetForm(this.editForm, societyConfig);

    this.societiesSharedCollection = this.societyService.addSocietyToCollectionIfMissing<ISociety>(
      this.societiesSharedCollection,
      societyConfig.society
    );
    this.bankDhoranDetailsSharedCollection = this.bankDhoranDetailsService.addBankDhoranDetailsToCollectionIfMissing<IBankDhoranDetails>(
      this.bankDhoranDetailsSharedCollection,
      societyConfig.bankDhoranDetails
    );
  }

  protected loadRelationshipsOptions(): void {
    this.societyService
      .query()
      .pipe(map((res: HttpResponse<ISociety[]>) => res.body ?? []))
      .pipe(
        map((societies: ISociety[]) =>
          this.societyService.addSocietyToCollectionIfMissing<ISociety>(societies, this.societyConfig?.society)
        )
      )
      .subscribe((societies: ISociety[]) => (this.societiesSharedCollection = societies));

    this.bankDhoranDetailsService
      .query()
      .pipe(map((res: HttpResponse<IBankDhoranDetails[]>) => res.body ?? []))
      .pipe(
        map((bankDhoranDetails: IBankDhoranDetails[]) =>
          this.bankDhoranDetailsService.addBankDhoranDetailsToCollectionIfMissing<IBankDhoranDetails>(
            bankDhoranDetails,
            this.societyConfig?.bankDhoranDetails
          )
        )
      )
      .subscribe((bankDhoranDetails: IBankDhoranDetails[]) => (this.bankDhoranDetailsSharedCollection = bankDhoranDetails));
  }
}
