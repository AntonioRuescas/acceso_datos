import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'juego',
        data: { pageTitle: 'Juegos' },
        loadChildren: () => import('./juego/juego.module').then(m => m.JuegoModule),
      },
      {
        path: 'jugador',
        data: { pageTitle: 'Jugadors' },
        loadChildren: () => import('./jugador/jugador.module').then(m => m.JugadorModule),
      },
      {
        path: 'partida',
        data: { pageTitle: 'Partidas' },
        loadChildren: () => import('./partida/partida.module').then(m => m.PartidaModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
