import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SocietyPrerequisiteDetailComponent } from './society-prerequisite-detail.component';

describe('SocietyPrerequisite Management Detail Component', () => {
  let comp: SocietyPrerequisiteDetailComponent;
  let fixture: ComponentFixture<SocietyPrerequisiteDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SocietyPrerequisiteDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ societyPrerequisite: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SocietyPrerequisiteDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SocietyPrerequisiteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load societyPrerequisite on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.societyPrerequisite).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
