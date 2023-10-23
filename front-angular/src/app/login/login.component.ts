import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  ngOnInit() {
    const form = document.querySelector('form') as HTMLFormElement;

    if (form) {
      form.addEventListener('submit', (e) => {
        e.preventDefault();

        const username = document.getElementById('username') as HTMLInputElement;
        const password = document.getElementById('password') as HTMLInputElement;
        if (username && password) {
          username.value;
          password.value;
        }

        const user = {
          username: username.value,
          password: password.value
        };

        fetch('http://localhost:8080/auth/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(user)
        })
          .then((response) => response.json())
          .then((data) => {
            if (data.username != null) {
              const token = data.token;
              document.cookie = `auth_token=${token}; path=/; expires=Fri, 31 Dec 9999 23:59:59 GMT`;

              window.location.href = '/home';
            } else {
              alert('Usuario não encontrado');
            }
          })
          .catch((error) => {
            alert('Usuario não encontrado');
          });
      });
    }
  }
}
