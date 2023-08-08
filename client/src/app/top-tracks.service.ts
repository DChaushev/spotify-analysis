import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { ResultItem } from './result-item';
import { QuerySpec } from './query-spec';

@Injectable({
  providedIn: 'root',
})
export class TopTracksService {
  url = 'http://localhost:8080/toptracks';

  constructor(private http: HttpClient) { }

  getTopTracks(querySpec: QuerySpec): Observable<ResultItem> {
    return this.http
      .get<ResultItem>(this.url, {
        headers: {
          Authorization: `Bearer ${querySpec.accessToken}`,
        },
        params: {
          limit: querySpec.limit,
          offset: querySpec.offset,
        },
      })
      .pipe(
        tap((_) => console.log('fetched tracks')),
        catchError(
          this.handleError<ResultItem>('getTopTracks', {
            resultItems: [],
            total: 0,
            previous: '',
            next: '',
          })
        )
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);

      console.log(`${operation} failed: ${error.message}`);

      return of(result as T);
    };
  }
}
