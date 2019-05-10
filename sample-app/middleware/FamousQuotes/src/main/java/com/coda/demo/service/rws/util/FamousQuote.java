/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.coda.demo.service.rws.util;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author bilorge May 5, 2019 4:29:25 PM
 */
@XmlRootElement
public class FamousQuote {
    private String author;
    private String quote;

    public FamousQuote() {
    }

    public FamousQuote(String author, String quote) {
        this.author = author;
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
