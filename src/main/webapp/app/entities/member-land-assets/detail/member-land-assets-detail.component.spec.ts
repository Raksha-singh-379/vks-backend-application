import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MemberLandAssetsDetailComponent } from './member-land-assets-detail.component';

describe('MemberLandAssets Management Detail Component', () => {
  let comp: MemberLandAssetsDetailComponent;
  let fixture: ComponentFixture<MemberLandAssetsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MemberLandAssetsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ memberLandAssets: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MemberLandAssetsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MemberLandAssetsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load memberLandAssets on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.memberLandAssets).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
