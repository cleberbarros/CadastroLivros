import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Livro } from '../../shared/models/livro.model';
import { LivroService } from '../../shared/services/livro.service';
import { ChangeDetectorRef } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { LivroCreateComponent } from '../livro-create/livro-create.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../shared/services/auth.service';
import { switchMap } from 'rxjs';

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
    MatChipsModule,
    MatPaginatorModule,
  ],
  selector: 'app-livro-list',
  templateUrl: './livro-list.component.html',
  styleUrls: ['./livro-list.component.css']
})
export class LivroListComponent implements OnInit {
  livros: Livro[] = [];
  userInfo: any;
  userId: number = 0;
  filter: string = '';
  totalLivros = 0;
  pageSize = 12;
  currentPage = 0;

  constructor(private route: ActivatedRoute,
    private livroService: LivroService,
    private authService: AuthService,
    private cd: ChangeDetectorRef,
    public dialog: MatDialog,
    
    private snackBar: MatSnackBar,) { }

  ngOnInit() {
    this.authService.getUserInfo().pipe(
      switchMap((userInfo) => {
        this.userInfo = userInfo;
        this.userId = this.userInfo.id;
        return this.route.paramMap;
      })
    ).subscribe(params => {
      this.loadLivros(this.userId, this.filter, this.currentPage, this.pageSize);

      if (this.userInfo.roles.includes('MANAGER')) {
  
      }
    });
  }

  loadLivros(userId: number, filter: string, page: number, size: number) {
    this.livroService.getLivrosByUserIdAndPage(userId, filter, page, size).subscribe(response => {
      this.livros = response.content;
      this.totalLivros = response.totalElements;
      this.cd.detectChanges();
     
    });
  }

  onFilterChange(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.filter = filterValue;
    this.loadLivros(this.userId, this.filter, this.currentPage, this.pageSize);
  }

  openNewLivroModal(): void {
    const dialogRef = this.dialog.open(LivroCreateComponent, {
      width: '1000px',
      data: { userId: this.userInfo.id }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.route.paramMap.subscribe(params => {
        this.loadLivros(this.userId, this.filter, this.currentPage, this.pageSize);
      });
    });
  }

  openLivroDetails(livro: any): void {
    this.dialog.open(LivroCreateComponent, {
      width: '1000px',
      data: livro
    });
  }


  onPageChange(event: any) {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadLivros(this.userId, this.filter, this.currentPage, this.pageSize);
  }

  handleNewLivro(newLivro: Livro) {
    this.snackBar.open(`Um novo livro criado: ${newLivro.titulo}`, 'Close', {
      duration: 5000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }




  
  editLivro(livro: Livro): void {
    
    const dialogRef = this.dialog.open(LivroCreateComponent, {
      width: '1000px',
      data: livro
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.loadLivros(this.userId, this.filter, this.currentPage, this.pageSize);
      }
    });
  }
  
  deleteLivro(livroId: number): void {
    if (confirm('Tem certeza que deseja excluir este livro?')) {
     /* this.livroService.deleteLivro(livroId).subscribe({
        next: () => {
          this.snackBar.open('Livro excluído com sucesso', 'Fechar', {
            duration: 3000,
          });
          this.loadLivros(this.userId, this.filter, this.currentPage, this.pageSize);
        },
        error: (err) => {
          console.error('Erro ao excluir o livro', err);
          this.snackBar.open('Erro ao excluir o livro', 'Fechar', {
            duration: 3000,
          });
        }
      });*/
    }
  }
  

}
