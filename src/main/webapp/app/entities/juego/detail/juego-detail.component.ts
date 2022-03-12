import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJuego } from '../juego.model';

@Component({
  selector: 'jhi-juego-detail',
  templateUrl: './juego-detail.component.html',
})
export class JuegoDetailComponent implements OnInit {
  juego: IJuego | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ juego }) => {
      this.juego = juego;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
