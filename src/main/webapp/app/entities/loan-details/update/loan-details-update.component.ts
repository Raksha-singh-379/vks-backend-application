import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { LoanDetailsFormService, LoanDetailsFormGroup } from './loan-details-form.service';
import { ILoanDetails } from '../loan-details.model';
import { LoanDetailsService } from '../service/loan-details.service';
import { ILoanDemand } from 'app/entities/loan-demand/loan-demand.model';
import { LoanDemandService } from 'app/entities/loan-demand/service/loan-demand.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { ISocietyLoanProduct } from 'app/entities/society-loan-product/society-loan-product.model';
import { SocietyLoanProductService } from 'app/entities/society-loan-product/service/society-loan-product.service';
import { IBankDhoranDetails } from 'app/entities/bank-dhoran-details/bank-dhoran-details.model';
import { BankDhoranDetailsService } from 'app/entities/bank-dhoran-details/service/bank-dhoran-details.service';
import { LoanType } from 'app/entities/enumerations/loan-type.model';
import { LoanStatus } from 'app/entities/enumerations/loan-status.model';
import { NpaClassification } from 'app/entities/enumerations/npa-classification.model';

@Component({
  selector: 'jhi-loan-details-update',
  templateUrl: './loan-details-update.component.html',
})
export class LoanDetailsUpdateComponent implements OnInit {
  isSaving = false;
  loanDetails: ILoanDetails | null = null;
  loanTypeValues = Object.keys(LoanType);
  loanStatusValues = Object.keys(LoanStatus);
  npaClassificationValues = Object.keys(NpaClassification);

  loanDemandsSharedCollection: ILoanDemand[] = [];
  membersSharedCollection: IMember[] = [];
  societyLoanProductsSharedCollection: ISocietyLoanProduct[] = [];
  bankDhoranDetailsSharedCollection: IBankDhoranDetails[] = [];

  editForm: LoanDetailsFormGroup = this.loanDetailsFormService.createLoanDetailsFormGroup();

  constructor(
    protected loanDetailsService: LoanDetailsService,
    protected loanDetailsFormService: LoanDetailsFormService,
    protected loanDemandService: LoanDemandService,
    protected memberService: MemberService,
    protected societyLoanProductService: SocietyLoanProductService,
    protected bankDhoranDetailsService: BankDhoranDetailsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLoanDemand = (o1: ILoanDemand | null, o2: ILoanDemand | null): boolean => this.loanDemandService.compareLoanDemand(o1, o2);

  compareMember = (o1: IMember | null, o2: IMember | null): boolean => this.memberService.compareMember(o1, o2);

  compareSocietyLoanProduct = (o1: ISocietyLoanProduct | null, o2: ISocietyLoanProduct | null): boolean =>
    this.societyLoanProductService.compareSocietyLoanProduct(o1, o2);

  compareBankDhoranDetails = (o1: IBankDhoranDetails | null, o2: IBankDhoranDetails | null): boolean =>
    this.bankDhoranDetailsService.compareBankDhoranDetails(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ loanDetails }) => {
      this.loanDetails = loanDetails;
      if (loanDetails) {
        this.updateForm(loanDetails);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const loanDetails = this.loanDetailsFormService.getLoanDetails(this.editForm);
    if (loanDetails.id !== null) {
      this.subscribeToSaveResponse(this.loanDetailsService.update(loanDetails));
    } else {
      this.subscribeToSaveResponse(this.loanDetailsService.create(loanDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoanDetails>>): void {
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

  protected updateForm(loanDetails: ILoanDetails): void {
    this.loanDetails = loanDetails;
    this.loanDetailsFormService.resetForm(this.editForm, loanDetails);

    this.loanDemandsSharedCollection = this.loanDemandService.addLoanDemandToCollectionIfMissing<ILoanDemand>(
      this.loanDemandsSharedCollection,
      loanDetails.loanDemand,
      loanDetails.loanDemand
    );
    this.membersSharedCollection = this.memberService.addMemberToCollectionIfMissing<IMember>(
      this.membersSharedCollection,
      loanDetails.member
    );
    this.societyLoanProductsSharedCollection =
      this.societyLoanProductService.addSocietyLoanProductToCollectionIfMissing<ISocietyLoanProduct>(
        this.societyLoanProductsSharedCollection,
        loanDetails.societyLoanProduct
      );
    this.bankDhoranDetailsSharedCollection = this.bankDhoranDetailsService.addBankDhoranDetailsToCollectionIfMissing<IBankDhoranDetails>(
      this.bankDhoranDetailsSharedCollection,
      loanDetails.bankDhoranDetails
    );
  }

  protected loadRelationshipsOptions(): void {
    this.loanDemandService
      .query()
      .pipe(map((res: HttpResponse<ILoanDemand[]>) => res.body ?? []))
      .pipe(
        map((loanDemands: ILoanDemand[]) =>
          this.loanDemandService.addLoanDemandToCollectionIfMissing<ILoanDemand>(
            loanDemands,
            this.loanDetails?.loanDemand,
            this.loanDetails?.loanDemand
          )
        )
      )
      .subscribe((loanDemands: ILoanDemand[]) => (this.loanDemandsSharedCollection = loanDemands));

    this.memberService
      .query()
      .pipe(map((res: HttpResponse<IMember[]>) => res.body ?? []))
      .pipe(map((members: IMember[]) => this.memberService.addMemberToCollectionIfMissing<IMember>(members, this.loanDetails?.member)))
      .subscribe((members: IMember[]) => (this.membersSharedCollection = members));

    this.societyLoanProductService
      .query()
      .pipe(map((res: HttpResponse<ISocietyLoanProduct[]>) => res.body ?? []))
      .pipe(
        map((societyLoanProducts: ISocietyLoanProduct[]) =>
          this.societyLoanProductService.addSocietyLoanProductToCollectionIfMissing<ISocietyLoanProduct>(
            societyLoanProducts,
            this.loanDetails?.societyLoanProduct
          )
        )
      )
      .subscribe((societyLoanProducts: ISocietyLoanProduct[]) => (this.societyLoanProductsSharedCollection = societyLoanProducts));

    this.bankDhoranDetailsService
      .query()
      .pipe(map((res: HttpResponse<IBankDhoranDetails[]>) => res.body ?? []))
      .pipe(
        map((bankDhoranDetails: IBankDhoranDetails[]) =>
          this.bankDhoranDetailsService.addBankDhoranDetailsToCollectionIfMissing<IBankDhoranDetails>(
            bankDhoranDetails,
            this.loanDetails?.bankDhoranDetails
          )
        )
      )
      .subscribe((bankDhoranDetails: IBankDhoranDetails[]) => (this.bankDhoranDetailsSharedCollection = bankDhoranDetails));
  }
}
