import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AccountMappingFormService } from './account-mapping-form.service';
import { AccountMappingService } from '../service/account-mapping.service';
import { IAccountMapping } from '../account-mapping.model';
import { ILedgerAccounts } from 'app/entities/ledger-accounts/ledger-accounts.model';
import { LedgerAccountsService } from 'app/entities/ledger-accounts/service/ledger-accounts.service';

import { AccountMappingUpdateComponent } from './account-mapping-update.component';

describe('AccountMapping Management Update Component', () => {
  let comp: AccountMappingUpdateComponent;
  let fixture: ComponentFixture<AccountMappingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let accountMappingFormService: AccountMappingFormService;
  let accountMappingService: AccountMappingService;
  let ledgerAccountsService: LedgerAccountsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AccountMappingUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AccountMappingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AccountMappingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    accountMappingFormService = TestBed.inject(AccountMappingFormService);
    accountMappingService = TestBed.inject(AccountMappingService);
    ledgerAccountsService = TestBed.inject(LedgerAccountsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LedgerAccounts query and add missing value', () => {
      const accountMapping: IAccountMapping = { id: 456 };
      const accountMapping: ILedgerAccounts = { id: 34123 };
      accountMapping.accountMapping = accountMapping;

      const ledgerAccountsCollection: ILedgerAccounts[] = [{ id: 65999 }];
      jest.spyOn(ledgerAccountsService, 'query').mockReturnValue(of(new HttpResponse({ body: ledgerAccountsCollection })));
      const additionalLedgerAccounts = [accountMapping];
      const expectedCollection: ILedgerAccounts[] = [...additionalLedgerAccounts, ...ledgerAccountsCollection];
      jest.spyOn(ledgerAccountsService, 'addLedgerAccountsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ accountMapping });
      comp.ngOnInit();

      expect(ledgerAccountsService.query).toHaveBeenCalled();
      expect(ledgerAccountsService.addLedgerAccountsToCollectionIfMissing).toHaveBeenCalledWith(
        ledgerAccountsCollection,
        ...additionalLedgerAccounts.map(expect.objectContaining)
      );
      expect(comp.ledgerAccountsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const accountMapping: IAccountMapping = { id: 456 };
      const accountMapping: ILedgerAccounts = { id: 30546 };
      accountMapping.accountMapping = accountMapping;

      activatedRoute.data = of({ accountMapping });
      comp.ngOnInit();

      expect(comp.ledgerAccountsSharedCollection).toContain(accountMapping);
      expect(comp.accountMapping).toEqual(accountMapping);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccountMapping>>();
      const accountMapping = { id: 123 };
      jest.spyOn(accountMappingFormService, 'getAccountMapping').mockReturnValue(accountMapping);
      jest.spyOn(accountMappingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accountMapping });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: accountMapping }));
      saveSubject.complete();

      // THEN
      expect(accountMappingFormService.getAccountMapping).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(accountMappingService.update).toHaveBeenCalledWith(expect.objectContaining(accountMapping));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccountMapping>>();
      const accountMapping = { id: 123 };
      jest.spyOn(accountMappingFormService, 'getAccountMapping').mockReturnValue({ id: null });
      jest.spyOn(accountMappingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accountMapping: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: accountMapping }));
      saveSubject.complete();

      // THEN
      expect(accountMappingFormService.getAccountMapping).toHaveBeenCalled();
      expect(accountMappingService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAccountMapping>>();
      const accountMapping = { id: 123 };
      jest.spyOn(accountMappingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ accountMapping });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(accountMappingService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLedgerAccounts', () => {
      it('Should forward to ledgerAccountsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(ledgerAccountsService, 'compareLedgerAccounts');
        comp.compareLedgerAccounts(entity, entity2);
        expect(ledgerAccountsService.compareLedgerAccounts).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
