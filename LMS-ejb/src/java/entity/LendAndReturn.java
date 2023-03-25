/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lhern
 */
@Entity
public class LendAndReturn implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lendId;

    @Temporal(TemporalType.DATE)
    private Date lendDate;

    @Temporal(TemporalType.DATE)
    private Date returnDate;

    private String idNum;
    private Long bookId;
    private double fineAmount;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private LibraryMember member;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Book book;

    public LendAndReturn() {
    }

    public LendAndReturn(String idNum, Long bookId, Date lendDate, Date returnDate, double fineAmount) {
        this.idNum = idNum;
        this.bookId = bookId;
        this.lendDate = lendDate;
        this.returnDate = returnDate;
        this.fineAmount = fineAmount;
    }

    public Long getLendId() {
        return lendId;
    }

    public void setLendId(Long lendId) {
        this.lendId = lendId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lendId != null ? lendId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the lendId fields are not set
        if (!(object instanceof LendAndReturn)) {
            return false;
        }
        LendAndReturn other = (LendAndReturn) object;
        if ((this.lendId == null && other.lendId != null) || (this.lendId != null && !this.lendId.equals(other.lendId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.LendAndReturn[ id=" + lendId + " ]";
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
     * @return the lendDate
     */
    public Date getLendDate() {
        return lendDate;
    }

    /**
     * @param lendDate the lendDate to set
     */
    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }

    /**
     * @return the returnDate
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * @param returnDate the returnDate to set
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * @return the fineAmount
     */
    public double getFineAmount() {
        Date currentDate = new Date();
        long daysDiff = TimeUnit.DAYS.convert(currentDate.getTime() - lendDate.getTime(), TimeUnit.MILLISECONDS);
        if (daysDiff > 14) {
            double fineAmount = (daysDiff - 14) * 0.5;
            return fineAmount;
        } else {
            return 0.0;
        }
    }

    /**
     * @param fineAmount the fineAmount to set
     */
    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    /**
     * @return the member
     */
    public LibraryMember getMember() {
        return member;
    }

    /**
     * @param member the member to set
     */
    public void setMember(LibraryMember member) {
        this.member = member;
    }

    /**
     * @return the book
     */
    public Book getBook() {
        return book;
    }

    /**
     * @param book the book to set
     */
    public void setBook(Book book) {
        this.book = book;
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

}
