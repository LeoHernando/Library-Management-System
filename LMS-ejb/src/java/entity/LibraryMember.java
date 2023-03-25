/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


/**
 *
 * @author lhern
 */
@Entity
public class LibraryMember implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    
    
    private String firstName;
    private String lastName;
    private char gender;
    private int age;
    private String identityNo;
    private String phone;
    private String address;
    
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private ArrayList<LendAndReturn> lendsAndReturns;

    public LibraryMember() {
        lendsAndReturns = new ArrayList<>();
    }

    public LibraryMember(String firstName, String lastName, char gender, int age, String identityNo, String phone, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.identityNo = identityNo;
        this.phone = phone;
        this.address = address;
        lendsAndReturns = new ArrayList<>();
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memberId != null ? memberId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the memberId fields are not set
        if (!(object instanceof LibraryMember)) {
            return false;
        }
        LibraryMember other = (LibraryMember) object;
        if ((this.memberId == null && other.memberId != null) || (this.memberId != null && !this.memberId.equals(other.memberId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Member[ id=" + memberId + " ]";
    }

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
     * @return the identityNo
     */
    public String getIdentityNo() {
        return identityNo;
    }

    /**
     * @param identityNo the identityNo to set
     */
    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
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
     * @return the lendsAndReturns
     */
    public ArrayList<LendAndReturn> getLendsAndReturns() {
        return lendsAndReturns;
    }

    /**
     * @param lendsAndReturns the lendsAndReturns to set
     */
    public void setLendsAndReturns(ArrayList<LendAndReturn> lendsAndReturns) {
        this.lendsAndReturns = lendsAndReturns;
    }


    
}
