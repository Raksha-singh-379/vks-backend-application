import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SocietyCropRegistrationDetailComponent } from './society-crop-registration-detail.component';

describe('SocietyCropRegistration Management Detail Component', () => {
  let comp: SocietyCropRegistrationDetailComponent;
  let fixture: ComponentFixture<SocietyCropRegistrationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SocietyCropRegistrationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ societyCropRegistration: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SocietyCropRegistrationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SocietyCropRegistrationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load societyCropRegistration on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.societyCropRegistration).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
