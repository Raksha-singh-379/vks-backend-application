import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { MemberLandAssetsFormService, MemberLandAssetsFormGroup } from './member-land-assets-form.service';
import { IMemberLandAssets } from '../member-land-assets.model';
import { MemberLandAssetsService } from '../service/member-land-assets.service';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';

@Component({
  selector: 'jhi-member-land-assets-update',
  templateUrl: './member-land-assets-update.component.html',
})
export class MemberLandAssetsUpdateComponent implements OnInit {
  isSaving = false;
  memberLandAssets: IMemberLandAssets | null = null;

  membersSharedCollection: IMember[] = [];

  editForm: MemberLandAssetsFormGroup = this.memberLandAssetsFormService.createMemberLandAssetsFormGroup();

  constructor(
    protected memberLandAssetsService: MemberLandAssetsService,
    protected memberLandAssetsFormService: MemberLandAssetsFormService,
    protected memberService: MemberService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMember = (o1: IMember | null, o2: IMember | null): boolean => this.memberService.compareMember(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ memberLandAssets }) => {
      this.memberLandAssets = memberLandAssets;
      if (memberLandAssets) {
        this.updateForm(memberLandAssets);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const memberLandAssets = this.memberLandAssetsFormService.getMemberLandAssets(this.editForm);
    if (memberLandAssets.id !== null) {
      this.subscribeToSaveResponse(this.memberLandAssetsService.update(memberLandAssets));
    } else {
      this.subscribeToSaveResponse(this.memberLandAssetsService.create(memberLandAssets));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMemberLandAssets>>): void {
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

  protected updateForm(memberLandAssets: IMemberLandAssets): void {
    this.memberLandAssets = memberLandAssets;
    this.memberLandAssetsFormService.resetForm(this.editForm, memberLandAssets);

    this.membersSharedCollection = this.memberService.addMemberToCollectionIfMissing<IMember>(
      this.membersSharedCollection,
      memberLandAssets.member
    );
  }

  protected loadRelationshipsOptions(): void {
    this.memberService
      .query()
      .pipe(map((res: HttpResponse<IMember[]>) => res.body ?? []))
      .pipe(map((members: IMember[]) => this.memberService.addMemberToCollectionIfMissing<IMember>(members, this.memberLandAssets?.member)))
      .subscribe((members: IMember[]) => (this.membersSharedCollection = members));
  }
}
