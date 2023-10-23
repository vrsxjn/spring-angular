import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../component/dialog/dialog.component';
import { DialogPicComponent } from '../component/dialog-pic/dialog-pic.component';
import { Post } from '../component/card/post.model';
import { AuthService } from '../shared/auth.service';

const authService = new AuthService();

let userInfor: any = [];

authService.getUserByToken()
  .then((user) => {
    userInfor = user;
  })
const api = {
  getPost: () => {
    const postUrl = 'http://localhost:8080/images';
    return fetch(postUrl, {
      headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${userInfor.token}`
     }})
      .then((response) => response.json())
      .then((posts) => {
        return api.getUser().then((users) => {
          return api.getComment().then((comments) => {
            const postsWithComments = posts.map((post: { id: any, user_id: any }) => {
              const postComments = comments.filter((comment: { id: any, picsId: any }) => comment.picsId === post.id);
              const user = users.find((user: { id: any; user_id: any }) => user.id === post.user_id);
              return { ...post, comments: postComments, user };
            });
            return postsWithComments;
          });
        });
      });
  },

  getComment: () => {
    const commentUrl = 'http://localhost:8080/comment';
    return fetch(commentUrl, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${userInfor.token}`
      }
      })
      .then((response) => response.json()); 
  },

  getUser: () => {
    const userUrl = 'http://localhost:8080/usuario';
    return fetch(userUrl, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${userInfor.token}`
      }
      })
      .then((response) => response.json()); 
  }
};


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],

})



export class HomeComponent {
  posts: Post[] = [];
  constructor(private dialog: MatDialog) { }
  
  ngOnInit() {
    api.getPost().then((posts) => {
      this.posts = posts; 
    });
  }

  openPicDialog() {
    this.dialog.open(DialogPicComponent);
  }

}

