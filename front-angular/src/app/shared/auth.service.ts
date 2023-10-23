import { Injectable } from '@angular/core';

function getAuthTokenCookie() {
  const name = 'auth_token=';
  const decodedCookies = decodeURIComponent(document.cookie);
  const cookieArray = decodedCookies.split(';');

  for (let i = 0; i < cookieArray.length; i++) {
    let cookie = cookieArray[i];
    while (cookie.charAt(0) === ' ') {
      cookie = cookie.substring(1);
    }
    if (cookie.indexOf(name) === 0) {
      return cookie.substring(name.length, cookie.length);
    }
  }

  return null;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  getUserByToken() {

    const body = {
      token: getAuthTokenCookie()
    };

    return fetch('http://localhost:8080/usuario/user', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${getAuthTokenCookie()}`

      },
      body: JSON.stringify(body)
    })
    .then((response) => {
        return response.json();
    })
    }
}