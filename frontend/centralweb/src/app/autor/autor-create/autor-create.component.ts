import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AutorService } from '../../shared/services/autor.service';
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule, DatePipe } from '@angular/common';
import { MatChipsModule } from '@angular/material/chips';
import { MatAutocompleteModule, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete'; // Aqui deve estar o MatAutocompleteSelectedEvent
import { Router } from '@angular/router';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-autor-create',
  templateUrl: './autor-create.component.html',
  styleUrls: ['./autor-create.component.css'],
  standalone: true,
  imports: [
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatIconModule,
    MatSnackBarModule,
    CommonModule,
    MatChipsModule,
    MatAutocompleteModule

  ],
  providers: [DatePipe],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AutorCreateComponent implements OnInit {
  autorForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private autorService:AutorService,
    public dialogRef: MatDialogRef<AutorCreateComponent>,
    private snackBar: MatSnackBar,
    private router: Router,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.autorForm = this.fb.group({
      id: [null],
      nome: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    if (this.data && this.data.id) {
      this.autorForm.patchValue(this.data);
    }
  }

  get descricao() {
    return this.autorForm.get('nome');
  }

  getDescricaoErrorMessage() {
    if (this.descricao?.hasError('required')) {
      return 'Nome é obrigatório';
    }
    return '';
  }

  onSubmit() {
    if (this.autorForm.valid) {
      const autor = this.autorForm.value;
      if (autor.id) {
        this.autorService.updateAutor(autor).subscribe({
          next: () => {
            this.snackBar.open('Autor atualizado com sucesso', 'Fechar', { duration: 3000 });
            this.dialogRef.close(true);
          },
          error: () => {
            this.snackBar.open('Erro ao atualizar assunto', 'Fechar', { duration: 3000 });
          }
        });
      } else {
        this.autorService.createAutor(autor).subscribe({
          next: () => {
            this.snackBar.open('Autor criado com sucesso', 'Fechar', { duration: 3000 });
            this.dialogRef.close(true);
          },
          error: () => {
            this.snackBar.open('Erro ao criar assunto', 'Fechar', { duration: 3000 });
          }
        });
      }
    }
  }
}
