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
import exception.StaffNotFoundException;
import exception.NotLoggedInException;
import exception.StaffExistException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.concurrent.TimeUnit;
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
public class StaffSessionBean implements StaffSessionBeanLocal {

    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;
    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public StaffSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Staff retrieveStaffByUsername(String username) throws StaffNotFoundException {
        Query query = em.createQuery("SELECT s FROM Staff s WHERE s.userName = :inUsername");
        query.setParameter("inUsername", username);

        try {
            return (Staff)query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new StaffNotFoundException("Staff Username " + username + " does not exist!");
        }
    }

    @Override
    public double viewFineAmount(LendAndReturn lend) throws LendingNotFoundException {

        try {
            Date lendDate = lend.getLendDate();
            Date currentDate = new Date();
            long daysDiff = TimeUnit.DAYS.convert(currentDate.getTime() - lendDate.getTime(), TimeUnit.SECONDS);
            

            if ( daysDiff > 14 ) {
                double fineAmount = (daysDiff - 14) * 0.5;
                return fineAmount;
            } else {
                return 0.0;
            }
        } catch (Exception ex) {
            throw new LendingNotFoundException("Lending not found");
        }

    }

    @Override
    public LendAndReturn retrieveLendById(Long id) throws BookNotFoundException, LendingNotFoundException {
        Query query = em.createQuery("SELECT l FROM LendAndReturn l WHERE l.lendId = :id");
        query.setParameter("id", id);

        try {
            return (LendAndReturn) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new LendingNotFoundException("Lend or Return with id " + id + " does not exist!");
        }
    }



    @Override
    public long createNewStaff(Staff s) throws EntityManagerException, StaffExistException {
        Set<ConstraintViolation<String>> constraintViolations = validator.validate(s.getUserName());

        if (constraintViolations.isEmpty()) {
            try {

                em.persist(s);
                em.flush();

                return s.getStaffId();
            } catch (Exception ex) {
                throw new EntityManagerException("Unable to register new staff");
            }
        } else {
            throw new StaffExistException("Staff already exist");

        }
    }
    
    @Override
    public Staff staffLogin(String username, String password) throws InvalidLoginException
    {
        try
        {
            Staff staffEntity = retrieveStaffByUsername(username);
            
            if(staffEntity.getPassword().equals(password))
            {
                               
                return staffEntity;
            }
            else
            {
                throw new InvalidLoginException("Username does not exist or invalid password!");
            }
        }
        catch(StaffNotFoundException ex)
        {
            throw new InvalidLoginException("Username does not exist or invalid password!");
        }
    }

    
}
