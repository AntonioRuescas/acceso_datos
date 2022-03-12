import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IJugador, Jugador } from '../jugador.model';
import { JugadorService } from '../service/jugador.service';
import { IJuego } from 'app/entities/juego/juego.model';
import { JuegoService } from 'app/entities/juego/service/juego.service';
import { IPartida } from 'app/entities/partida/partida.model';
import { PartidaService } from 'app/entities/partida/service/partida.service';

@Component({
  selector: 'jhi-jugador-update',
  templateUrl: './jugador-update.component.html',
})
export class JugadorUpdateComponent implements OnInit {
  isSaving = false;

  juegosSharedCollection: IJuego[] = [];
  partidasSharedCollection: IPartida[] = [];

  editForm = this.fb.group({
    id: [],
    apodo: [null, [Validators.required, Validators.pattern('^[A-Za-z0-9_]*$')]],
    nombre: [null, [Validators.required]],
    apellido: [null, [Validators.required]],
    fechaDeNacimiento: [null, [Validators.required]],
    juegos: [],
    partidas: [],
  });

  constructor(
    protected jugadorService: JugadorService,
    protected juegoService: JuegoService,
    protected partidaService: PartidaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jugador }) => {
      this.updateForm(jugador);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jugador = this.createFromForm();
    if (jugador.id !== undefined) {
      this.subscribeToSaveResponse(this.jugadorService.update(jugador));
    } else {
      this.subscribeToSaveResponse(this.jugadorService.create(jugador));
    }
  }

  trackJuegoById(index: number, item: IJuego): number {
    return item.id!;
  }

  trackPartidaById(index: number, item: IPartida): number {
    return item.id!;
  }

  getSelectedJuego(option: IJuego, selectedVals?: IJuego[]): IJuego {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  getSelectedPartida(option: IPartida, selectedVals?: IPartida[]): IPartida {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJugador>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(jugador: IJugador): void {
    this.editForm.patchValue({
      id: jugador.id,
      apodo: jugador.apodo,
      nombre: jugador.nombre,
      apellido: jugador.apellido,
      fechaDeNacimiento: jugador.fechaDeNacimiento,
      juegos: jugador.juegos,
      partidas: jugador.partidas,
    });

    this.juegosSharedCollection = this.juegoService.addJuegoToCollectionIfMissing(this.juegosSharedCollection, ...(jugador.juegos ?? []));
    this.partidasSharedCollection = this.partidaService.addPartidaToCollectionIfMissing(
      this.partidasSharedCollection,
      ...(jugador.partidas ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.juegoService
      .query()
      .pipe(map((res: HttpResponse<IJuego[]>) => res.body ?? []))
      .pipe(
        map((juegos: IJuego[]) => this.juegoService.addJuegoToCollectionIfMissing(juegos, ...(this.editForm.get('juegos')!.value ?? [])))
      )
      .subscribe((juegos: IJuego[]) => (this.juegosSharedCollection = juegos));

    this.partidaService
      .query()
      .pipe(map((res: HttpResponse<IPartida[]>) => res.body ?? []))
      .pipe(
        map((partidas: IPartida[]) =>
          this.partidaService.addPartidaToCollectionIfMissing(partidas, ...(this.editForm.get('partidas')!.value ?? []))
        )
      )
      .subscribe((partidas: IPartida[]) => (this.partidasSharedCollection = partidas));
  }

  protected createFromForm(): IJugador {
    return {
      ...new Jugador(),
      id: this.editForm.get(['id'])!.value,
      apodo: this.editForm.get(['apodo'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      apellido: this.editForm.get(['apellido'])!.value,
      fechaDeNacimiento: this.editForm.get(['fechaDeNacimiento'])!.value,
      juegos: this.editForm.get(['juegos'])!.value,
      partidas: this.editForm.get(['partidas'])!.value,
    };
  }
}
