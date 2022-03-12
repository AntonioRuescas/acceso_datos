import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { JuegoComponent } from './list/juego.component';
import { JuegoDetailComponent } from './detail/juego-detail.component';
import { JuegoUpdateComponent } from './update/juego-update.component';
import { JuegoDeleteDialogComponent } from './delete/juego-delete-dialog.component';
import { JuegoRoutingModule } from './route/juego-routing.module';

@NgModule({
  imports: [SharedModule, JuegoRoutingModule],
  declarations: [JuegoComponent, JuegoDetailComponent, JuegoUpdateComponent, JuegoDeleteDialogComponent],
  entryComponents: [JuegoDeleteDialogComponent],
})
export class JuegoModule {}
