import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { HttpParams } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-callback',
  templateUrl: './callback.component.html',
  styleUrls: ['./callback.component.css'],
})
export class CallbackComponent implements OnInit {
  code!: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private auth: AuthService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.code = params['code'];
    });

    let base64Encoded = btoa(`${environment.CLIENT_ID}:${environment.CLIENT_SECRET}`);
    console.log(base64Encoded)

    let body = new HttpParams()
      .set("grant_type", "authorization_code")
      .set("code", this.code)
      .set("redirect_uri", environment.REDIRECT_URI);

    this.http.post<any>("https://accounts.spotify.com/api/token",
      body,
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/x-www-form-urlencoded',
          Authorization: `Basic ${base64Encoded}`
        })
      })
      .subscribe(data => {
        console.log(data)
        let accessToken = data['access_token'];
        let refreshToken = data['refresh_token'];

        this.auth.setAccessToken(accessToken);
        this.router.navigate(['/toptracks']);
      });
  }
}
