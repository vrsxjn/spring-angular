// auth.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    const authToken = this.getAuthTokenFromCookies();

    if (authToken) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }

  private getAuthTokenFromCookies(): string | null {
    const cookieRegex = /auth_token=([^;]+)/;
    const match = document.cookie.match(cookieRegex);

    if (match && match[1]) {
      return match[1];
    } else {
      return null;
    }
  }
}
