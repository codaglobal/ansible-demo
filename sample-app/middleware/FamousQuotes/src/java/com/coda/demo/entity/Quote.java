/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.coda.demo.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author bilorge May 5, 2019 11:43:11 AM
 */
@Entity
@Table(catalog = "famous_quotes", name  = "quote", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idquote"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Quote.findAll", query = "SELECT q FROM Quote q")
    , @NamedQuery(name = "Quote.findByQuote", query = "SELECT q FROM Quote q WHERE q.quote = :quote")
    , @NamedQuery(name = "Quote.findByIdquote", query = "SELECT q FROM Quote q WHERE q.idquote = :idquote")})
public class Quote implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false, length = 36)
    private String idquote;
    @Basic(optional = false)
    @Lob
    @Column(nullable = false, length = 16777215)
    private String quote;
    @JoinColumn(name = "idauthor", referencedColumnName = "idauthor", nullable = false)
    @ManyToOne(optional = false)
    private Author idauthor;

    public Quote() {
    }

    public Quote(String idquote) {
        this.idquote = idquote;
    }

    public Quote(String quote, Author idauthor) {
        this.quote = quote;
        this.idauthor = idauthor;
    }

    public Quote(String idquote, String quote, Author idauthor) {
        this.idquote = idquote;
        this.quote = quote;
        this.idauthor = idauthor;
    }
    
    
    
    public Quote(String idquote, String quote) {
        this.idquote = idquote;
        this.quote = quote;
    }

    public String getIdquote() {
        return idquote;
    }

    public void setIdquote(String idquote) {
        this.idquote = idquote;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public Author getIdauthor() {
        return idauthor;
    }

    public void setIdauthor(Author idauthor) {
        this.idauthor = idauthor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idquote != null ? idquote.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Quote)) {
            return false;
        }
        Quote other = (Quote) object;
        if ((this.idquote == null && other.idquote != null) || (this.idquote != null && !this.idquote.equals(other.idquote))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.coda.demo.entity.Quote[ idquote=" + idquote + " ]";
    }

}
