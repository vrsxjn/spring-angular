import { Component, Input } from '@angular/core';
import { AuthService } from 'src/app/shared/auth.service';
import { DialogComponent } from '../dialog/dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent {
  @Input() post: any; 
  currentUser: any;
  comment: string | undefined;


  constructor(private authService: AuthService, private dialog: MatDialog) {
    this.authService.getUserByToken().then((response: any) => {
      this.currentUser = response;
    });
  }

  openDialog(picId: number): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      data: { comment: this.comment },
    });
  
    dialogRef.afterClosed().subscribe((result: string) => {
      if (result) {
        this.comment = result;
        

        const commentario = {
          picsId:  picId,
          userId: this.currentUser.id,
          comment: result,
          username: this.currentUser.username
        };

        fetch('http://localhost:8080/comment', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${this.currentUser.token}`
          },
          body: JSON.stringify(commentario)
        })
          .then((response) => response.json())
          .then((data) => {
            location.reload();
          })
        
      }
    });
  }
  editDialog(id: number): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      data: { comment: this.comment },
    });
  
    dialogRef.afterClosed().subscribe((result: string) => {
      if (result) {
        this.comment = result;
        

        const commentario = {
          id:  id,
          comment: result,
        };

        fetch('http://localhost:8080/comment', {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${this.currentUser.token}`
          },
          body: JSON.stringify(commentario)
        })
          .then((response) => response.json())
          .then((data) => {
            location.reload();
          })
          location.reload();
      }
    });
    
  }
  deletDialog(id: number): void {
    fetch(`http://localhost:8080/comment/${id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.currentUser.token}`
      },
    })
    location.reload();
    }
  deletPic(id: number): void {
    fetch(`http://localhost:8080/images/${id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.currentUser.token}`
      },
    })
    location.reload();
    }
}