/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coda.demo.controller;

import com.coda.demo.controller.exceptions.IllegalOrphanException;
import com.coda.demo.controller.exceptions.NonexistentEntityException;
import com.coda.demo.controller.exceptions.PreexistingEntityException;
import com.coda.demo.entity.Author;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.coda.demo.entity.Quote;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 * @author bilorge May 5, 2019 11:44:18 AM
 */
public class AuthorJpaController implements Serializable {

    public AuthorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Author author) throws PreexistingEntityException, Exception {
        if (author.getQuoteCollection() == null) {
            author.setQuoteCollection(new ArrayList<Quote>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Quote> attachedQuoteCollection = new ArrayList<Quote>();
            for (Quote quoteCollectionQuoteToAttach : author.getQuoteCollection()) {
                quoteCollectionQuoteToAttach = em.getReference(quoteCollectionQuoteToAttach.getClass(), quoteCollectionQuoteToAttach.getIdquote());
                attachedQuoteCollection.add(quoteCollectionQuoteToAttach);
            }
            author.setQuoteCollection(attachedQuoteCollection);
            em.persist(author);
            for (Quote quoteCollectionQuote : author.getQuoteCollection()) {
                Author oldIdauthorOfQuoteCollectionQuote = quoteCollectionQuote.getIdauthor();
                quoteCollectionQuote.setIdauthor(author);
                quoteCollectionQuote = em.merge(quoteCollectionQuote);
                if (oldIdauthorOfQuoteCollectionQuote != null) {
                    oldIdauthorOfQuoteCollectionQuote.getQuoteCollection().remove(quoteCollectionQuote);
                    oldIdauthorOfQuoteCollectionQuote = em.merge(oldIdauthorOfQuoteCollectionQuote);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAuthor(author.getIdauthor()) != null) {
                throw new PreexistingEntityException("Author " + author + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Author author) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Author persistentAuthor = em.find(Author.class, author.getIdauthor());
            Collection<Quote> quoteCollectionOld = persistentAuthor.getQuoteCollection();
            Collection<Quote> quoteCollectionNew = author.getQuoteCollection();
            List<String> illegalOrphanMessages = null;
            for (Quote quoteCollectionOldQuote : quoteCollectionOld) {
                if (!quoteCollectionNew.contains(quoteCollectionOldQuote)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Quote " + quoteCollectionOldQuote + " since its idauthor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Quote> attachedQuoteCollectionNew = new ArrayList<Quote>();
            for (Quote quoteCollectionNewQuoteToAttach : quoteCollectionNew) {
                quoteCollectionNewQuoteToAttach = em.getReference(quoteCollectionNewQuoteToAttach.getClass(), quoteCollectionNewQuoteToAttach.getIdquote());
                attachedQuoteCollectionNew.add(quoteCollectionNewQuoteToAttach);
            }
            quoteCollectionNew = attachedQuoteCollectionNew;
            author.setQuoteCollection(quoteCollectionNew);
            author = em.merge(author);
            for (Quote quoteCollectionNewQuote : quoteCollectionNew) {
                if (!quoteCollectionOld.contains(quoteCollectionNewQuote)) {
                    Author oldIdauthorOfQuoteCollectionNewQuote = quoteCollectionNewQuote.getIdauthor();
                    quoteCollectionNewQuote.setIdauthor(author);
                    quoteCollectionNewQuote = em.merge(quoteCollectionNewQuote);
                    if (oldIdauthorOfQuoteCollectionNewQuote != null && !oldIdauthorOfQuoteCollectionNewQuote.equals(author)) {
                        oldIdauthorOfQuoteCollectionNewQuote.getQuoteCollection().remove(quoteCollectionNewQuote);
                        oldIdauthorOfQuoteCollectionNewQuote = em.merge(oldIdauthorOfQuoteCollectionNewQuote);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = author.getIdauthor();
                if (findAuthor(id) == null) {
                    throw new NonexistentEntityException("The author with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Author author;
            try {
                author = em.getReference(Author.class, id);
                author.getIdauthor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The author with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Quote> quoteCollectionOrphanCheck = author.getQuoteCollection();
            for (Quote quoteCollectionOrphanCheckQuote : quoteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Author (" + author + ") cannot be destroyed since the Quote " + quoteCollectionOrphanCheckQuote + " in its quoteCollection field has a non-nullable idauthor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(author);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Author> findAuthorEntities() {
        return findAuthorEntities(true, -1, -1);
    }

    public List<Author> findAuthorEntities(int maxResults, int firstResult) {
        return findAuthorEntities(false, maxResults, firstResult);
    }

    private List<Author> findAuthorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Author.class));
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

    public Author findAuthor(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Author.class, id);
        } finally {
            em.close();
        }
    }

    public Author findAuthorByName(String name) {
        EntityManager em = getEntityManager();
        try {
            Query qry = em.createNamedQuery("Author.findByAuthorName");
            qry.setParameter("authorName", name);
            Author author = (Author) qry.getSingleResult();
            return author;
        } catch (NoResultException x) {
            return null;
        } finally {
            em.close();
        }
    }

    public int getAuthorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Author> rt = cq.from(Author.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
