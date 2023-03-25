/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author lhern
 */
public class StaffExistException extends Exception{

    /**
     * Creates a new instance of <code>StaffExistException</code> without detail
     * message.
     */
    public StaffExistException() {
    }

    /**
     * Constructs an instance of <code>StaffExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public StaffExistException(String msg) {
        super(msg);
    }
}
