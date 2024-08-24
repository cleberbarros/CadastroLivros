import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { LivroService } from '../../shared/services/livro.service';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { CommonModule, DatePipe } from '@angular/common';
import { Livro } from '../../shared/models/livro.model';
import { MatChipsModule } from '@angular/material/chips';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { Observable, of } from 'rxjs';
import { AutorDTO } from '../../shared/models/autor.model';
import { AssuntoDTO } from '../../shared/models/assunto.model';
import { startWith, map, switchMap } from 'rxjs/operators';
import { AutorService } from '../../shared/services/autor.service';
import { AssuntoService } from '../../shared/services/assunto.service';
import { MatAutocompleteModule, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete'; // Aqui deve estar o MatAutocompleteSelectedEvent
import { Router } from '@angular/router';

@Component({
  selector: 'app-livro-create',
  templateUrl: './livro-create.component.html',
  styleUrls: ['./livro-create.component.css'],
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
export class LivroCreateComponent implements OnInit {
  livroForm: FormGroup;
  

  autorControl = new FormControl('');
  autoresFiltrados!: Observable<AutorDTO[]>;


  assuntoControl = new FormControl('');
  assuntosFiltrados!: Observable<AssuntoDTO[]>;


  constructor(
    private fb: FormBuilder,
    private livroService: LivroService,
    public dialogRef: MatDialogRef<LivroCreateComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private snackBar: MatSnackBar,
    private autorService: AutorService,
    private assuntoService: AssuntoService,
    private router: Router
  ) {
    this.livroForm = this.fb.group({
      id: [''],
      titulo: ['', Validators.required],
      editora: ['', Validators.required],
      edicao: ['', Validators.required],
      anoPublicacao: ['', Validators.required],
      autores: [[], Validators.required],
      assuntos: [[], Validators.required],

    });

    if (this.data.id) {
      this.livroForm.patchValue(this.data);
      // this.livroForm.disable();
    }
  }

  ngOnInit(): void {

  }

  buscarAutores(value: string): Observable<AutorDTO[]> {
    if (value.length < 3) {
      return of([]);
    }
    return this.autorService.buscarPorNome(value);
  }

  buscarAssuntos(value: string): Observable<AssuntoDTO[]> {
    if (value.length < 3) {
      return of([]);
    }
    return this.assuntoService.buscarPorDescricao(value);
  }

  onAutorInput(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    const value = inputElement.value;
    this.autoresFiltrados = this.buscarAutores(value);
  }

  displayAutorName(autor?: AutorDTO): string {
    return autor ? autor.nome : '';
  }

  addAutor(event: MatAutocompleteSelectedEvent): void {
    const autorSelecionado = event.option.value;
    const autores = this.livroForm.get('autores')?.value || [];

    // Verifica se o autor já foi adicionado
    if (!autores.some((autor: AutorDTO) => autor.id === autorSelecionado.id)) {
      autores.push(autorSelecionado);
      this.livroForm.get('autores')?.setValue(autores);
    }

    // Limpa o campo de entrada para permitir a adição de mais autores
    this.autorControl.setValue('');
  }


  removeAutor(autorId: number): void {
    const autores = this.livroForm.get('autores')?.value;
    this.livroForm.get('autores')?.setValue(autores.filter((id: number) => id !== autorId));
    console.log('autores-->',this.livroForm.get('autores'))
  }


  //Assuntos
  onAssuntoInput(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    const value = inputElement.value;
    this.assuntosFiltrados = this.buscarAssuntos(value);
  }

  displayAssuntoName(assunto?: AssuntoDTO): string {
    return assunto ? assunto.descricao : '';
  }

  addAssunto(event: MatAutocompleteSelectedEvent): void {
    const assuntoSelecionado = event.option.value;
    const assuntos = this.livroForm.get('assuntos')?.value || [];

    // Verifica se o assunto já foi adicionado
    if (!assuntos.some((assunto: AssuntoDTO) => assunto.id === assuntoSelecionado.id)) {
      assuntos.push(assuntoSelecionado);
      this.livroForm.get('assuntos')?.setValue(assuntos);
    }

    // Limpa o campo de entrada para permitir a adição de mais autores
    this.autorControl.setValue('');
  }


  removeAssunto(autorId: number): void {
    const assuntos = this.livroForm.get('assuntos')?.value;
    this.livroForm.get('assuntos')?.setValue(assuntos.filter((id: number) => id !== autorId));
    console.log('assuntos-->',this.livroForm.get('assuntos'))
  }

  saveLivro(): void {
    if (this.livroForm.valid) {
      const newLivro = { ...this.livroForm.value, userId: this.data.userId };
      if (!this.data.id) {
        this.livroService.createLivro(newLivro).subscribe({
          next: (livro) => {
            this.snackBar.open('Livro salvo com sucesso', 'Close', { duration: 3000 });
            this.dialogRef.close(livro);
          },
          error: (error) => {
            console.error('Contem erro', error);
            this.snackBar.open('Erro ao salvar livro', 'Close', { duration: 3000 });
          }
        });
      } else {
        this.livroService.updateLivro(this.data.id, newLivro).subscribe({
          next: (livro) => {
            this.snackBar.open('Livro alterado com sucesso', 'Close', { duration: 3000 });
            this.router.navigateByUrl('/');
            this.dialogRef.close(livro);
          },
          error: (error) => {
            console.error('Erro ao atualizar livro', error);
            this.snackBar.open('Erro ao atualizar livro', 'Close', { duration: 3000 });
          }
        });
      }
    }
  }



}



