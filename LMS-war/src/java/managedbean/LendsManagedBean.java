/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import entity.LendAndReturn;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import session.MemberSessionBeanLocal;

/**
 *
 * @author lhern
 */
@Named(value = "lendsManagedBean")
@ViewScoped
public class LendsManagedBean implements Serializable {

    @EJB
    private MemberSessionBeanLocal memberSessionLocal;
    
    
    private List<LendAndReturn> lendsAndReturns;

    private String searchType = "IDNUMBER";
    private String searchString;

    /**
     * Creates a new instance of LendsManagedBean
     */
    public LendsManagedBean() {
    }

    @PostConstruct
    public void init() {
        if (getSearchString() == null || getSearchString().equals("")) {
            setLendsAndReturns(memberSessionLocal.searchLendsAndReturnsByIdNumber(null));

        } else {
            switch (getSearchType()) {
                case "IDNUMBER":
                    setLendsAndReturns(memberSessionLocal.searchLendsAndReturnsByIdNumber(getSearchString()));
                    break;
                case "BOOKID": {
                    setLendsAndReturns(memberSessionLocal.searchLendsAndReturnsByBookId(getSearchString()));
                    break;
                }
                default:
                    setLendsAndReturns(memberSessionLocal.searchLendsAndReturnsByIdNumber(getSearchString()));
                    break;
            }
        }
    }


    public void handleSearch() {
        init();
    } //end handleSearch

    /**
     * @return the lendsAndReturns
     */
    public List<LendAndReturn> getLendsAndReturns() {
        return lendsAndReturns;
    }

    /**
     * @param lendsAndReturns the lendsAndReturns to set
     */
    public void setLendsAndReturns(List<LendAndReturn> lendsAndReturns) {
        this.lendsAndReturns = lendsAndReturns;
    }

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

}
