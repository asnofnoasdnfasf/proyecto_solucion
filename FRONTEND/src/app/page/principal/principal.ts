import { Component, OnInit, inject } from '@angular/core';
import { RouterLink } from '@angular/router'; 
import { ElectromaticApiService } from '../../services/electromatic-api.service';

@Component({
  selector: 'app-principal',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './principal.html',
  styleUrl: './principal.css'
})
export class Principal implements OnInit {
  private api = inject(ElectromaticApiService);
  indicadores: Record<string, number> = {};
  ngOnInit(): void {
    this.api.indicadores().subscribe({ next: data => this.indicadores = data as Record<string, number>, error: () => this.indicadores = {} });
  }
}
