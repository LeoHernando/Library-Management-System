/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;


import entity.LendAndReturn;
import entity.LibraryMember;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import session.DataInitializationSessionBean;
import session.MemberSessionBeanLocal;
import session.StaffSessionBeanLocal;

/**
 *
 * @author lhern
 */
@Named(value = "libraryManagedBean")
@ViewScoped
public class LibraryManagedBean implements Serializable {

    @EJB
    private StaffSessionBeanLocal staffSessionLocal;
    @EJB
    private MemberSessionBeanLocal memberSessionLocal;


    private Long mId;
    private LibraryMember selectedMember;

    private List<LibraryMember> members;
    //for registerMember.xhtml
    private String firstName;
    private String lastName;
    private char gender;
    private int age;
    private String idNum;
    private String phone;
    private String address;

    //for Lending Book
    private Long bookId;
    //for viewing fine amount
    private Long lendId;

    private String searchType = "FIRSTNAME";
    private String searchString;
    

    /**
     * Creates a new instance of LibraryManagedBean
     */
    public LibraryManagedBean() {
    }

    @PostConstruct
    public void init() {
        if (searchString == null || searchString.equals("")) {
        members = memberSessionLocal.searchMembersByFirstName(null);

        } else {
            switch (searchType) {
                case "FIRSTNAME":
                    members = memberSessionLocal.searchMembersByFirstName(searchString);
                    break;
                case "LASTNAME": {
                    members = memberSessionLocal.searchMembersByLastName(searchString);
                    break;
                }
                case "IDNUMBER": {
                    members = memberSessionLocal.searchMembersByIdNumber(searchString);
                    break;
                }
                default:
                    members = memberSessionLocal.searchMembersByIdNumber(searchString);
                    break;
            }
        }
    }

    public void loadSelectedMember() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            this.selectedMember = memberSessionLocal.retrieveMemberByIdentityNo(idNum);
            firstName = this.selectedMember.getFirstName();
            lastName = this.selectedMember.getLastName();
            gender = this.selectedMember.getGender();
            age = this.selectedMember.getAge();
            idNum = this.selectedMember.getIdentityNo();
            phone = this.selectedMember.getPhone();
            address = this.selectedMember.getAddress();
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to load members"));
        }
    } //end loadSelectedCustomer

    public void handleSearch() {
        init();
    } //end handleSearch

    public void registerMember(ActionEvent evt) {
        LibraryMember m = new LibraryMember();
        m.setFirstName(firstName);
        m.setLastName(lastName);
        m.setGender(gender);
        m.setAge(age);
        m.setIdentityNo(idNum);
        m.setPhone(phone);
        m.setAddress(address);
        
        memberSessionLocal.registerNewMember(m);
        
    } //end registerMember

    public void lendBook(ActionEvent evt) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            memberSessionLocal.lendBook(idNum, bookId);
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to lend book"));
        }
    } //end lendBook

    public void returnBook(ActionEvent evt) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            memberSessionLocal.returnBook(idNum, bookId);
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to return book"));
        }
    } //end returnBook


    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the gender
     */
    public char getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the idNum
     */
    public String getIdNum() {
        return idNum;
    }

    /**
     * @param idNum the idNum to set
     */
    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the members
     */
    public List<LibraryMember> getMembers() {
        return members;
    }

    /**
     * @param members the members to set
     */
    public void setMembers(List<LibraryMember> members) {
        this.members = members;
    }

    /**
     * @return the mId
     */
    public Long getmId() {
        return mId;
    }

    /**
     * @param mId the mId to set
     */
    public void setmId(Long mId) {
        this.mId = mId;
    }

    /**
     * @return the selectedMember
     */
    public LibraryMember getSelectedMember() {
        return selectedMember;
    }

    /**
     * @param selectedMember the selectedMember to set
     */
    public void setSelectedMember(LibraryMember selectedMember) {
        this.selectedMember = selectedMember;
    }

    /**
     * @return the bookId
     */
    public Long getBookId() {
        return bookId;
    }

    /**
     * @param bookId the bookId to set
     */
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    /**
     * @return the lendId
     */
    public Long getLendId() {
        return lendId;
    }

    /**
     * @param lendId the lendId to set
     */
    public void setLendId(Long lendId) {
        this.lendId = lendId;
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
