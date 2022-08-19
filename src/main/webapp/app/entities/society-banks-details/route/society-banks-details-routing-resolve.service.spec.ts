import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISocietyBanksDetails } from '../society-banks-details.model';
import { SocietyBanksDetailsService } from '../service/society-banks-details.service';

import { SocietyBanksDetailsRoutingResolveService } from './society-banks-details-routing-resolve.service';

describe('SocietyBanksDetails routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SocietyBanksDetailsRoutingResolveService;
  let service: SocietyBanksDetailsService;
  let resultSocietyBanksDetails: ISocietyBanksDetails | null | undefined;

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
    routingResolveService = TestBed.inject(SocietyBanksDetailsRoutingResolveService);
    service = TestBed.inject(SocietyBanksDetailsService);
    resultSocietyBanksDetails = undefined;
  });

  describe('resolve', () => {
    it('should return ISocietyBanksDetails returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSocietyBanksDetails = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSocietyBanksDetails).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSocietyBanksDetails = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSocietyBanksDetails).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISocietyBanksDetails>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSocietyBanksDetails = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSocietyBanksDetails).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
