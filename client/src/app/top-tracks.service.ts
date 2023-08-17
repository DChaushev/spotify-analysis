import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { ResultItem } from './result-item';
import { QuerySpec } from './query-spec';
import { ResponseItem } from './response-item';

@Injectable({
  providedIn: 'root',
})
export class TopTracksService {
  url = 'http://localhost:8080/toptracks';

  constructor(private http: HttpClient) { }

  getTopTracks(querySpec: QuerySpec): Observable<ResponseItem> {
    return this.http
      .get<ResponseItem>(this.url, {
        headers: {
          Authorization: `Bearer ${querySpec.accessToken}`,
        },
        params: {
          limit: querySpec.limit,
          offset: querySpec.offset,
        },
      });
  }
}
