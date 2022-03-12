import { IJugador } from 'app/entities/jugador/jugador.model';
import { IPartida } from 'app/entities/partida/partida.model';

export interface IJuego {
  id?: number;
  nombre?: string;
  jugadors?: IJugador[] | null;
  partidas?: IPartida[] | null;
}

export class Juego implements IJuego {
  constructor(public id?: number, public nombre?: string, public jugadors?: IJugador[] | null, public partidas?: IPartida[] | null) {}
}

export function getJuegoIdentifier(juego: IJuego): number | undefined {
  return juego.id;
}
