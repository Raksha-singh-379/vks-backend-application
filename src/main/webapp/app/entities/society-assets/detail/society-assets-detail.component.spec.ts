import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SocietyAssetsDetailComponent } from './society-assets-detail.component';

describe('SocietyAssets Management Detail Component', () => {
  let comp: SocietyAssetsDetailComponent;
  let fixture: ComponentFixture<SocietyAssetsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SocietyAssetsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ societyAssets: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SocietyAssetsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SocietyAssetsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load societyAssets on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.societyAssets).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
