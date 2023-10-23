import { Component, ElementRef, ViewChild } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AuthService } from 'src/app/shared/auth.service';

@Component({
  selector: 'app-dialog-pic',
  templateUrl: './dialog-pic.component.html',
  styleUrls: ['./dialog-pic.component.css']
})
export class DialogPicComponent {
  @ViewChild('fileInput')
  fileInput!: ElementRef;
  currentUser: any

  constructor(public dialogRef: MatDialogRef<DialogPicComponent>, private authService: AuthService) {
    this.authService.getUserByToken().then((response: any) => {
      this.currentUser = response;
    });
  }
  
  


  openFileInput() {
    this.fileInput.nativeElement.click();
  }


  onFileSelected(event: Event) {
    const fileInput = event.target as HTMLInputElement;
    const imagePreview = document.getElementById('imagePreview') as HTMLImageElement;
  
    if (fileInput.files && fileInput.files[0]) {
      const selectedFile = fileInput.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        const result = reader.result as string;
        const base64 = result.split(',')[1]; 
        const jsonData = {
          fileName: selectedFile.name,
          mimeType: selectedFile.type,
          base64: base64,
          user_id: this.currentUser.id
        };
  
        console.log(jsonData);        
        imagePreview.src = result;
        imagePreview.style.display = 'block';

        fetch('http://localhost:8080/images/upload', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${this.currentUser.token}`

          },
          body: JSON.stringify(jsonData)
        })
          .then((response) => response.json())
          .then((data) => {
            location.reload();
          })
      };
      reader.readAsDataURL(selectedFile);
    } else {
      imagePreview.src = '';
      imagePreview.style.display = 'none';
    }
  }
}
