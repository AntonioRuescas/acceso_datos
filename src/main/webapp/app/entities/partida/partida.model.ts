import { IJuego } from 'app/entities/juego/juego.model';
import { IJugador } from 'app/entities/jugador/jugador.model';

export interface IPartida {
  id?: number;
  ganador?: string;
  perdedor?: string;
  puntosDelGanador?: number;
  juego?: IJuego | null;
  jugadors?: IJugador[] | null;
}

export class Partida implements IPartida {
  constructor(
    public id?: number,
    public ganador?: string,
    public perdedor?: string,
    public puntosDelGanador?: number,
    public juego?: IJuego | null,
    public jugadors?: IJugador[] | null
  ) {}
}

export function getPartidaIdentifier(partida: IPartida): number | undefined {
  return partida.id;
}
