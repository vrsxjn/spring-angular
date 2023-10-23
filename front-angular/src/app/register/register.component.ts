import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  ngOnInit() {
    const form = document.getElementById('register-form') as HTMLFormElement;

    if (form) {
      form.addEventListener('submit', (e) => {
        e.preventDefault();

        const username = document.getElementById('new-username') as HTMLInputElement;
        const password = document.getElementById('new-password') as HTMLInputElement;
        const passwordConfirm = document.getElementById('confirm-password') as HTMLInputElement;

        if (username && password && passwordConfirm) {
          const email = username.value;
          const passwordLogin = password.value;
          const passwordConfirmm = passwordConfirm.value;

          if (passwordLogin !== passwordConfirmm) {
            alert('A senha e a confirmação de senha não coincidem.');
            return;
          }

          const newUser = {
            username: email,
            password: passwordLogin
          };

          fetch('http://localhost:8080/auth/register', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(newUser)
          })
            .then((response) => response.json())
            .then((data) => {
              if (data.username != null) {
                window.location.href = '/login';
              } else {
                alert(data.message);
              }
            })
            .catch((error) => {
              alert(error);
            });
        }
      });
    }
  }
}
