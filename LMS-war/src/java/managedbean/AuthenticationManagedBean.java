package managedbean;

import entity.Staff;
import exception.InvalidLoginException;
import exception.StaffNotFoundException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import session.StaffSessionBeanLocal;

@Named(value = "authenticationManagedBean")
@SessionScoped
public class AuthenticationManagedBean implements Serializable {

    @EJB
    private StaffSessionBeanLocal staffSessionLocal;

    private String username = null;
    private String password = null;
    private long userId = -1;

    public AuthenticationManagedBean() {
    }

    public String login() throws InvalidLoginException {
        try {
            Staff s = staffSessionLocal.retrieveStaffByUsername(username);

            if (s.getPassword().equals(password)) {
                userId = s.getStaffId();
                return "/authenticated/home.xhtml?faces-redirect=true";
            } else {
                username = null;
                password = null;
                userId = -1;
                return "login.xhtml";
            }
        } catch (StaffNotFoundException ex) {
            throw new InvalidLoginException("Username does not exist or invalid password!");
        }
    }

    public String logout() {
        username = null;
        password = null;
        userId = -1;

        return "/login.xhtml?faces-redirect=true";
    } //end logout

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
