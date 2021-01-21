import { Moment } from 'moment';

export interface ICovid {
  id?: number;
  nombretest?: string;
  positifcas?: string;
  date?: Moment;
}

export class Covid implements ICovid {
  constructor(public id?: number, public nombretest?: string, public positifcas?: string, public date?: Moment) {}
}
