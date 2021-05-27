package lists;

public class User {
    private Integer id;
    private String login;
    private String password;
    private String position;
    private String nameStation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNameStation() {
        return nameStation;
    }

    public void setNameStation(String nameStation) {
        this.nameStation = nameStation;
    }

    public User(Integer id, String login, String password, String position, String nameStation) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.position = position;
        this.nameStation = nameStation;
    }
}
