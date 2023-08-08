import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public getToken(): string | null {
    return localStorage.getItem('token');
  }

  public setToken(token: string) {
    localStorage.setItem('token', token);
  }

  public isAuthenticated(): boolean {
    const token = this.getToken();
    return token != null;
  }

  constructor() { }
}
