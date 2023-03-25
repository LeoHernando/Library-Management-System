/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.Book;
import entity.LendAndReturn;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import session.MemberSessionBeanLocal;

/**
 *
 * @author lhern
 */
@Named(value = "booksManagedBean")
@ViewScoped
public class BooksManagedBean implements Serializable {
    
     @EJB
    private MemberSessionBeanLocal memberSessionLocal;
    
    
    private List<Book> books;

    private String searchType = "TITLE";
    private String searchString;


    /**
     * Creates a new instance of BooksManagedBean
     */
    public BooksManagedBean() {
    }
    
    

    @PostConstruct
    public void init() {
        if (getSearchString() == null || getSearchString().equals("")) {
            setBooks(memberSessionLocal.searchBooksByTitle(null));

        } else {
            switch (getSearchType()) {
                case "TITLE":
                    setBooks(memberSessionLocal.searchBooksByTitle(searchString));
                    break;
                case "AUTHOR": {
                    setBooks(memberSessionLocal.searchBooksByAuthor(searchString));
                    break;
                }
                case "ISBN": {
                    setBooks(memberSessionLocal.searchBooksByIsbn(searchString));
                    break;
                }
                default:
                    setBooks(memberSessionLocal.searchBooksByTitle(searchString));
                    break;
            }
        }
    }


    public void handleSearch() {
        init();
    } //end handleSearch

    /**
     * @return the searchType
     */
    public String getSearchType() {
        return searchType;
    }

    /**
     * @param searchType the searchType to set
     */
    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    /**
     * @return the searchString
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * @param searchString the searchString to set
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    /**
     * @return the books
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * @param books the books to set
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }
    
}
