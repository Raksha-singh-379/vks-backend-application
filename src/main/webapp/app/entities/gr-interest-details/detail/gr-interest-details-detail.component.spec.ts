import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GRInterestDetailsDetailComponent } from './gr-interest-details-detail.component';

describe('GRInterestDetails Management Detail Component', () => {
  let comp: GRInterestDetailsDetailComponent;
  let fixture: ComponentFixture<GRInterestDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GRInterestDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ gRInterestDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(GRInterestDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(GRInterestDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load gRInterestDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.gRInterestDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
