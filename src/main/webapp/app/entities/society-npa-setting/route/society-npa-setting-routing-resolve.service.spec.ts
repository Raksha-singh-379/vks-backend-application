import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISocietyNpaSetting } from '../society-npa-setting.model';
import { SocietyNpaSettingService } from '../service/society-npa-setting.service';

import { SocietyNpaSettingRoutingResolveService } from './society-npa-setting-routing-resolve.service';

describe('SocietyNpaSetting routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SocietyNpaSettingRoutingResolveService;
  let service: SocietyNpaSettingService;
  let resultSocietyNpaSetting: ISocietyNpaSetting | null | undefined;

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
    routingResolveService = TestBed.inject(SocietyNpaSettingRoutingResolveService);
    service = TestBed.inject(SocietyNpaSettingService);
    resultSocietyNpaSetting = undefined;
  });

  describe('resolve', () => {
    it('should return ISocietyNpaSetting returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSocietyNpaSetting = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSocietyNpaSetting).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSocietyNpaSetting = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSocietyNpaSetting).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISocietyNpaSetting>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSocietyNpaSetting = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSocietyNpaSetting).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
