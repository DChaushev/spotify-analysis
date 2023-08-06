import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap} from 'rxjs/operators';
import { ResultItem } from './result-item';
import { QuerySpec } from './query-spec';

@Injectable({
  providedIn: 'root'
})
export class TopTracksService {
  url = 'http://localhost:8080/toptracks';
  // ?limit=5&offset=5&accessToken=BQBYe92hGfq9W6aqUL34H11ny2lm05rB6uLP8Npigq1dz4ngH5Ld-NdozQH48OJumJjLccogPwC3rvolrJbi2pT8xQBPZjHwoDq0yhDBjc_H05krfPU_KjlUKv-05l5sJfSeTsx4QMEql-mF206drNBtzlNnNdlLW1CFYyBhO4GZ_HXU1uWiVVX8_GA9PQusAABG1g';

  constructor(private http: HttpClient) {}
  
  getTopTracks(querySpec: QuerySpec): Observable<ResultItem> {
    return this.http.get<ResultItem>(this.url,
      {
        params: {
          limit: querySpec.limit,
          offset: querySpec.offset,
          accessToken: querySpec.accessToken
        }
      })
      .pipe(
        tap(_ => console.log('fetched tracks')),
        catchError(this.handleError<ResultItem>('getTopTracks', {resultItems: [],
        total: 0, previous: "", next: ""}))
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
