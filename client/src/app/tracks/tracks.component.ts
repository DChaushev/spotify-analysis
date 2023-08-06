import { Component, ViewChild, inject } from '@angular/core';
import { TopTracksService } from '../top-tracks.service';
import { Track } from '../track';
import { QuerySpec } from '../query-spec';
import { MatPaginator } from '@angular/material/paginator';
import { catchError, map, startWith, switchMap, of as observableOf } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-tracks',
  templateUrl: './tracks.component.html',
  styleUrls: ['./tracks.component.css']
})
export class TracksComponent {
  limit: number = 10;

  trackList: Track[] = [];
  previous: String = ""
  next: String = ""
  topTracksService: TopTracksService = inject(TopTracksService);

  displayedColumns: string[] = ['album-cover', 'name', 'album', 'artist', 'duration', 'preview'];
  @ViewChild('paginator') paginator!: MatPaginator;

  pageSizes = [this.limit];
  totalData!: number;
  dataSource = new MatTableDataSource<Track>();
  
  msToReadableString(duration_ms:number) {
    let minutes = Math.floor((duration_ms % 3600000) / 60000);
    let seconds = Math.floor(((duration_ms % 360000) % 60000) / 1000);
    return minutes + ":" + seconds;
  }

  getTableData$(limit: number, offset: number) {
    const querySpec: QuerySpec = {
      limit: limit,
      offset: offset,
      accessToken: "BQBROleKtfbrzrZM2qGLGcexlU46CqeVKbqQhyHkb9Se-SZM9pqKhCXzaLTOohJjEuS8bAl0FelAWpfvriNeLh-woWg0MOloMePd4K6JbsEL_tXoFoXEfhxb5Z8G4jhAF3I0eqM5AHbm6sC3zEFnvzk_77g1yA7-zTaCx4caXU4lJAncX3f26kNlIDT6LpAEQy5sNA"
    }
    return this.topTracksService.getTopTracks(querySpec)
  }

  ngAfterViewInit() {
    
    this.dataSource.paginator = this.paginator;

    this.paginator.page
      .pipe(
        startWith({}),
        switchMap(() => {
          return this.getTableData$(
            this.limit,
            this.paginator.pageIndex * this.limit
          ).pipe(catchError(() => observableOf(null)));
        }),
        map((resultItem) => {
          if (resultItem == null) return [];
          this.totalData = resultItem.total;
          return resultItem.resultItems;
        })
      )
      .subscribe((resultItem) => {
        this.trackList = resultItem;
        this.dataSource = new MatTableDataSource(this.trackList);
      });
  }
}
