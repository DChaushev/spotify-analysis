import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public getAccessToken(): string | null {
    return localStorage.getItem('accessToken');
  }

  public setAccessToken(token: string) {
    localStorage.setItem('accessToken', token);
  }

  public getRefreshToken() : string | null {
    return localStorage.getItem('refreshToken');
  }

  public setRefreshToken(refreshToken: string) {
    localStorage.setItem('refreshToken', refreshToken)
  }

  public isAuthenticated(): boolean {
    const token = this.getAccessToken();
    return token != null;
  }

  constructor(private http: HttpClient) { }

  refreshToken(token: string) {
    return this.http.post('https://accounts.spotify.com/api/token', {
      refreshToken: token
    }, {});
  }  
}
