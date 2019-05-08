/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.coda.demo.service.rws.util;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author bilorge May 5, 2019 9:46:54 PM
 */
@XmlRootElement
public class Quotes {
    
    private List<FamousQuote> quotes = new ArrayList<>();

    public Quotes() {
    }
    
    @XmlElement
    public List<FamousQuote> getQuotes() {
        return this.quotes;
    }
    
    public boolean addQuote(FamousQuote quote) {
        return this.quotes.add(quote);
    }
}