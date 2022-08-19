import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AccountMappingFormService, AccountMappingFormGroup } from './account-mapping-form.service';
import { IAccountMapping } from '../account-mapping.model';
import { AccountMappingService } from '../service/account-mapping.service';
import { ILedgerAccounts } from 'app/entities/ledger-accounts/ledger-accounts.model';
import { LedgerAccountsService } from 'app/entities/ledger-accounts/service/ledger-accounts.service';
import { MappingType } from 'app/entities/enumerations/mapping-type.model';

@Component({
  selector: 'jhi-account-mapping-update',
  templateUrl: './account-mapping-update.component.html',
})
export class AccountMappingUpdateComponent implements OnInit {
  isSaving = false;
  accountMapping: IAccountMapping | null = null;
  mappingTypeValues = Object.keys(MappingType);

  ledgerAccountsSharedCollection: ILedgerAccounts[] = [];

  editForm: AccountMappingFormGroup = this.accountMappingFormService.createAccountMappingFormGroup();

  constructor(
    protected accountMappingService: AccountMappingService,
    protected accountMappingFormService: AccountMappingFormService,
    protected ledgerAccountsService: LedgerAccountsService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareLedgerAccounts = (o1: ILedgerAccounts | null, o2: ILedgerAccounts | null): boolean =>
    this.ledgerAccountsService.compareLedgerAccounts(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accountMapping }) => {
      this.accountMapping = accountMapping;
      if (accountMapping) {
        this.updateForm(accountMapping);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accountMapping = this.accountMappingFormService.getAccountMapping(this.editForm);
    if (accountMapping.id !== null) {
      this.subscribeToSaveResponse(this.accountMappingService.update(accountMapping));
    } else {
      this.subscribeToSaveResponse(this.accountMappingService.create(accountMapping));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountMapping>>): void {
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

  protected updateForm(accountMapping: IAccountMapping): void {
    this.accountMapping = accountMapping;
    this.accountMappingFormService.resetForm(this.editForm, accountMapping);

    this.ledgerAccountsSharedCollection = this.ledgerAccountsService.addLedgerAccountsToCollectionIfMissing<ILedgerAccounts>(
      this.ledgerAccountsSharedCollection,
      accountMapping.accountMapping
    );
  }

  protected loadRelationshipsOptions(): void {
    this.ledgerAccountsService
      .query()
      .pipe(map((res: HttpResponse<ILedgerAccounts[]>) => res.body ?? []))
      .pipe(
        map((ledgerAccounts: ILedgerAccounts[]) =>
          this.ledgerAccountsService.addLedgerAccountsToCollectionIfMissing<ILedgerAccounts>(
            ledgerAccounts,
            this.accountMapping?.accountMapping
          )
        )
      )
      .subscribe((ledgerAccounts: ILedgerAccounts[]) => (this.ledgerAccountsSharedCollection = ledgerAccounts));
  }
}
