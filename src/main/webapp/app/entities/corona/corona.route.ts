import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICorona, Corona } from 'app/shared/model/corona.model';
import { CoronaService } from './corona.service';
import { CoronaComponent } from './corona.component';
import { CoronaDetailComponent } from './corona-detail.component';
import { CoronaUpdateComponent } from './corona-update.component';

@Injectable({ providedIn: 'root' })
export class CoronaResolve implements Resolve<ICorona> {
  constructor(private service: CoronaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICorona> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((corona: HttpResponse<Corona>) => {
          if (corona.body) {
            return of(corona.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Corona());
  }
}

export const coronaRoute: Routes = [
  {
    path: '',
    component: CoronaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Coronas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CoronaDetailComponent,
    resolve: {
      corona: CoronaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Coronas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CoronaUpdateComponent,
    resolve: {
      corona: CoronaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Coronas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CoronaUpdateComponent,
    resolve: {
      corona: CoronaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Coronas',
    },
    canActivate: [UserRouteAccessService],
  },
];
