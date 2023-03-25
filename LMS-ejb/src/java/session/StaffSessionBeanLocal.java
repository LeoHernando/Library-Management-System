/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.LendAndReturn;
import entity.LibraryMember;
import entity.Staff;
import exception.BookExistException;
import exception.BookNotFoundException;
import exception.EntityManagerException;
import exception.FineNotPaidException;
import exception.InvalidCredentialsException;
import exception.InvalidLoginException;
import exception.LendingNotFoundException;
import exception.MemberExistException;
import exception.MemberNotFoundException;
import exception.StaffExistException;
import exception.StaffNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lhern
 */
@Local
public interface StaffSessionBeanLocal {

    public Staff retrieveStaffByUsername(String username) throws StaffNotFoundException;

    public double viewFineAmount(LendAndReturn lend) throws LendingNotFoundException;

    public LendAndReturn retrieveLendById(Long id) throws BookNotFoundException, LendingNotFoundException;

    public long createNewStaff(Staff s) throws EntityManagerException, StaffExistException;

    public Staff staffLogin(String username, String password) throws InvalidLoginException;




    

    
    
}
