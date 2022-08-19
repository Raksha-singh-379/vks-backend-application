import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LoanDisbursementDetailsFormService } from './loan-disbursement-details-form.service';
import { LoanDisbursementDetailsService } from '../service/loan-disbursement-details.service';
import { ILoanDisbursementDetails } from '../loan-disbursement-details.model';
import { ILoanDetails } from 'app/entities/loan-details/loan-details.model';
import { LoanDetailsService } from 'app/entities/loan-details/service/loan-details.service';

import { LoanDisbursementDetailsUpdateComponent } from './loan-disbursement-details-update.component';

describe('LoanDisbursementDetails Management Update Component', () => {
  let comp: LoanDisbursementDetailsUpdateComponent;
  let fixture: ComponentFixture<LoanDisbursementDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let loanDisbursementDetailsFormService: LoanDisbursementDetailsFormService;
  let loanDisbursementDetailsService: LoanDisbursementDetailsService;
  let loanDetailsService: LoanDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LoanDisbursementDetailsUpdateComponent],
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
      .overrideTemplate(LoanDisbursementDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LoanDisbursementDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    loanDisbursementDetailsFormService = TestBed.inject(LoanDisbursementDetailsFormService);
    loanDisbursementDetailsService = TestBed.inject(LoanDisbursementDetailsService);
    loanDetailsService = TestBed.inject(LoanDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LoanDetails query and add missing value', () => {
      const loanDisbursementDetails: ILoanDisbursementDetails = { id: 456 };
      const loanDetails: ILoanDetails = { id: 9957 };
      loanDisbursementDetails.loanDetails = loanDetails;

      const loanDetailsCollection: ILoanDetails[] = [{ id: 98292 }];
      jest.spyOn(loanDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: loanDetailsCollection })));
      const additionalLoanDetails = [loanDetails];
      const expectedCollection: ILoanDetails[] = [...additionalLoanDetails, ...loanDetailsCollection];
      jest.spyOn(loanDetailsService, 'addLoanDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanDisbursementDetails });
      comp.ngOnInit();

      expect(loanDetailsService.query).toHaveBeenCalled();
      expect(loanDetailsService.addLoanDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        loanDetailsCollection,
        ...additionalLoanDetails.map(expect.objectContaining)
      );
      expect(comp.loanDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const loanDisbursementDetails: ILoanDisbursementDetails = { id: 456 };
      const loanDetails: ILoanDetails = { id: 34319 };
      loanDisbursementDetails.loanDetails = loanDetails;

      activatedRoute.data = of({ loanDisbursementDetails });
      comp.ngOnInit();

      expect(comp.loanDetailsSharedCollection).toContain(loanDetails);
      expect(comp.loanDisbursementDetails).toEqual(loanDisbursementDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanDisbursementDetails>>();
      const loanDisbursementDetails = { id: 123 };
      jest.spyOn(loanDisbursementDetailsFormService, 'getLoanDisbursementDetails').mockReturnValue(loanDisbursementDetails);
      jest.spyOn(loanDisbursementDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanDisbursementDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanDisbursementDetails }));
      saveSubject.complete();

      // THEN
      expect(loanDisbursementDetailsFormService.getLoanDisbursementDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(loanDisbursementDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(loanDisbursementDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanDisbursementDetails>>();
      const loanDisbursementDetails = { id: 123 };
      jest.spyOn(loanDisbursementDetailsFormService, 'getLoanDisbursementDetails').mockReturnValue({ id: null });
      jest.spyOn(loanDisbursementDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanDisbursementDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanDisbursementDetails }));
      saveSubject.complete();

      // THEN
      expect(loanDisbursementDetailsFormService.getLoanDisbursementDetails).toHaveBeenCalled();
      expect(loanDisbursementDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanDisbursementDetails>>();
      const loanDisbursementDetails = { id: 123 };
      jest.spyOn(loanDisbursementDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanDisbursementDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(loanDisbursementDetailsService.update).toHaveBeenCalled();
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
