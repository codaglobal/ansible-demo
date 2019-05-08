/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.coda.demo.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author bilorge May 5, 2019 11:43:11 AM
 */
@Entity
@Table(catalog = "famous_quotes", name = "author", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idauthor"})
    , @UniqueConstraint(columnNames = {"author"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Author.findAll", query = "SELECT a FROM Author a")
    , @NamedQuery(name = "Author.findByAuthorName", query = "SELECT a FROM Author a WHERE a.author = :authorName")
    , @NamedQuery(name = "Author.findByIdauthor", query = "SELECT a FROM Author a WHERE a.idauthor = :idauthor")
    , @NamedQuery(name = "Author.findByAuthor", query = "SELECT a FROM Author a WHERE a.author = :author")})
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false, length = 36)
    private String idauthor;
    @Basic(optional = false)
    @Column(nullable = false, length = 45)
    private String author;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idauthor")
    private Collection<Quote> quoteCollection;

    public Author() {
    }

    public Author(String idauthor) {
        this.idauthor = idauthor;
    }

    public Author(String idauthor, String author) {
        this.idauthor = idauthor;
        this.author = author;
    }

    public String getIdauthor() {
        return idauthor;
    }

    public void setIdauthor(String idauthor) {
        this.idauthor = idauthor;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @XmlTransient
    public Collection<Quote> getQuoteCollection() {
        return quoteCollection;
    }

    public void setQuoteCollection(Collection<Quote> quoteCollection) {
        this.quoteCollection = quoteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idauthor != null ? idauthor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Author)) {
            return false;
        }
        Author other = (Author) object;
        if ((this.idauthor == null && other.idauthor != null) || (this.idauthor != null && !this.idauthor.equals(other.idauthor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.coda.demo.entity.Author[ idauthor=" + idauthor + " ]";
    }

}
