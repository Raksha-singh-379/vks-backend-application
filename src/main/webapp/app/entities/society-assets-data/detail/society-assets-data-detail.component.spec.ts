import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SocietyAssetsDataDetailComponent } from './society-assets-data-detail.component';

describe('SocietyAssetsData Management Detail Component', () => {
  let comp: SocietyAssetsDataDetailComponent;
  let fixture: ComponentFixture<SocietyAssetsDataDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SocietyAssetsDataDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ societyAssetsData: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SocietyAssetsDataDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SocietyAssetsDataDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load societyAssetsData on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.societyAssetsData).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
