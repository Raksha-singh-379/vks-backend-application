import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SocietyConfigDetailComponent } from './society-config-detail.component';

describe('SocietyConfig Management Detail Component', () => {
  let comp: SocietyConfigDetailComponent;
  let fixture: ComponentFixture<SocietyConfigDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SocietyConfigDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ societyConfig: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SocietyConfigDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SocietyConfigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load societyConfig on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.societyConfig).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
