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
import { VendaService } from '../../shared/services/venda.service';
import { VendaDTO } from '../../shared/models/venda.model';
import { VendaCreateComponent } from '../venda-create/venda-create.component';


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
  selector: 'app-venda-list',
  templateUrl: './venda-list.component.html',
  styleUrls: ['./venda-list.component.css']
})
export class VendaListComponent implements OnInit {
  vendas: VendaDTO[] = [];
  filter: string = '';
  totalVendas = 0;
  pageSize = 12;
  currentPage = 0;

  constructor(private route: ActivatedRoute,
    private vendaService: VendaService,
    private cd: ChangeDetectorRef,
    public dialog: MatDialog,
    private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.loadVendas(this.filter, this.currentPage, this.pageSize);
  }

  loadVendas(filter: string, page: number, size: number) {
    this.vendaService.getVendas(filter, page, size).subscribe(response => {
      this.vendas = response.content;
      this.totalVendas = response.totalElements;
      this.cd.detectChanges();
    });
  }

  onFilterChange(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.filter = filterValue;
    this.loadVendas(this.filter, this.currentPage, this.pageSize);
  }

  openNewVendaModal(): void {
    const dialogRef = this.dialog.open(VendaCreateComponent, {
      width: '1000px',
      
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadVendas(this.filter, this.currentPage, this.pageSize);
    });
  }

  openVendaDetails(autor: any): void {
    this.dialog.open(VendaCreateComponent, {
      width: '1000px',
      data: autor
    });
  }

  onPageChange(event: any) {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadVendas(this.filter, this.currentPage, this.pageSize);
  }

  deleteVenda(id: number): void {
    if (confirm('Tem certeza que deseja excluir este Formato de Venda?')) {
      this.vendaService.deleteVenda(id).subscribe({
        next: () => {
          this.snackBar.open('Formato de Venda excluído com sucesso', 'Fechar', { duration: 3000 });
          this.loadVendas(this.filter, this.currentPage, this.pageSize);
        },
        error: (error) => {
          const errorMessage = error.error || 'Erro ao excluir Venda. Por favor, tente novamente mais tarde.';
          this.snackBar.open(errorMessage, 'Fechar', { duration: 5000 });
        }
      });
    }
  }
}
