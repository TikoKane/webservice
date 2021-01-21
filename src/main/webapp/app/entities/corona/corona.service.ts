import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ICorona } from 'app/shared/model/corona.model';

type EntityResponseType = HttpResponse<ICorona>;
type EntityArrayResponseType = HttpResponse<ICorona[]>;

@Injectable({ providedIn: 'root' })
export class CoronaService {
  public resourceUrl = SERVER_API_URL + 'api/coronas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/coronas';

  constructor(protected http: HttpClient) {}

  create(corona: ICorona): Observable<EntityResponseType> {
    return this.http.post<ICorona>(this.resourceUrl, corona, { observe: 'response' });
  }

  update(corona: ICorona): Observable<EntityResponseType> {
    return this.http.put<ICorona>(this.resourceUrl, corona, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICorona>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICorona[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICorona[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
