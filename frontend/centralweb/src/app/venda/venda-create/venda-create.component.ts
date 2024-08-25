import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { VendaService } from '../../shared/services/venda.service';
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
  selector: 'app-venda-create',
  templateUrl: './venda-create.component.html',
  styleUrls: ['./venda-create.component.css'],
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
export class VendaCreateComponent implements OnInit {
  vendaForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private vendaService:VendaService,
    public dialogRef: MatDialogRef<VendaCreateComponent>,
    private snackBar: MatSnackBar,
    private router: Router,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.vendaForm = this.fb.group({
      id: [null],
      descricao: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    if (this.data && this.data.id) {
      this.vendaForm.patchValue(this.data);
    }
  }

  get descricao() {
    return this.vendaForm.get('nome');
  }

  getDescricaoErrorMessage() {
    if (this.descricao?.hasError('required')) {
      return 'Descrição é obrigatório';
    }
    return '';
  }

  onSubmit() {
    if (this.vendaForm.valid) {
      const venda = this.vendaForm.value;
      if (venda.id) {
        this.vendaService.updateVenda(venda).subscribe({
          next: () => {
            this.snackBar.open('Formato de Venda atualizado com sucesso', 'Fechar', { duration: 3000 });
            this.dialogRef.close(true);
          },
          error: () => {
            this.snackBar.open('Erro ao atualizar Formato de Venda', 'Fechar', { duration: 3000 });
          }
        });
      } else {
        this.vendaService.createVenda(venda).subscribe({
          next: () => {
            this.snackBar.open('Formato de Venda criado com sucesso', 'Fechar', { duration: 3000 });
            this.dialogRef.close(true);
          },
          error: () => {
            this.snackBar.open('Erro ao criar Formato de Venda', 'Fechar', { duration: 3000 });
          }
        });
      }
    }
  }
}
