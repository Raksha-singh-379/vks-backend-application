import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LoanDemandFormService, LoanDemandFormGroup } from './loan-demand-form.service';
import { ILoanDemand } from '../loan-demand.model';
import { LoanDemandService } from '../service/loan-demand.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { ISocietyLoanProduct } from 'app/entities/society-loan-product/society-loan-product.model';
import { SocietyLoanProductService } from 'app/entities/society-loan-product/service/society-loan-product.service';
import { IMemberLandAssets } from 'app/entities/member-land-assets/member-land-assets.model';
import { MemberLandAssetsService } from 'app/entities/member-land-assets/service/member-land-assets.service';
import { ISocietyCropRegistration } from 'app/entities/society-crop-registration/society-crop-registration.model';
import { SocietyCropRegistrationService } from 'app/entities/society-crop-registration/service/society-crop-registration.service';
import { LoanStatus } from 'app/entities/enumerations/loan-status.model';

@Component({
  selector: 'jhi-loan-demand-update',
  templateUrl: './loan-demand-update.component.html',
})
export class LoanDemandUpdateComponent implements OnInit {
  isSaving = false;
  loanDemand: ILoanDemand | null = null;
  loanStatusValues = Object.keys(LoanStatus);

  membersSharedCollection: IMember[] = [];
  societyLoanProductsSharedCollection: ISocietyLoanProduct[] = [];
  memberLandAssetsSharedCollection: IMemberLandAssets[] = [];
  societyCropRegistrationsSharedCollection: ISocietyCropRegistration[] = [];

  editForm: LoanDemandFormGroup = this.loanDemandFormService.createLoanDemandFormGroup();

  constructor(
    protected loanDemandService: LoanDemandService,
    protected loanDemandFormService: LoanDemandFormService,
    protected memberService: MemberService,
    protected societyLoanProductService: SocietyLoanProductService,
    protected memberLandAssetsService: MemberLandAssetsService,
    protected societyCropRegistrationService: SocietyCropRegistrationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMember = (o1: IMember | null, o2: IMember | null): boolean => this.memberService.compareMember(o1, o2);

  compareSocietyLoanProduct = (o1: ISocietyLoanProduct | null, o2: ISocietyLoanProduct | null): boolean =>
    this.societyLoanProductService.compareSocietyLoanProduct(o1, o2);

  compareMemberLandAssets = (o1: IMemberLandAssets | null, o2: IMemberLandAssets | null): boolean =>
    this.memberLandAssetsService.compareMemberLandAssets(o1, o2);

  compareSocietyCropRegistration = (o1: ISocietyCropRegistration | null, o2: ISocietyCropRegistration | null): boolean =>
    this.societyCropRegistrationService.compareSocietyCropRegistration(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanDemand }) => {
      this.loanDemand = loanDemand;
      if (loanDemand) {
        this.updateForm(loanDemand);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const loanDemand = this.loanDemandFormService.getLoanDemand(this.editForm);
    if (loanDemand.id !== null) {
      this.subscribeToSaveResponse(this.loanDemandService.update(loanDemand));
    } else {
      this.subscribeToSaveResponse(this.loanDemandService.create(loanDemand));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoanDemand>>): void {
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

  protected updateForm(loanDemand: ILoanDemand): void {
    this.loanDemand = loanDemand;
    this.loanDemandFormService.resetForm(this.editForm, loanDemand);

    this.membersSharedCollection = this.memberService.addMemberToCollectionIfMissing<IMember>(
      this.membersSharedCollection,
      loanDemand.member
    );
    this.societyLoanProductsSharedCollection =
      this.societyLoanProductService.addSocietyLoanProductToCollectionIfMissing<ISocietyLoanProduct>(
        this.societyLoanProductsSharedCollection,
        loanDemand.societyLoanProduct
      );
    this.memberLandAssetsSharedCollection = this.memberLandAssetsService.addMemberLandAssetsToCollectionIfMissing<IMemberLandAssets>(
      this.memberLandAssetsSharedCollection,
      loanDemand.memberLandAssets
    );
    this.societyCropRegistrationsSharedCollection =
      this.societyCropRegistrationService.addSocietyCropRegistrationToCollectionIfMissing<ISocietyCropRegistration>(
        this.societyCropRegistrationsSharedCollection,
        loanDemand.societyCropRegistration
      );
  }

  protected loadRelationshipsOptions(): void {
    this.memberService
      .query()
      .pipe(map((res: HttpResponse<IMember[]>) => res.body ?? []))
      .pipe(map((members: IMember[]) => this.memberService.addMemberToCollectionIfMissing<IMember>(members, this.loanDemand?.member)))
      .subscribe((members: IMember[]) => (this.membersSharedCollection = members));

    this.societyLoanProductService
      .query()
      .pipe(map((res: HttpResponse<ISocietyLoanProduct[]>) => res.body ?? []))
      .pipe(
        map((societyLoanProducts: ISocietyLoanProduct[]) =>
          this.societyLoanProductService.addSocietyLoanProductToCollectionIfMissing<ISocietyLoanProduct>(
            societyLoanProducts,
            this.loanDemand?.societyLoanProduct
          )
        )
      )
      .subscribe((societyLoanProducts: ISocietyLoanProduct[]) => (this.societyLoanProductsSharedCollection = societyLoanProducts));

    this.memberLandAssetsService
      .query()
      .pipe(map((res: HttpResponse<IMemberLandAssets[]>) => res.body ?? []))
      .pipe(
        map((memberLandAssets: IMemberLandAssets[]) =>
          this.memberLandAssetsService.addMemberLandAssetsToCollectionIfMissing<IMemberLandAssets>(
            memberLandAssets,
            this.loanDemand?.memberLandAssets
          )
        )
      )
      .subscribe((memberLandAssets: IMemberLandAssets[]) => (this.memberLandAssetsSharedCollection = memberLandAssets));

    this.societyCropRegistrationService
      .query()
      .pipe(map((res: HttpResponse<ISocietyCropRegistration[]>) => res.body ?? []))
      .pipe(
        map((societyCropRegistrations: ISocietyCropRegistration[]) =>
          this.societyCropRegistrationService.addSocietyCropRegistrationToCollectionIfMissing<ISocietyCropRegistration>(
            societyCropRegistrations,
            this.loanDemand?.societyCropRegistration
          )
        )
      )
      .subscribe(
        (societyCropRegistrations: ISocietyCropRegistration[]) => (this.societyCropRegistrationsSharedCollection = societyCropRegistrations)
      );
  }
}
