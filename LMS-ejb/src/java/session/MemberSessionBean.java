/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.LendAndReturn;
import entity.LibraryMember;
import exception.BookExistException;
import exception.BookNotAvailableException;
import exception.BookNotFoundException;
import exception.EntityManagerException;
import exception.FineNotPaidException;
import exception.LendingNotFoundException;
import exception.MemberExistException;
import exception.MemberNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author lhern
 */
@Stateless
public class MemberSessionBean implements MemberSessionBeanLocal {

    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public MemberSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public LibraryMember retrieveMemberByIdentityNo(String identityNo) throws MemberNotFoundException {
        Query query = em.createQuery("SELECT m FROM LibraryMember m WHERE m.identityNo = :id");
        query.setParameter("id", identityNo);

        try {
            return (LibraryMember) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new MemberNotFoundException("Member with identity number " + identityNo + " does not exist!");
        }
    }

    @Override
    public List<LibraryMember> searchMembersByFirstName(String firstName) {
        Query q;
        if (firstName != null) {
            q = em.createQuery("SELECT m FROM LibraryMember m WHERE "
                    + "LOWER(m.firstName) LIKE :name");
            q.setParameter("name", "%" + firstName.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT m FROM LibraryMember m");
        }
        return q.getResultList();
    } //end searchMembersByFirstName

    @Override
    public List<LibraryMember> searchMembersByLastName(String lastName) {
        Query q;
        if (lastName != null) {
            q = em.createQuery("SELECT m FROM LibraryMember m WHERE "
                    + "LOWER(m.lastName) LIKE :name");
            q.setParameter("name", "%" + lastName.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT m FROM LibraryMember m");
        }

        return q.getResultList();
    } //end searchMembersByLastName

    @Override
    public List<LibraryMember> searchMembersByIdNumber(String idNum) {
        Query q;
        if (idNum != null) {
            q = em.createQuery("SELECT m FROM LibraryMember m WHERE "
                    + "LOWER(m.identityNo) LIKE :id");
            q.setParameter("id", "%" + idNum.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT m FROM LibraryMember m");
        }

        return q.getResultList();
    } //end searchMembersByLastName

    @Override
    public List<LendAndReturn> searchLendsAndReturnsByIdNumber(String idNum) {
        Query q;
        if (idNum != null) {
            q = em.createQuery("SELECT l FROM LendAndReturn l WHERE "
                    + "LOWER(l.idNum) LIKE :id");
            q.setParameter("id", "%" + idNum.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT l FROM LendAndReturn l");
        }

        return q.getResultList();
    } //end searchLendsAndReturnsByIdNumber

    @Override
    public List<LendAndReturn> searchLendsAndReturnsByBookId(String bookId) {
        Query q;
        if (bookId != null) {
            q = em.createQuery("SELECT l FROM LendAndReturn l WHERE "
                    + "LOWER(l.bookId) LIKE :id");
            q.setParameter("id", "%" + bookId.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT l FROM LendAndReturn l");
        }

        return q.getResultList();
    } //end searchLendsAndReturnsByIdNumber
    
    @Override
    public List<Book> searchBooksByTitle(String title) {
        Query q;
        if (title != null) {
            q = em.createQuery("SELECT b FROM Book b WHERE "
                    + "LOWER(b.title) LIKE :title");
            q.setParameter("title", "%" + title.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT b FROM Book b");
        }
        return q.getResultList();
    } //end searchBooksByTitle
    
    @Override
    public List<Book> searchBooksByAuthor(String author) {
        Query q;
        if (author != null) {
            q = em.createQuery("SELECT b FROM Book b WHERE "
                    + "LOWER(b.author) LIKE :author");
            q.setParameter("author", "%" + author.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT b FROM Book b");
        }
        return q.getResultList();
    } //end searchBooksByAuthor
    
    @Override
    public List<Book> searchBooksByIsbn(String isbn) {
        Query q;
        if (isbn != null) {
            q = em.createQuery("SELECT b FROM Book b WHERE "
                    + "LOWER(b.isbn) LIKE :isbn");
            q.setParameter("isbn", "%" + isbn.toLowerCase() + "%");
        } else {
            q = em.createQuery("SELECT b FROM Book b");
        }
        return q.getResultList();
    } //end searchBooksByIsbn

    @Override
    public Long registerNewMember(String firstName, String lastName, char gender, int age, String idNum, String phone, String address) throws EntityManagerException, MemberExistException {

        Set<ConstraintViolation<String>> constraintViolations = validator.validate(idNum);

        if (constraintViolations.isEmpty()) {
            try {
                LibraryMember newMember = new LibraryMember(firstName, lastName, gender, age, idNum, phone, address);
                em.persist(newMember);
                em.flush();

                return newMember.getMemberId();
            } catch (Exception ex) {
                throw new EntityManagerException("Unable to register new member");
            }
        } else {
            throw new MemberExistException("Member already exist");
        }
    }

    @Override
    public void registerNewMember(LibraryMember m) {
        em.persist(m);
        em.flush();
    } //end registerNewMember

    @Override
    public long createNewBook(Book b) throws EntityManagerException, BookExistException {

        Set<ConstraintViolation<String>> constraintViolations = validator.validate(b.getIsbn());

        if (constraintViolations.isEmpty()) {
            try {

                em.persist(b);
                em.flush();

                return b.getBookId();
            } catch (Exception ex) {
                throw new EntityManagerException("Unable to create new book");
            }
        } else {
            throw new BookExistException("Book already exists");
        }

    }

    @Override
    public Book retrieveBookById(Long bookId) throws BookNotFoundException {
        Query query = em.createQuery("SELECT b FROM Book b WHERE b.bookId = :id");
        query.setParameter("id", bookId);
        try {
            return (Book) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new BookNotFoundException("Book with id " + bookId + " does not exist!");
        }
    }

    @Override
    public void lendBook(String idNum, Long bookId) throws MemberNotFoundException, BookNotFoundException, BookNotAvailableException {
        Book b = retrieveBookById(bookId);
        if (b.isAvailable()) {
        Date currentDate = new Date();
        LendAndReturn lend = new LendAndReturn(idNum, bookId, currentDate, null, 0);
            LibraryMember m = retrieveMemberByIdentityNo(idNum);
            m.getLendsAndReturns().add(lend);
            b.getLendsAndReturns().add(lend);
            b.setAvailable(false);
        em.persist(lend);
        em.flush();
        } else {
            throw new BookNotAvailableException("Book is not available");
        }
    }

    @Override
    public LendAndReturn retrieveReturnByMemberId(String idNum) throws LendingNotFoundException {
        Query query = em.createQuery("SELECT l FROM LendAndReturn l WHERE l.idNum = :id");
        query.setParameter("id", idNum);

        try {
            return (LendAndReturn) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new LendingNotFoundException("Lending or return with id " + idNum + " does not exist!");
        }
    }

    @Override
    public void returnBook(String idNum, Long bookId) throws BookNotFoundException, FineNotPaidException {
        try {

            LendAndReturn selectedReturn = retrieveReturnByMemberId(idNum);
            if (selectedReturn.getFineAmount() > 0) {
                throw new FineNotPaidException("Fine has not been paid. Return not allowed");
            }
            Date date = new Date();
            selectedReturn.setReturnDate(date);
            Book b = this.retrieveBookById(bookId);
            b.setAvailable(true);
        } catch (Exception ex) {
            throw new BookNotFoundException("Book is not found");
        }
    }
}
