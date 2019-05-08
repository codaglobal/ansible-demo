import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Quotes} from '../../util/quotes';
import {Quote} from '../../util/quote';

@Injectable({providedIn: 'root'})
export class QuotesService {
  // baseurl = 'http://localhost:8080/FamousQuotes/webresources/quotes';
  baseurl = 'http://18.236.245.191/FamousQuotes/webresources/quotes';

  private httpOptions = {

    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      accept: 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  getAllQoutes() {
    return this.http.get<Quotes>(this.baseurl, {observe: 'response'});
  }

  postNewQuote(wow: Quote) {
    return this.http.post(this.baseurl, wow, this.httpOptions);
  }
}
