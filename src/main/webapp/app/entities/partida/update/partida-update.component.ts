import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IPartida, Partida } from '../partida.model';
import { PartidaService } from '../service/partida.service';
import { IJuego } from 'app/entities/juego/juego.model';
import { JuegoService } from 'app/entities/juego/service/juego.service';

@Component({
  selector: 'jhi-partida-update',
  templateUrl: './partida-update.component.html',
})
export class PartidaUpdateComponent implements OnInit {
  isSaving = false;

  juegosSharedCollection: IJuego[] = [];

  editForm = this.fb.group({
    id: [],
    ganador: [null, [Validators.required]],
    perdedor: [null, [Validators.required]],
    puntosDelGanador: [null, [Validators.required, Validators.min(0)]],
    juego: [],
  });

  constructor(
    protected partidaService: PartidaService,
    protected juegoService: JuegoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partida }) => {
      this.updateForm(partida);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partida = this.createFromForm();
    if (partida.id !== undefined) {
      this.subscribeToSaveResponse(this.partidaService.update(partida));
    } else {
      this.subscribeToSaveResponse(this.partidaService.create(partida));
    }
  }

  trackJuegoById(index: number, item: IJuego): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartida>>): void {
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

  protected updateForm(partida: IPartida): void {
    this.editForm.patchValue({
      id: partida.id,
      ganador: partida.ganador,
      perdedor: partida.perdedor,
      puntosDelGanador: partida.puntosDelGanador,
      juego: partida.juego,
    });

    this.juegosSharedCollection = this.juegoService.addJuegoToCollectionIfMissing(this.juegosSharedCollection, partida.juego);
  }

  protected loadRelationshipsOptions(): void {
    this.juegoService
      .query()
      .pipe(map((res: HttpResponse<IJuego[]>) => res.body ?? []))
      .pipe(map((juegos: IJuego[]) => this.juegoService.addJuegoToCollectionIfMissing(juegos, this.editForm.get('juego')!.value)))
      .subscribe((juegos: IJuego[]) => (this.juegosSharedCollection = juegos));
  }

  protected createFromForm(): IPartida {
    return {
      ...new Partida(),
      id: this.editForm.get(['id'])!.value,
      ganador: this.editForm.get(['ganador'])!.value,
      perdedor: this.editForm.get(['perdedor'])!.value,
      puntosDelGanador: this.editForm.get(['puntosDelGanador'])!.value,
      juego: this.editForm.get(['juego'])!.value,
    };
  }
}
