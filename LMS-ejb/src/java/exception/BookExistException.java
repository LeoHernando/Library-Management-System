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
public class BookExistException extends Exception{

    /**
     * Creates a new instance of <code>BookExistException</code> without detail
     * message.
     */
    public BookExistException() {
    }

    /**
     * Constructs an instance of <code>BookExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BookExistException(String msg) {
        super(msg);
    }
}
