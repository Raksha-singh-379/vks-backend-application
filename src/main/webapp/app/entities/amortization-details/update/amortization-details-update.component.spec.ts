import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AmortizationDetailsFormService } from './amortization-details-form.service';
import { AmortizationDetailsService } from '../service/amortization-details.service';
import { IAmortizationDetails } from '../amortization-details.model';
import { ILoanDetails } from 'app/entities/loan-details/loan-details.model';
import { LoanDetailsService } from 'app/entities/loan-details/service/loan-details.service';

import { AmortizationDetailsUpdateComponent } from './amortization-details-update.component';

describe('AmortizationDetails Management Update Component', () => {
  let comp: AmortizationDetailsUpdateComponent;
  let fixture: ComponentFixture<AmortizationDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let amortizationDetailsFormService: AmortizationDetailsFormService;
  let amortizationDetailsService: AmortizationDetailsService;
  let loanDetailsService: LoanDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AmortizationDetailsUpdateComponent],
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
      .overrideTemplate(AmortizationDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AmortizationDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    amortizationDetailsFormService = TestBed.inject(AmortizationDetailsFormService);
    amortizationDetailsService = TestBed.inject(AmortizationDetailsService);
    loanDetailsService = TestBed.inject(LoanDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LoanDetails query and add missing value', () => {
      const amortizationDetails: IAmortizationDetails = { id: 456 };
      const loanDetails: ILoanDetails = { id: 10654 };
      amortizationDetails.loanDetails = loanDetails;

      const loanDetailsCollection: ILoanDetails[] = [{ id: 18838 }];
      jest.spyOn(loanDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: loanDetailsCollection })));
      const additionalLoanDetails = [loanDetails];
      const expectedCollection: ILoanDetails[] = [...additionalLoanDetails, ...loanDetailsCollection];
      jest.spyOn(loanDetailsService, 'addLoanDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ amortizationDetails });
      comp.ngOnInit();

      expect(loanDetailsService.query).toHaveBeenCalled();
      expect(loanDetailsService.addLoanDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        loanDetailsCollection,
        ...additionalLoanDetails.map(expect.objectContaining)
      );
      expect(comp.loanDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const amortizationDetails: IAmortizationDetails = { id: 456 };
      const loanDetails: ILoanDetails = { id: 75037 };
      amortizationDetails.loanDetails = loanDetails;

      activatedRoute.data = of({ amortizationDetails });
      comp.ngOnInit();

      expect(comp.loanDetailsSharedCollection).toContain(loanDetails);
      expect(comp.amortizationDetails).toEqual(amortizationDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAmortizationDetails>>();
      const amortizationDetails = { id: 123 };
      jest.spyOn(amortizationDetailsFormService, 'getAmortizationDetails').mockReturnValue(amortizationDetails);
      jest.spyOn(amortizationDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ amortizationDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: amortizationDetails }));
      saveSubject.complete();

      // THEN
      expect(amortizationDetailsFormService.getAmortizationDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(amortizationDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(amortizationDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAmortizationDetails>>();
      const amortizationDetails = { id: 123 };
      jest.spyOn(amortizationDetailsFormService, 'getAmortizationDetails').mockReturnValue({ id: null });
      jest.spyOn(amortizationDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ amortizationDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: amortizationDetails }));
      saveSubject.complete();

      // THEN
      expect(amortizationDetailsFormService.getAmortizationDetails).toHaveBeenCalled();
      expect(amortizationDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAmortizationDetails>>();
      const amortizationDetails = { id: 123 };
      jest.spyOn(amortizationDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ amortizationDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(amortizationDetailsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLoanDetails', () => {
      it('Should forward to loanDetailsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(loanDetailsService, 'compareLoanDetails');
        comp.compareLoanDetails(entity, entity2);
        expect(loanDetailsService.compareLoanDetails).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
