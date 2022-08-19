import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BankDhoranDetailsFormService } from './bank-dhoran-details-form.service';
import { BankDhoranDetailsService } from '../service/bank-dhoran-details.service';
import { IBankDhoranDetails } from '../bank-dhoran-details.model';
import { ISociety } from 'app/entities/society/society.model';
import { SocietyService } from 'app/entities/society/service/society.service';

import { BankDhoranDetailsUpdateComponent } from './bank-dhoran-details-update.component';

describe('BankDhoranDetails Management Update Component', () => {
  let comp: BankDhoranDetailsUpdateComponent;
  let fixture: ComponentFixture<BankDhoranDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bankDhoranDetailsFormService: BankDhoranDetailsFormService;
  let bankDhoranDetailsService: BankDhoranDetailsService;
  let societyService: SocietyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BankDhoranDetailsUpdateComponent],
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
      .overrideTemplate(BankDhoranDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BankDhoranDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bankDhoranDetailsFormService = TestBed.inject(BankDhoranDetailsFormService);
    bankDhoranDetailsService = TestBed.inject(BankDhoranDetailsService);
    societyService = TestBed.inject(SocietyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Society query and add missing value', () => {
      const bankDhoranDetails: IBankDhoranDetails = { id: 456 };
      const society: ISociety = { id: 66877 };
      bankDhoranDetails.society = society;

      const societyCollection: ISociety[] = [{ id: 61906 }];
      jest.spyOn(societyService, 'query').mockReturnValue(of(new HttpResponse({ body: societyCollection })));
      const additionalSocieties = [society];
      const expectedCollection: ISociety[] = [...additionalSocieties, ...societyCollection];
      jest.spyOn(societyService, 'addSocietyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ bankDhoranDetails });
      comp.ngOnInit();

      expect(societyService.query).toHaveBeenCalled();
      expect(societyService.addSocietyToCollectionIfMissing).toHaveBeenCalledWith(
        societyCollection,
        ...additionalSocieties.map(expect.objectContaining)
      );
      expect(comp.societiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const bankDhoranDetails: IBankDhoranDetails = { id: 456 };
      const society: ISociety = { id: 68706 };
      bankDhoranDetails.society = society;

      activatedRoute.data = of({ bankDhoranDetails });
      comp.ngOnInit();

      expect(comp.societiesSharedCollection).toContain(society);
      expect(comp.bankDhoranDetails).toEqual(bankDhoranDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBankDhoranDetails>>();
      const bankDhoranDetails = { id: 123 };
      jest.spyOn(bankDhoranDetailsFormService, 'getBankDhoranDetails').mockReturnValue(bankDhoranDetails);
      jest.spyOn(bankDhoranDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bankDhoranDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bankDhoranDetails }));
      saveSubject.complete();

      // THEN
      expect(bankDhoranDetailsFormService.getBankDhoranDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(bankDhoranDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(bankDhoranDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBankDhoranDetails>>();
      const bankDhoranDetails = { id: 123 };
      jest.spyOn(bankDhoranDetailsFormService, 'getBankDhoranDetails').mockReturnValue({ id: null });
      jest.spyOn(bankDhoranDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bankDhoranDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bankDhoranDetails }));
      saveSubject.complete();

      // THEN
      expect(bankDhoranDetailsFormService.getBankDhoranDetails).toHaveBeenCalled();
      expect(bankDhoranDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBankDhoranDetails>>();
      const bankDhoranDetails = { id: 123 };
      jest.spyOn(bankDhoranDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bankDhoranDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bankDhoranDetailsService.update).toHaveBeenCalled();
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
  });
});
