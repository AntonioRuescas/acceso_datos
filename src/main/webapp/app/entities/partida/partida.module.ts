import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PartidaComponent } from './list/partida.component';
import { PartidaDetailComponent } from './detail/partida-detail.component';
import { PartidaUpdateComponent } from './update/partida-update.component';
import { PartidaDeleteDialogComponent } from './delete/partida-delete-dialog.component';
import { PartidaRoutingModule } from './route/partida-routing.module';

@NgModule({
  imports: [SharedModule, PartidaRoutingModule],
  declarations: [PartidaComponent, PartidaDetailComponent, PartidaUpdateComponent, PartidaDeleteDialogComponent],
  entryComponents: [PartidaDeleteDialogComponent],
})
export class PartidaModule {}
