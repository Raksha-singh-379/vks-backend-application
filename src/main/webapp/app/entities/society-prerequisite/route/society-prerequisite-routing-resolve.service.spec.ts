import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISocietyPrerequisite } from '../society-prerequisite.model';
import { SocietyPrerequisiteService } from '../service/society-prerequisite.service';

import { SocietyPrerequisiteRoutingResolveService } from './society-prerequisite-routing-resolve.service';

describe('SocietyPrerequisite routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SocietyPrerequisiteRoutingResolveService;
  let service: SocietyPrerequisiteService;
  let resultSocietyPrerequisite: ISocietyPrerequisite | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(SocietyPrerequisiteRoutingResolveService);
    service = TestBed.inject(SocietyPrerequisiteService);
    resultSocietyPrerequisite = undefined;
  });

  describe('resolve', () => {
    it('should return ISocietyPrerequisite returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSocietyPrerequisite = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSocietyPrerequisite).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSocietyPrerequisite = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSocietyPrerequisite).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISocietyPrerequisite>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSocietyPrerequisite = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSocietyPrerequisite).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
