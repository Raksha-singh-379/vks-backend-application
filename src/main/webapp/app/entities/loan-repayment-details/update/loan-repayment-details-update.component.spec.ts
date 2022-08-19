import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LoanRepaymentDetailsFormService } from './loan-repayment-details-form.service';
import { LoanRepaymentDetailsService } from '../service/loan-repayment-details.service';
import { ILoanRepaymentDetails } from '../loan-repayment-details.model';
import { ILoanDetails } from 'app/entities/loan-details/loan-details.model';
import { LoanDetailsService } from 'app/entities/loan-details/service/loan-details.service';

import { LoanRepaymentDetailsUpdateComponent } from './loan-repayment-details-update.component';

describe('LoanRepaymentDetails Management Update Component', () => {
  let comp: LoanRepaymentDetailsUpdateComponent;
  let fixture: ComponentFixture<LoanRepaymentDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let loanRepaymentDetailsFormService: LoanRepaymentDetailsFormService;
  let loanRepaymentDetailsService: LoanRepaymentDetailsService;
  let loanDetailsService: LoanDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LoanRepaymentDetailsUpdateComponent],
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
      .overrideTemplate(LoanRepaymentDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LoanRepaymentDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    loanRepaymentDetailsFormService = TestBed.inject(LoanRepaymentDetailsFormService);
    loanRepaymentDetailsService = TestBed.inject(LoanRepaymentDetailsService);
    loanDetailsService = TestBed.inject(LoanDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LoanDetails query and add missing value', () => {
      const loanRepaymentDetails: ILoanRepaymentDetails = { id: 456 };
      const loanDetails: ILoanDetails = { id: 39423 };
      loanRepaymentDetails.loanDetails = loanDetails;

      const loanDetailsCollection: ILoanDetails[] = [{ id: 64266 }];
      jest.spyOn(loanDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: loanDetailsCollection })));
      const additionalLoanDetails = [loanDetails];
      const expectedCollection: ILoanDetails[] = [...additionalLoanDetails, ...loanDetailsCollection];
      jest.spyOn(loanDetailsService, 'addLoanDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanRepaymentDetails });
      comp.ngOnInit();

      expect(loanDetailsService.query).toHaveBeenCalled();
      expect(loanDetailsService.addLoanDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        loanDetailsCollection,
        ...additionalLoanDetails.map(expect.objectContaining)
      );
      expect(comp.loanDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const loanRepaymentDetails: ILoanRepaymentDetails = { id: 456 };
      const loanDetails: ILoanDetails = { id: 12617 };
      loanRepaymentDetails.loanDetails = loanDetails;

      activatedRoute.data = of({ loanRepaymentDetails });
      comp.ngOnInit();

      expect(comp.loanDetailsSharedCollection).toContain(loanDetails);
      expect(comp.loanRepaymentDetails).toEqual(loanRepaymentDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanRepaymentDetails>>();
      const loanRepaymentDetails = { id: 123 };
      jest.spyOn(loanRepaymentDetailsFormService, 'getLoanRepaymentDetails').mockReturnValue(loanRepaymentDetails);
      jest.spyOn(loanRepaymentDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanRepaymentDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanRepaymentDetails }));
      saveSubject.complete();

      // THEN
      expect(loanRepaymentDetailsFormService.getLoanRepaymentDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(loanRepaymentDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(loanRepaymentDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanRepaymentDetails>>();
      const loanRepaymentDetails = { id: 123 };
      jest.spyOn(loanRepaymentDetailsFormService, 'getLoanRepaymentDetails').mockReturnValue({ id: null });
      jest.spyOn(loanRepaymentDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanRepaymentDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanRepaymentDetails }));
      saveSubject.complete();

      // THEN
      expect(loanRepaymentDetailsFormService.getLoanRepaymentDetails).toHaveBeenCalled();
      expect(loanRepaymentDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanRepaymentDetails>>();
      const loanRepaymentDetails = { id: 123 };
      jest.spyOn(loanRepaymentDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanRepaymentDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(loanRepaymentDetailsService.update).toHaveBeenCalled();
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
