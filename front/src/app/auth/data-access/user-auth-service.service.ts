import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: "root",
})
export class UserAuthService {
  private apiUrl = 'http://localhost:8080/auth';
  private tokenKey = 'authToken';
  private roleKey = 'userRole';

  constructor(private http: HttpClient) { }

  login(username: string, role: string): Observable<any> {  // login mode admin ou user
    return this.http.post(
      `${this.apiUrl}/login`,
      { username, role },
      {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
        withCredentials: true, 
      }
    );
  }

  setToken(token: string, role: string) {  // MAJ le token dans le localStorage
    localStorage.setItem(this.tokenKey, token);
    localStorage.setItem(this.roleKey, role);
  }

  getToken(): string | null { // recuperation  du token depuis localStorage
    return localStorage.getItem(this.tokenKey);
  }

  getRole(): string | null {  // recuperation  du role depuis localStorage
    return localStorage.getItem(this.roleKey);
  }

  isAdmin(): boolean { 
    return this.getRole() === 'admin';
  }

  isUserRole(): boolean { 
    return this.getRole() === 'user';
  }

  logout() {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.roleKey);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }
}
