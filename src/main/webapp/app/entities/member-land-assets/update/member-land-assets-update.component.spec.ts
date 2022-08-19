import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MemberLandAssetsFormService } from './member-land-assets-form.service';
import { MemberLandAssetsService } from '../service/member-land-assets.service';
import { IMemberLandAssets } from '../member-land-assets.model';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';

import { MemberLandAssetsUpdateComponent } from './member-land-assets-update.component';

describe('MemberLandAssets Management Update Component', () => {
  let comp: MemberLandAssetsUpdateComponent;
  let fixture: ComponentFixture<MemberLandAssetsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let memberLandAssetsFormService: MemberLandAssetsFormService;
  let memberLandAssetsService: MemberLandAssetsService;
  let memberService: MemberService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MemberLandAssetsUpdateComponent],
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
      .overrideTemplate(MemberLandAssetsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MemberLandAssetsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    memberLandAssetsFormService = TestBed.inject(MemberLandAssetsFormService);
    memberLandAssetsService = TestBed.inject(MemberLandAssetsService);
    memberService = TestBed.inject(MemberService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Member query and add missing value', () => {
      const memberLandAssets: IMemberLandAssets = { id: 456 };
      const member: IMember = { id: 99308 };
      memberLandAssets.member = member;

      const memberCollection: IMember[] = [{ id: 62882 }];
      jest.spyOn(memberService, 'query').mockReturnValue(of(new HttpResponse({ body: memberCollection })));
      const additionalMembers = [member];
      const expectedCollection: IMember[] = [...additionalMembers, ...memberCollection];
      jest.spyOn(memberService, 'addMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ memberLandAssets });
      comp.ngOnInit();

      expect(memberService.query).toHaveBeenCalled();
      expect(memberService.addMemberToCollectionIfMissing).toHaveBeenCalledWith(
        memberCollection,
        ...additionalMembers.map(expect.objectContaining)
      );
      expect(comp.membersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const memberLandAssets: IMemberLandAssets = { id: 456 };
      const member: IMember = { id: 20625 };
      memberLandAssets.member = member;

      activatedRoute.data = of({ memberLandAssets });
      comp.ngOnInit();

      expect(comp.membersSharedCollection).toContain(member);
      expect(comp.memberLandAssets).toEqual(memberLandAssets);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberLandAssets>>();
      const memberLandAssets = { id: 123 };
      jest.spyOn(memberLandAssetsFormService, 'getMemberLandAssets').mockReturnValue(memberLandAssets);
      jest.spyOn(memberLandAssetsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberLandAssets });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: memberLandAssets }));
      saveSubject.complete();

      // THEN
      expect(memberLandAssetsFormService.getMemberLandAssets).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(memberLandAssetsService.update).toHaveBeenCalledWith(expect.objectContaining(memberLandAssets));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberLandAssets>>();
      const memberLandAssets = { id: 123 };
      jest.spyOn(memberLandAssetsFormService, 'getMemberLandAssets').mockReturnValue({ id: null });
      jest.spyOn(memberLandAssetsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberLandAssets: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: memberLandAssets }));
      saveSubject.complete();

      // THEN
      expect(memberLandAssetsFormService.getMemberLandAssets).toHaveBeenCalled();
      expect(memberLandAssetsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberLandAssets>>();
      const memberLandAssets = { id: 123 };
      jest.spyOn(memberLandAssetsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberLandAssets });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(memberLandAssetsService.update).toHaveBeenCalled();
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
  });
});
