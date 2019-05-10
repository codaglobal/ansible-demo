/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coda.demo.service.rws.quotes;

import com.coda.demo.controller.AuthorJpaController;
import com.coda.demo.controller.QuoteJpaController;
import com.coda.demo.entity.Author;
import com.coda.demo.entity.Quote;
import com.coda.demo.service.rws.util.FamousQuote;
import com.coda.demo.service.rws.util.Quotes;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author bilorge
 */
@Path("quotes")
public class FamousQuotesRWS {

    @Context
    private UriInfo context;
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("FamousQuotesPU");

    /**
     * Creates a new instance of FamousQuotesRWS
     */
    public FamousQuotesRWS() {
    }

    @GET
    @Path("ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "ECHO!";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getQuotes() throws Exception {
        QuoteJpaController jcon = new QuoteJpaController(emf);
        List allQuotes = jcon.getAllQuotes();
        Quotes quotes = new Quotes();
        
        for (Object q : allQuotes) {
            quotes.addQuote(new FamousQuote( (String) ((Object[]) q)[1], (String) ((Object[]) q)[0]));
        }

        Response.ResponseBuilder r = Response.ok(quotes);
        return r.build();
    }
    
    @GET
    @Path("sample/quotes")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response sample() {
        Response.ResponseBuilder r = Response.ok();
        Quotes quotes = new Quotes();
        quotes.addQuote(new FamousQuote("Trump", "GINAH!"));
        quotes.addQuote(new FamousQuote("Trump", "MY AHBRAIN IS SO SO SO SMART"));
        r.entity(quotes);
        return r.build();
    }
    
    @GET
    @Path("sample/quote")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response sampleFamousQoute() {
        Response.ResponseBuilder r = Response.ok();
        FamousQuote famousQuote = new FamousQuote("Trump", "MY AHBRAIN IS SO SO SO SMART");
        r.entity(famousQuote);
        return r.build();
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response postQuote(FamousQuote famousQuote) throws Exception {
        String authr;
        String qt;
        
        //always make sure there is something to post!
        if (famousQuote != null) {
            authr = famousQuote.getAuthor().trim();
            qt = famousQuote.getQuote().trim();
        } else {
            return Response.noContent().build();
        }

        if (qt.equals("")) {
            return Response.noContent().build();
        }
        
        //find if author exists... Well, determine if its from annonymous
        AuthorJpaController acon = new AuthorJpaController(emf);
        Author author = null;

        //make sure the quote has an author, if not, then make it anonymous.
        if (authr == null || authr.equals("")) {
            author = acon.findAuthorByName("Anonymous");
        } else {
            author = acon.findAuthorByName(authr);
        }
        
        if (author == null) {
            author = new Author(UUID.randomUUID().toString(), famousQuote.getAuthor());
            acon.create(author);
        }
        
        QuoteJpaController qcon = new QuoteJpaController(emf);
        Quote quote = qcon.findQuoteByQuote(qt);
        
        if (quote == null) {
            Quote newQuote = new Quote(UUID.randomUUID().toString(), famousQuote.getQuote(), author);
            qcon.create(newQuote);
        } //else the quoet already exists and it should not be accepted.
        
        return Response.accepted().build();
    }

}
