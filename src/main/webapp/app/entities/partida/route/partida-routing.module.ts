import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PartidaComponent } from '../list/partida.component';
import { PartidaDetailComponent } from '../detail/partida-detail.component';
import { PartidaUpdateComponent } from '../update/partida-update.component';
import { PartidaRoutingResolveService } from './partida-routing-resolve.service';

const partidaRoute: Routes = [
  {
    path: '',
    component: PartidaComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartidaDetailComponent,
    resolve: {
      partida: PartidaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartidaUpdateComponent,
    resolve: {
      partida: PartidaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartidaUpdateComponent,
    resolve: {
      partida: PartidaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(partidaRoute)],
  exports: [RouterModule],
})
export class PartidaRoutingModule {}
