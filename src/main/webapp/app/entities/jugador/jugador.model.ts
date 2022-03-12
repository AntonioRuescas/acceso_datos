import dayjs from 'dayjs/esm';
import { IJuego } from 'app/entities/juego/juego.model';
import { IPartida } from 'app/entities/partida/partida.model';

export interface IJugador {
  id?: number;
  apodo?: string;
  nombre?: string;
  apellido?: string;
  fechaDeNacimiento?: dayjs.Dayjs;
  juegos?: IJuego[] | null;
  partidas?: IPartida[] | null;
}

export class Jugador implements IJugador {
  constructor(
    public id?: number,
    public apodo?: string,
    public nombre?: string,
    public apellido?: string,
    public fechaDeNacimiento?: dayjs.Dayjs,
    public juegos?: IJuego[] | null,
    public partidas?: IPartida[] | null
  ) {}
}

export function getJugadorIdentifier(jugador: IJugador): number | undefined {
  return jugador.id;
}
