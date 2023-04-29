package entity;

public class User {
    private Integer userID;
    private Role role;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String email;

    public Integer getUserId() {
        return userID;
    }
    public void setId(Integer userID) {
        this.userID = userID;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(int index) {
        System.out.println("index : " + index);
        this.role = Role.get(index);
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int result = userID.hashCode();
        result = result * 31 + role.hashCode();
        result = result * 31 + login.hashCode();
        result = result * 31 + password.hashCode();
        result = result * 31 + name.hashCode();
        result = result * 31 + surname.hashCode();
        result = result * 31 + email.hashCode();
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        User user = (User) obj;
        return userID.equals(user.userID)
                &&
                role.equals(user.role)
                &&
                login.equals(user.login)
                &&
                password.equals(user.password)
                &&
                name.equals(user.name)
                &&
                surname.equals(user.surname)
                &&
                email.equals(user.email);
    }

    @Override
    public String toString() {
        return surname + " " + name;
    }
}
