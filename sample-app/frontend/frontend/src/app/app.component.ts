import {Component, OnInit} from '@angular/core';
import { QuotesService } from './services/rws/quotes.service';
import {Quotes} from './util/quotes';
import {NgForm} from '@angular/forms';
import {Quote} from './util/quote';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'frontend';
  quotes: Quotes;

  constructor(private svc: QuotesService) {
    this.gitr();
  }

  ngOnInit() { }

  gitr() {
    this.svc.getAllQoutes().subscribe((resp) => {
      this.quotes = resp.body;
    }, error1 => {
      console.log('Error Message: ', error1);
    });
  }

  postSomeWisdom(frm: NgForm) {
    const quote = new Quote();
    const vals = frm.value;
    quote.author = vals.iauthor;
    quote.quote = vals.iquote;

    this.svc.postNewQuote(quote).subscribe(value => {
      frm.resetForm();
      this.gitr();
    }, err => {
      console.log('DOH!:', err);
    });
  }
}
