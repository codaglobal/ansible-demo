/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coda.demo.controller;

import com.coda.demo.controller.exceptions.NonexistentEntityException;
import com.coda.demo.controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.coda.demo.entity.Author;
import com.coda.demo.entity.Quote;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 * @author bilorge May 5, 2019 11:44:18 AM
 */
public class QuoteJpaController implements Serializable {

    public QuoteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List getAllQuotes() {
        EntityManager em = getEntityManager();
        try {
            String qry  ="select q.quote, a.author from quote q join author a on q.idauthor = a.idauthor";
            Query natq = em.createNativeQuery(qry);
            List quotes = natq.getResultList();
            return quotes;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void create(Quote quote) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Author idauthor = quote.getIdauthor();
            if (idauthor != null) {
                idauthor = em.getReference(idauthor.getClass(), idauthor.getIdauthor());
                quote.setIdauthor(idauthor);
            }
            em.persist(quote);
            if (idauthor != null) {
                idauthor.getQuoteCollection().add(quote);
                idauthor = em.merge(idauthor);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findQuote(quote.getIdquote()) != null) {
                throw new PreexistingEntityException("Quote " + quote + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Quote quote) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Quote persistentQuote = em.find(Quote.class, quote.getIdquote());
            Author idauthorOld = persistentQuote.getIdauthor();
            Author idauthorNew = quote.getIdauthor();
            if (idauthorNew != null) {
                idauthorNew = em.getReference(idauthorNew.getClass(), idauthorNew.getIdauthor());
                quote.setIdauthor(idauthorNew);
            }
            quote = em.merge(quote);
            if (idauthorOld != null && !idauthorOld.equals(idauthorNew)) {
                idauthorOld.getQuoteCollection().remove(quote);
                idauthorOld = em.merge(idauthorOld);
            }
            if (idauthorNew != null && !idauthorNew.equals(idauthorOld)) {
                idauthorNew.getQuoteCollection().add(quote);
                idauthorNew = em.merge(idauthorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = quote.getIdquote();
                if (findQuote(id) == null) {
                    throw new NonexistentEntityException("The quote with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Quote quote;
            try {
                quote = em.getReference(Quote.class, id);
                quote.getIdquote();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The quote with id " + id + " no longer exists.", enfe);
            }
            Author idauthor = quote.getIdauthor();
            if (idauthor != null) {
                idauthor.getQuoteCollection().remove(quote);
                idauthor = em.merge(idauthor);
            }
            em.remove(quote);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Quote> findQuoteEntities() {
        return findQuoteEntities(true, -1, -1);
    }

    public List<Quote> findQuoteEntities(int maxResults, int firstResult) {
        return findQuoteEntities(false, maxResults, firstResult);
    }

    private List<Quote> findQuoteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Quote.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Quote findQuote(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Quote.class, id);
        } finally {
            em.close();
        }
    }

    public Quote findQuoteByQuote(String quote) {
        EntityManager em = getEntityManager();
        try {
            Query qry = em.createNamedQuery("Quote.findByQuote");
            qry.setParameter("quote", quote);
            Quote q = (Quote) qry.getSingleResult();
            
            return q;
        } catch(NoResultException x) {
            return null;
        } finally {
            em.close();
        }
    }

    public int getQuoteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Quote> rt = cq.from(Quote.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
