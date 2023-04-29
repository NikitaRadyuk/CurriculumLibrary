package Services;

import dao.implementation.UserDaoImpl;
import entity.User;
import exception.DaoException;

public class LoginService {
    private static final LoginService INSTANCE = new LoginService();
    private static UserDaoImpl userDao = UserDaoImpl.getInstance();

    private LoginService(){};
    public static LoginService getInstance(){return INSTANCE;}

    public User login(String login, String password) throws ServiceException{
        if(!isValid(login, password)){
            throw new ServiceException("NotValid");
        }
        UserDaoImpl dao = UserDaoImpl.getInstance();
        try{
            String pass = dao.findUserPasswordByLogin(login);
            if(pass != null){
                if(pass.equals(password)){
                    User user = dao.findUserByLogin(login);
                    if(user != null){
                        return user;
                    }
                }
            }
        } catch (DaoException e) {
            throw new ServiceException("dao exception while login");
        }
        return null;
    }
    public boolean isValid(String login, String password) {
        if(login == null || login.isEmpty())
            return false;
        if(password == null || password.isEmpty())
            return false;
        return true;
    }

    private boolean validReg(User user){
        return user.getLogin() != null && user.getPassword() != null && user.getName() != null
                && user.getSurname() != null && user.getEmail() != null;
    }

    public boolean Register(User user) throws ServiceException{
        if (validReg(user)){
            return true;
        }
        else{
            throw new ServiceException("invalid");
        }
    }
}
