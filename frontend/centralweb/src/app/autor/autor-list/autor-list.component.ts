import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ChangeDetectorRef } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AutorService } from '../../shared/services/autor.service';
import { AutorDTO } from '../../shared/models/autor.model';
import { AutorCreateComponent } from '../autor-create/autor-create.component';


@Component({
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatToolbarModule,
    MatIconModule,
    MatPaginatorModule,
  ],
  selector: 'app-autor-list',
  templateUrl: './autor-list.component.html',
  styleUrls: ['./autor-list.component.css']
})
export class AutorListComponent implements OnInit {
  autores: AutorDTO[] = [];
  filter: string = '';
  totalAutores = 0;
  pageSize = 12;
  currentPage = 0;

  constructor(private route: ActivatedRoute,
    private autorService: AutorService,
    private cd: ChangeDetectorRef,
    public dialog: MatDialog,
    private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.loadAutores(this.filter, this.currentPage, this.pageSize);
  }

  loadAutores(filter: string, page: number, size: number) {
    this.autorService.getAutores(filter, page, size).subscribe(response => {
      this.autores = response.content;
      this.totalAutores = response.totalElements;
      this.cd.detectChanges();
    });
  }

  onFilterChange(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.filter = filterValue;
    this.loadAutores(this.filter, this.currentPage, this.pageSize);
  }

  openNewAutorModal(): void {
    const dialogRef = this.dialog.open(AutorCreateComponent, {
      width: '1000px',
      height: '200px',
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadAutores(this.filter, this.currentPage, this.pageSize);
    });
  }

  openAutorDetails(autor: any): void {
    this.dialog.open(AutorCreateComponent, {
      width: '1000px',
      data: autor
    });
  }

  onPageChange(event: any) {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadAutores(this.filter, this.currentPage, this.pageSize);
  }

  deleteAutor(id: number): void {
    if (confirm('Tem certeza que deseja excluir este autor?')) {
      this.autorService.deleteAutor(id).subscribe({
        next: () => {
          this.snackBar.open('Autor excluído com sucesso', 'Fechar', { duration: 3000 });
          this.loadAutores(this.filter, this.currentPage, this.pageSize);
        },
        error: (error) => {
          const errorMessage = error.error || 'Erro ao excluir assunto. Por favor, tente novamente mais tarde.';
          this.snackBar.open(errorMessage, 'Fechar', { duration: 5000 });
        }
      });
    }
  }
}
