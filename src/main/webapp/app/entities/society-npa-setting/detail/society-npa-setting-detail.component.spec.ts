import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SocietyNpaSettingDetailComponent } from './society-npa-setting-detail.component';

describe('SocietyNpaSetting Management Detail Component', () => {
  let comp: SocietyNpaSettingDetailComponent;
  let fixture: ComponentFixture<SocietyNpaSettingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SocietyNpaSettingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ societyNpaSetting: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SocietyNpaSettingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SocietyNpaSettingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load societyNpaSetting on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.societyNpaSetting).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
