import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AssuntoService } from '../../shared/services/assunto.service';
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { LivroService } from '../../shared/services/livro.service';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule, DatePipe } from '@angular/common';
import { MatChipsModule } from '@angular/material/chips';
import { MatAutocompleteModule, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete'; // Aqui deve estar o MatAutocompleteSelectedEvent
import { Router } from '@angular/router';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-assunto-create',
  templateUrl: './assunto-create.component.html',
  styleUrls: ['./assunto-create.component.css'],
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
export class AssuntoCreateComponent implements OnInit {
  assuntoForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private assuntoService: AssuntoService,
    public dialogRef: MatDialogRef<AssuntoCreateComponent>,
    private snackBar: MatSnackBar,
    private router: Router,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.assuntoForm = this.fb.group({
      id: [null],
      descricao: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    if (this.data && this.data.id) {
      this.assuntoForm.patchValue(this.data);
    }
  }

  get descricao() {
    return this.assuntoForm.get('descricao');
  }

  getDescricaoErrorMessage() {
    if (this.descricao?.hasError('required')) {
      return 'Descrição é obrigatória';
    }
    return '';
  }

  onSubmit() {
    if (this.assuntoForm.valid) {
      const assunto = this.assuntoForm.value;
      if (assunto.id) {
        this.assuntoService.updateAssunto(assunto).subscribe({
          next: () => {
            this.snackBar.open('Assunto atualizado com sucesso', 'Fechar', { duration: 3000 });
            this.dialogRef.close(true);
          },
          error: () => {
            this.snackBar.open('Erro ao atualizar assunto', 'Fechar', { duration: 3000 });
          }
        });
      } else {
        this.assuntoService.createAssunto(assunto).subscribe({
          next: () => {
            this.snackBar.open('Assunto criado com sucesso', 'Fechar', { duration: 3000 });
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
