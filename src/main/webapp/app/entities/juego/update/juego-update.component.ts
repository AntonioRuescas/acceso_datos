import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IJuego, Juego } from '../juego.model';
import { JuegoService } from '../service/juego.service';

@Component({
  selector: 'jhi-juego-update',
  templateUrl: './juego-update.component.html',
})
export class JuegoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
  });

  constructor(protected juegoService: JuegoService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ juego }) => {
      this.updateForm(juego);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const juego = this.createFromForm();
    if (juego.id !== undefined) {
      this.subscribeToSaveResponse(this.juegoService.update(juego));
    } else {
      this.subscribeToSaveResponse(this.juegoService.create(juego));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJuego>>): void {
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

  protected updateForm(juego: IJuego): void {
    this.editForm.patchValue({
      id: juego.id,
      nombre: juego.nombre,
    });
  }

  protected createFromForm(): IJuego {
    return {
      ...new Juego(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
    };
  }
}
