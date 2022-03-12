import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartida } from '../partida.model';

@Component({
  selector: 'jhi-partida-detail',
  templateUrl: './partida-detail.component.html',
})
export class PartidaDetailComponent implements OnInit {
  partida: IPartida | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partida }) => {
      this.partida = partida;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
