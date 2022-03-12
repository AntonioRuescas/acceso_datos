import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { JuegoComponent } from '../list/juego.component';
import { JuegoDetailComponent } from '../detail/juego-detail.component';
import { JuegoUpdateComponent } from '../update/juego-update.component';
import { JuegoRoutingResolveService } from './juego-routing-resolve.service';

const juegoRoute: Routes = [
  {
    path: '',
    component: JuegoComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JuegoDetailComponent,
    resolve: {
      juego: JuegoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JuegoUpdateComponent,
    resolve: {
      juego: JuegoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JuegoUpdateComponent,
    resolve: {
      juego: JuegoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(juegoRoute)],
  exports: [RouterModule],
})
export class JuegoRoutingModule {}
