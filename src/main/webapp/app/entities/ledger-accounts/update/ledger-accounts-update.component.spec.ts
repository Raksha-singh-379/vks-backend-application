import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LedgerAccountsFormService } from './ledger-accounts-form.service';
import { LedgerAccountsService } from '../service/ledger-accounts.service';
import { ILedgerAccounts } from '../ledger-accounts.model';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';

import { LedgerAccountsUpdateComponent } from './ledger-accounts-update.component';

describe('LedgerAccounts Management Update Component', () => {
  let comp: LedgerAccountsUpdateComponent;
  let fixture: ComponentFixture<LedgerAccountsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let ledgerAccountsFormService: LedgerAccountsFormService;
  let ledgerAccountsService: LedgerAccountsService;
  let societyService: SocietyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LedgerAccountsUpdateComponent],
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
      .overrideTemplate(LedgerAccountsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LedgerAccountsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    ledgerAccountsFormService = TestBed.inject(LedgerAccountsFormService);
    ledgerAccountsService = TestBed.inject(LedgerAccountsService);
    societyService = TestBed.inject(SocietyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Society query and add missing value', () => {
      const ledgerAccounts: ILedgerAccounts = { id: 456 };
      const society: ISociety = { id: 28810 };
      ledgerAccounts.society = society;

      const societyCollection: ISociety[] = [{ id: 95945 }];
      jest.spyOn(societyService, 'query').mockReturnValue(of(new HttpResponse({ body: societyCollection })));
      const additionalSocieties = [society];
      const expectedCollection: ISociety[] = [...additionalSocieties, ...societyCollection];
      jest.spyOn(societyService, 'addSocietyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ledgerAccounts });
      comp.ngOnInit();

      expect(societyService.query).toHaveBeenCalled();
      expect(societyService.addSocietyToCollectionIfMissing).toHaveBeenCalledWith(
        societyCollection,
        ...additionalSocieties.map(expect.objectContaining)
      );
      expect(comp.societiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call LedgerAccounts query and add missing value', () => {
      const ledgerAccounts: ILedgerAccounts = { id: 456 };
      const ledgerAccounts: ILedgerAccounts = { id: 30879 };
      ledgerAccounts.ledgerAccounts = ledgerAccounts;

      const ledgerAccountsCollection: ILedgerAccounts[] = [{ id: 43994 }];
      jest.spyOn(ledgerAccountsService, 'query').mockReturnValue(of(new HttpResponse({ body: ledgerAccountsCollection })));
      const additionalLedgerAccounts = [ledgerAccounts];
      const expectedCollection: ILedgerAccounts[] = [...additionalLedgerAccounts, ...ledgerAccountsCollection];
      jest.spyOn(ledgerAccountsService, 'addLedgerAccountsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ ledgerAccounts });
      comp.ngOnInit();

      expect(ledgerAccountsService.query).toHaveBeenCalled();
      expect(ledgerAccountsService.addLedgerAccountsToCollectionIfMissing).toHaveBeenCalledWith(
        ledgerAccountsCollection,
        ...additionalLedgerAccounts.map(expect.objectContaining)
      );
      expect(comp.ledgerAccountsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const ledgerAccounts: ILedgerAccounts = { id: 456 };
      const society: ISociety = { id: 4684 };
      ledgerAccounts.society = society;
      const ledgerAccounts: ILedgerAccounts = { id: 95378 };
      ledgerAccounts.ledgerAccounts = ledgerAccounts;

      activatedRoute.data = of({ ledgerAccounts });
      comp.ngOnInit();

      expect(comp.societiesSharedCollection).toContain(society);
      expect(comp.ledgerAccountsSharedCollection).toContain(ledgerAccounts);
      expect(comp.ledgerAccounts).toEqual(ledgerAccounts);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILedgerAccounts>>();
      const ledgerAccounts = { id: 123 };
      jest.spyOn(ledgerAccountsFormService, 'getLedgerAccounts').mockReturnValue(ledgerAccounts);
      jest.spyOn(ledgerAccountsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ledgerAccounts });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ledgerAccounts }));
      saveSubject.complete();

      // THEN
      expect(ledgerAccountsFormService.getLedgerAccounts).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(ledgerAccountsService.update).toHaveBeenCalledWith(expect.objectContaining(ledgerAccounts));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILedgerAccounts>>();
      const ledgerAccounts = { id: 123 };
      jest.spyOn(ledgerAccountsFormService, 'getLedgerAccounts').mockReturnValue({ id: null });
      jest.spyOn(ledgerAccountsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ledgerAccounts: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: ledgerAccounts }));
      saveSubject.complete();

      // THEN
      expect(ledgerAccountsFormService.getLedgerAccounts).toHaveBeenCalled();
      expect(ledgerAccountsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILedgerAccounts>>();
      const ledgerAccounts = { id: 123 };
      jest.spyOn(ledgerAccountsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ ledgerAccounts });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(ledgerAccountsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSociety', () => {
      it('Should forward to societyService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(societyService, 'compareSociety');
        comp.compareSociety(entity, entity2);
        expect(societyService.compareSociety).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
