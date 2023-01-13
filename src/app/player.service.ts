import { Injectable } from '@angular/core';
import { Player } from './player';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  private playersURL = 'http://localhost:8080/Players'

  constructor(
    private http: HttpClient) { }

  getPlayers(): Observable<Player[]> {
    return this.http.get<Player[]>(this.playersURL).pipe(
      catchError(this.handleError<Player[]>('getPlayers', []))
    );
  }

  getPlayer(id: number): Observable<Player> {
    const url = `${this.playersURL}/${id}`;
    return this.http.get<Player>(url).pipe(
      catchError(this.handleError<Player>(`getHero id=${id}`))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
  
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
  
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  searchPlayers(term: string): Observable<Player[]> {
    if (!term.trim()) {
      // if not search term, return empty hero array.
      return of([]);
    }
    return this.http.get<Player[]>(`${this.playersURL}/?name=${term}`).pipe(
      catchError(this.handleError<Player[]>('searchHeroes', []))
    );
  }
}
