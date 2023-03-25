/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.LibraryMember;
import entity.Staff;
import exception.BookExistException;
import exception.EntityManagerException;
import exception.StaffExistException;
import exception.StaffNotFoundException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author lhern
 */
@Singleton
@LocalBean
@Startup
public class DataInitializationSessionBean {

    @EJB
    private StaffSessionBeanLocal staffSessionBeanLocal;
    @EJB
    private MemberSessionBeanLocal memberSessionBeanLocal;

    public DataInitializationSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            Staff eric = staffSessionBeanLocal.retrieveStaffByUsername("eric");
        } catch (StaffNotFoundException ex) {
            initializeData();
        }
    }

    private void initializeData() {
        try {

            staffSessionBeanLocal.createNewStaff(new Staff("Eric", "Some", "eric", "password"));
            staffSessionBeanLocal.createNewStaff(new Staff("Sarah", "Brightman", "sarah", "password"));
            
            memberSessionBeanLocal.createNewBook(new Book("Anna Karenina", "0451528611", "Leo Tolstoy"));
            memberSessionBeanLocal.createNewBook(new Book("Madame Bovary", "979-8649042031", "Gustave Flaubert"));
            memberSessionBeanLocal.createNewBook(new Book("Hamlet", "1980625026", "William Shakespeare"));
            memberSessionBeanLocal.createNewBook(new Book("The Hobbit", "9780007458424", "J R R Tolkien"));
            memberSessionBeanLocal.createNewBook(new Book("Great Expectations", "1521853592", "Charles Dickens"));
            memberSessionBeanLocal.createNewBook(new Book("Pride and Prejudice", "979-8653642272", "Jane Austen"));
            memberSessionBeanLocal.createNewBook(new Book("Wuthering Heights", "3961300224", "Emily Brontë"));

            memberSessionBeanLocal.registerNewMember(new LibraryMember("Tony", "Shade", 'M', 31, "S8900678A", "83722773", "13 Jurong East, Ave 3"));
            memberSessionBeanLocal.registerNewMember(new LibraryMember("Dewi", "Tan", 'F', 35, "S8581028X", "94602711", "15 Computing Dr"));
        } catch (StaffExistException  | BookExistException | EntityManagerException  ex) {
            ex.printStackTrace();
        }
    }
}
