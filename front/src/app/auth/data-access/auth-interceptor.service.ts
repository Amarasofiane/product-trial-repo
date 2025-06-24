import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { inject } from '@angular/core';
import { UserAuthService } from './user-auth-service.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private userAuthService = inject(UserAuthService);

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> { // intercepteur de toute les request afin d'ajouter le token pour authentification 
    const token = this.userAuthService.getToken();

    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    }

    return next.handle(request);
  }
}
