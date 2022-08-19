import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LoanDemandFormService } from './loan-demand-form.service';
import { LoanDemandService } from '../service/loan-demand.service';
import { ILoanDemand } from '../loan-demand.model';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';
import { ISocietyLoanProduct } from 'app/entities/society-loan-product/society-loan-product.model';
import { SocietyLoanProductService } from 'app/entities/society-loan-product/service/society-loan-product.service';
import { IMemberLandAssets } from 'app/entities/member-land-assets/member-land-assets.model';
import { MemberLandAssetsService } from 'app/entities/member-land-assets/service/member-land-assets.service';
import { ISocietyCropRegistration } from 'app/entities/society-crop-registration/society-crop-registration.model';
import { SocietyCropRegistrationService } from 'app/entities/society-crop-registration/service/society-crop-registration.service';

import { LoanDemandUpdateComponent } from './loan-demand-update.component';

describe('LoanDemand Management Update Component', () => {
  let comp: LoanDemandUpdateComponent;
  let fixture: ComponentFixture<LoanDemandUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let loanDemandFormService: LoanDemandFormService;
  let loanDemandService: LoanDemandService;
  let memberService: MemberService;
  let societyLoanProductService: SocietyLoanProductService;
  let memberLandAssetsService: MemberLandAssetsService;
  let societyCropRegistrationService: SocietyCropRegistrationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LoanDemandUpdateComponent],
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
      .overrideTemplate(LoanDemandUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LoanDemandUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    loanDemandFormService = TestBed.inject(LoanDemandFormService);
    loanDemandService = TestBed.inject(LoanDemandService);
    memberService = TestBed.inject(MemberService);
    societyLoanProductService = TestBed.inject(SocietyLoanProductService);
    memberLandAssetsService = TestBed.inject(MemberLandAssetsService);
    societyCropRegistrationService = TestBed.inject(SocietyCropRegistrationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Member query and add missing value', () => {
      const loanDemand: ILoanDemand = { id: 456 };
      const member: IMember = { id: 83459 };
      loanDemand.member = member;

      const memberCollection: IMember[] = [{ id: 68662 }];
      jest.spyOn(memberService, 'query').mockReturnValue(of(new HttpResponse({ body: memberCollection })));
      const additionalMembers = [member];
      const expectedCollection: IMember[] = [...additionalMembers, ...memberCollection];
      jest.spyOn(memberService, 'addMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanDemand });
      comp.ngOnInit();

      expect(memberService.query).toHaveBeenCalled();
      expect(memberService.addMemberToCollectionIfMissing).toHaveBeenCalledWith(
        memberCollection,
        ...additionalMembers.map(expect.objectContaining)
      );
      expect(comp.membersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SocietyLoanProduct query and add missing value', () => {
      const loanDemand: ILoanDemand = { id: 456 };
      const societyLoanProduct: ISocietyLoanProduct = { id: 47266 };
      loanDemand.societyLoanProduct = societyLoanProduct;

      const societyLoanProductCollection: ISocietyLoanProduct[] = [{ id: 88292 }];
      jest.spyOn(societyLoanProductService, 'query').mockReturnValue(of(new HttpResponse({ body: societyLoanProductCollection })));
      const additionalSocietyLoanProducts = [societyLoanProduct];
      const expectedCollection: ISocietyLoanProduct[] = [...additionalSocietyLoanProducts, ...societyLoanProductCollection];
      jest.spyOn(societyLoanProductService, 'addSocietyLoanProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanDemand });
      comp.ngOnInit();

      expect(societyLoanProductService.query).toHaveBeenCalled();
      expect(societyLoanProductService.addSocietyLoanProductToCollectionIfMissing).toHaveBeenCalledWith(
        societyLoanProductCollection,
        ...additionalSocietyLoanProducts.map(expect.objectContaining)
      );
      expect(comp.societyLoanProductsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call MemberLandAssets query and add missing value', () => {
      const loanDemand: ILoanDemand = { id: 456 };
      const memberLandAssets: IMemberLandAssets = { id: 32651 };
      loanDemand.memberLandAssets = memberLandAssets;

      const memberLandAssetsCollection: IMemberLandAssets[] = [{ id: 69507 }];
      jest.spyOn(memberLandAssetsService, 'query').mockReturnValue(of(new HttpResponse({ body: memberLandAssetsCollection })));
      const additionalMemberLandAssets = [memberLandAssets];
      const expectedCollection: IMemberLandAssets[] = [...additionalMemberLandAssets, ...memberLandAssetsCollection];
      jest.spyOn(memberLandAssetsService, 'addMemberLandAssetsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanDemand });
      comp.ngOnInit();

      expect(memberLandAssetsService.query).toHaveBeenCalled();
      expect(memberLandAssetsService.addMemberLandAssetsToCollectionIfMissing).toHaveBeenCalledWith(
        memberLandAssetsCollection,
        ...additionalMemberLandAssets.map(expect.objectContaining)
      );
      expect(comp.memberLandAssetsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SocietyCropRegistration query and add missing value', () => {
      const loanDemand: ILoanDemand = { id: 456 };
      const societyCropRegistration: ISocietyCropRegistration = { id: 8026 };
      loanDemand.societyCropRegistration = societyCropRegistration;

      const societyCropRegistrationCollection: ISocietyCropRegistration[] = [{ id: 30443 }];
      jest
        .spyOn(societyCropRegistrationService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: societyCropRegistrationCollection })));
      const additionalSocietyCropRegistrations = [societyCropRegistration];
      const expectedCollection: ISocietyCropRegistration[] = [...additionalSocietyCropRegistrations, ...societyCropRegistrationCollection];
      jest.spyOn(societyCropRegistrationService, 'addSocietyCropRegistrationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ loanDemand });
      comp.ngOnInit();

      expect(societyCropRegistrationService.query).toHaveBeenCalled();
      expect(societyCropRegistrationService.addSocietyCropRegistrationToCollectionIfMissing).toHaveBeenCalledWith(
        societyCropRegistrationCollection,
        ...additionalSocietyCropRegistrations.map(expect.objectContaining)
      );
      expect(comp.societyCropRegistrationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const loanDemand: ILoanDemand = { id: 456 };
      const member: IMember = { id: 88154 };
      loanDemand.member = member;
      const societyLoanProduct: ISocietyLoanProduct = { id: 115 };
      loanDemand.societyLoanProduct = societyLoanProduct;
      const memberLandAssets: IMemberLandAssets = { id: 79187 };
      loanDemand.memberLandAssets = memberLandAssets;
      const societyCropRegistration: ISocietyCropRegistration = { id: 49223 };
      loanDemand.societyCropRegistration = societyCropRegistration;

      activatedRoute.data = of({ loanDemand });
      comp.ngOnInit();

      expect(comp.membersSharedCollection).toContain(member);
      expect(comp.societyLoanProductsSharedCollection).toContain(societyLoanProduct);
      expect(comp.memberLandAssetsSharedCollection).toContain(memberLandAssets);
      expect(comp.societyCropRegistrationsSharedCollection).toContain(societyCropRegistration);
      expect(comp.loanDemand).toEqual(loanDemand);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanDemand>>();
      const loanDemand = { id: 123 };
      jest.spyOn(loanDemandFormService, 'getLoanDemand').mockReturnValue(loanDemand);
      jest.spyOn(loanDemandService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanDemand });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanDemand }));
      saveSubject.complete();

      // THEN
      expect(loanDemandFormService.getLoanDemand).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(loanDemandService.update).toHaveBeenCalledWith(expect.objectContaining(loanDemand));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanDemand>>();
      const loanDemand = { id: 123 };
      jest.spyOn(loanDemandFormService, 'getLoanDemand').mockReturnValue({ id: null });
      jest.spyOn(loanDemandService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanDemand: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: loanDemand }));
      saveSubject.complete();

      // THEN
      expect(loanDemandFormService.getLoanDemand).toHaveBeenCalled();
      expect(loanDemandService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILoanDemand>>();
      const loanDemand = { id: 123 };
      jest.spyOn(loanDemandService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ loanDemand });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(loanDemandService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
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

    describe('compareMemberLandAssets', () => {
      it('Should forward to memberLandAssetsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(memberLandAssetsService, 'compareMemberLandAssets');
        comp.compareMemberLandAssets(entity, entity2);
        expect(memberLandAssetsService.compareMemberLandAssets).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareSocietyCropRegistration', () => {
      it('Should forward to societyCropRegistrationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(societyCropRegistrationService, 'compareSocietyCropRegistration');
        comp.compareSocietyCropRegistration(entity, entity2);
        expect(societyCropRegistrationService.compareSocietyCropRegistration).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
