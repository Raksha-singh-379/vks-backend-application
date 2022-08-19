import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SocietyBanksDetailsDetailComponent } from './society-banks-details-detail.component';

describe('SocietyBanksDetails Management Detail Component', () => {
  let comp: SocietyBanksDetailsDetailComponent;
  let fixture: ComponentFixture<SocietyBanksDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SocietyBanksDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ societyBanksDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SocietyBanksDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SocietyBanksDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load societyBanksDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.societyBanksDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
