import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AssuntoDTO } from '../../shared/models/assunto.model';
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
import { AssuntoService } from '../../shared/services/assunto.service';
import { AssuntoCreateComponent } from '../assunto-create/assunto-create.component';

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
  selector: 'app-assunto-list',
  templateUrl: './assunto-list.component.html',
  styleUrls: ['./assunto-list.component.css']
})
export class AssuntoListComponent implements OnInit {
  assuntos: AssuntoDTO[] = [];
  filter: string = '';
  totalAssuntos = 0;
  pageSize = 12;
  currentPage = 0;

  constructor(private route: ActivatedRoute,
    private assuntoService: AssuntoService,
    private cd: ChangeDetectorRef,
    public dialog: MatDialog,
    private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.loadAssuntos(this.filter, this.currentPage, this.pageSize);
  }

  loadAssuntos(filter: string, page: number, size: number) {
    this.assuntoService.getAssuntos(filter, page, size).subscribe(response => {
      this.assuntos = response.content;
      this.totalAssuntos = response.totalElements;
      this.cd.detectChanges();
    });
  }

  onFilterChange(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.filter = filterValue;
    this.loadAssuntos(this.filter, this.currentPage, this.pageSize);
  }

  openNewAssuntoModal(): void {
    const dialogRef = this.dialog.open(AssuntoCreateComponent, {
      width: '1000px',
      height: '200px',
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadAssuntos(this.filter, this.currentPage, this.pageSize);
    });
  }

  openAssuntoDetails(assunto: any): void {
    this.dialog.open(AssuntoCreateComponent, {
      width: '1000px',
      data: assunto
    });
  }

  onPageChange(event: any) {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadAssuntos(this.filter, this.currentPage, this.pageSize);
  }

  deleteAssunto(id: number): void {
    if (confirm('Tem certeza que deseja excluir este assunto?')) {
      this.assuntoService.deleteAssunto(id).subscribe({
        next: () => {
          this.snackBar.open('Assunto excluído com sucesso', 'Fechar', { duration: 3000 });
          this.loadAssuntos(this.filter, this.currentPage, this.pageSize);
        },
        error: (error) => {
          const errorMessage = error.error || 'Erro ao excluir assunto. Por favor, tente novamente mais tarde.';
          this.snackBar.open(errorMessage, 'Fechar', { duration: 5000 });
        }
      });
    }
  }
}
