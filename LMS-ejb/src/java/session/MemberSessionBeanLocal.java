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
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lhern
 */
@Local
public interface MemberSessionBeanLocal {

    public LibraryMember retrieveMemberByIdentityNo(String identityNo) throws MemberNotFoundException;

    public List<LibraryMember> searchMembersByFirstName(String firstName);

    public List<LibraryMember> searchMembersByLastName(String lastName);

    public List<LibraryMember> searchMembersByIdNumber(String idNum);

    public Long registerNewMember(String firstName, String lastName, char gender, int age, String idNum, String phone, String address) throws EntityManagerException, MemberExistException;

    public void registerNewMember(LibraryMember m);

    public long createNewBook(Book b) throws EntityManagerException, BookExistException;

    public void lendBook(String idNum, Long bookId) throws MemberNotFoundException, BookNotFoundException, BookNotAvailableException;

    public LendAndReturn retrieveReturnByMemberId(String idNum) throws LendingNotFoundException;

    public void returnBook(String idNum, Long bookId) throws BookNotFoundException, FineNotPaidException;

    public Book retrieveBookById(Long bookId) throws BookNotFoundException;

    public List<LendAndReturn> searchLendsAndReturnsByIdNumber(String idNum);

    public List<LendAndReturn> searchLendsAndReturnsByBookId(String bookId);

    public List<Book> searchBooksByTitle(String title);

    public List<Book> searchBooksByAuthor(String author);

    public List<Book> searchBooksByIsbn(String isbn);
    
}
