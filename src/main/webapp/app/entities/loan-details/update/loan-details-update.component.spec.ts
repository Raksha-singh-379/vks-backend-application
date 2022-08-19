import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LoanDetailsFormService } from './loan-details-form.service';
import { LoanDetailsService } from '../service/loan-details.service';
import { ILoanDetails } from '../loan-details.model';
import { ILoanDemand } from 'app/entities/loan-demand/loan-demand.model';
import { LoanDemandService } from 'app/entities/loan-demand/service/loan-demand.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { ISocietyLoanProduct } from 'app/entities/society-loan-product/society-loan-product.model';
import { SocietyLoanProductService } from 'app/entities/society-loan-product/service/society-loan-product.service';
import { IBankDhoranDetails } from 'app/entities/bank-dhoran-details/bank-dhoran-details.model';
import { BankDhoranDetailsService } from 'app/entities/bank-dhoran-details/service/bank-dhoran-details.service';

import { LoanDetailsUpdateComponent } from './loan-details-update.component';

describe('LoanDetails Management Update Component', () => {
  let comp: LoanDetailsUpdateComponent;
  let fixture: ComponentFixture<LoanDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let loanDetailsFormService: LoanDetailsFormService;
  let loanDetailsService: LoanDetailsService;
  let loanDemandService: LoanDemandService;
  let memberService: MemberService;
  let societyLoanProductService: SocietyLoanProductService;
  let bankDhoranDetailsService: BankDhoranDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LoanDetailsUpdateComponent],
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
      .overrideTemplate(LoanDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LoanDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    loanDetailsFormService = TestBed.inject(LoanDetailsFormService);
    loanDetailsService = TestBed.inject(LoanDetailsService);
    loanDemandService = TestBed.inject(LoanDemandService);
    memberService = TestBed.inject(MemberService);
    societyLoanProductService = TestBed.inject(SocietyLoanProductService);
    bankDhoranDetailsService = TestBed.inject(BankDhoranDetailsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call LoanDemand query and add missing value', () => {
      const loanDetails: ILoanDetails = { id: 456 };
      const loanDemand: ILoanDemand = { id: 68265 };
      loanDetails.loanDemand = loanDemand;
      const loanDemand: ILoanDemand = { id: 68665 };
      loanDetails.loanDemand = loanDemand;

      const loanDemandCollection: ILoanDemand[] = [{ id: 84379 }];
      jest.spyOn(loanDemandService, 'query').mockReturnValue(of(new HttpResponse({ body: loanDemandCollection })));
      const additionalLoanDemands = [loanDemand, loanDemand];
      const expectedCollection: ILoanDemand[] = [...additionalLoanDemands, ...loanDemandCollection];
      jest.spyOn(loanDemandService, 'addLoanDemandToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanDetails });
      comp.ngOnInit();

      expect(loanDemandService.query).toHaveBeenCalled();
      expect(loanDemandService.addLoanDemandToCollectionIfMissing).toHaveBeenCalledWith(
        loanDemandCollection,
        ...additionalLoanDemands.map(expect.objectContaining)
      );
      expect(comp.loanDemandsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Member query and add missing value', () => {
      const loanDetails: ILoanDetails = { id: 456 };
      const member: IMember = { id: 61109 };
      loanDetails.member = member;

      const memberCollection: IMember[] = [{ id: 14921 }];
      jest.spyOn(memberService, 'query').mockReturnValue(of(new HttpResponse({ body: memberCollection })));
      const additionalMembers = [member];
      const expectedCollection: IMember[] = [...additionalMembers, ...memberCollection];
      jest.spyOn(memberService, 'addMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanDetails });
      comp.ngOnInit();

      expect(memberService.query).toHaveBeenCalled();
      expect(memberService.addMemberToCollectionIfMissing).toHaveBeenCalledWith(
        memberCollection,
        ...additionalMembers.map(expect.objectContaining)
      );
      expect(comp.membersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SocietyLoanProduct query and add missing value', () => {
      const loanDetails: ILoanDetails = { id: 456 };
      const societyLoanProduct: ISocietyLoanProduct = { id: 39062 };
      loanDetails.societyLoanProduct = societyLoanProduct;

      const societyLoanProductCollection: ISocietyLoanProduct[] = [{ id: 87039 }];
      jest.spyOn(societyLoanProductService, 'query').mockReturnValue(of(new HttpResponse({ body: societyLoanProductCollection })));
      const additionalSocietyLoanProducts = [societyLoanProduct];
      const expectedCollection: ISocietyLoanProduct[] = [...additionalSocietyLoanProducts, ...societyLoanProductCollection];
      jest.spyOn(societyLoanProductService, 'addSocietyLoanProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanDetails });
      comp.ngOnInit();

      expect(societyLoanProductService.query).toHaveBeenCalled();
      expect(societyLoanProductService.addSocietyLoanProductToCollectionIfMissing).toHaveBeenCalledWith(
        societyLoanProductCollection,
        ...additionalSocietyLoanProducts.map(expect.objectContaining)
      );
      expect(comp.societyLoanProductsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call BankDhoranDetails query and add missing value', () => {
      const loanDetails: ILoanDetails = { id: 456 };
      const bankDhoranDetails: IBankDhoranDetails = { id: 96424 };
      loanDetails.bankDhoranDetails = bankDhoranDetails;

      const bankDhoranDetailsCollection: IBankDhoranDetails[] = [{ id: 93027 }];
      jest.spyOn(bankDhoranDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: bankDhoranDetailsCollection })));
      const additionalBankDhoranDetails = [bankDhoranDetails];
      const expectedCollection: IBankDhoranDetails[] = [...additionalBankDhoranDetails, ...bankDhoranDetailsCollection];
      jest.spyOn(bankDhoranDetailsService, 'addBankDhoranDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanDetails });
      comp.ngOnInit();

      expect(bankDhoranDetailsService.query).toHaveBeenCalled();
      expect(bankDhoranDetailsService.addBankDhoranDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        bankDhoranDetailsCollection,
        ...additionalBankDhoranDetails.map(expect.objectContaining)
      );
      expect(comp.bankDhoranDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const loanDetails: ILoanDetails = { id: 456 };
      const loanDemand: ILoanDemand = { id: 92523 };
      loanDetails.loanDemand = loanDemand;
      const loanDemand: ILoanDemand = { id: 7424 };
      loanDetails.loanDemand = loanDemand;
      const member: IMember = { id: 50744 };
      loanDetails.member = member;
      const societyLoanProduct: ISocietyLoanProduct = { id: 88052 };
      loanDetails.societyLoanProduct = societyLoanProduct;
      const bankDhoranDetails: IBankDhoranDetails = { id: 92658 };
      loanDetails.bankDhoranDetails = bankDhoranDetails;

      activatedRoute.data = of({ loanDetails });
      comp.ngOnInit();

      expect(comp.loanDemandsSharedCollection).toContain(loanDemand);
      expect(comp.loanDemandsSharedCollection).toContain(loanDemand);
      expect(comp.membersSharedCollection).toContain(member);
      expect(comp.societyLoanProductsSharedCollection).toContain(societyLoanProduct);
      expect(comp.bankDhoranDetailsSharedCollection).toContain(bankDhoranDetails);
      expect(comp.loanDetails).toEqual(loanDetails);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanDetails>>();
      const loanDetails = { id: 123 };
      jest.spyOn(loanDetailsFormService, 'getLoanDetails').mockReturnValue(loanDetails);
      jest.spyOn(loanDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanDetails }));
      saveSubject.complete();

      // THEN
      expect(loanDetailsFormService.getLoanDetails).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(loanDetailsService.update).toHaveBeenCalledWith(expect.objectContaining(loanDetails));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanDetails>>();
      const loanDetails = { id: 123 };
      jest.spyOn(loanDetailsFormService, 'getLoanDetails').mockReturnValue({ id: null });
      jest.spyOn(loanDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanDetails: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanDetails }));
      saveSubject.complete();

      // THEN
      expect(loanDetailsFormService.getLoanDetails).toHaveBeenCalled();
      expect(loanDetailsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanDetails>>();
      const loanDetails = { id: 123 };
      jest.spyOn(loanDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(loanDetailsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareLoanDemand', () => {
      it('Should forward to loanDemandService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(loanDemandService, 'compareLoanDemand');
        comp.compareLoanDemand(entity, entity2);
        expect(loanDemandService.compareLoanDemand).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareMember', () => {
      it('Should forward to memberService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(memberService, 'compareMember');
        comp.compareMember(entity, entity2);
        expect(memberService.compareMember).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareSocietyLoanProduct', () => {
      it('Should forward to societyLoanProductService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(societyLoanProductService, 'compareSocietyLoanProduct');
        comp.compareSocietyLoanProduct(entity, entity2);
        expect(societyLoanProductService.compareSocietyLoanProduct).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareBankDhoranDetails', () => {
      it('Should forward to bankDhoranDetailsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(bankDhoranDetailsService, 'compareBankDhoranDetails');
        comp.compareBankDhoranDetails(entity, entity2);
        expect(bankDhoranDetailsService.compareBankDhoranDetails).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
