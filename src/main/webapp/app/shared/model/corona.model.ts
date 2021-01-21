export interface ICorona {
  id?: number;
  nombrecasparjour?: string;
  caspositif?: string;
  cascommunautaire?: string;
  casgrave?: string;
  guerison?: string;
  deces?: string;
  cascontact?: string;
  casimporte?: string;
}

export class Corona implements ICorona {
  constructor(
    public id?: number,
    public nombrecasparjour?: string,
    public caspositif?: string,
    public cascommunautaire?: string,
    public casgrave?: string,
    public guerison?: string,
    public deces?: string,
    public cascontact?: string,
    public casimporte?: string
  ) {}
}
