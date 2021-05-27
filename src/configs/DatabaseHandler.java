package configs;

import java.sql.*;

// Подключение к БД
public class DatabaseHandler extends Configs {
    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName;
        // Class.forName("com.mysql.jdbc.Driver"); // название библиотеки устарело
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }
    public ResultSet selectAllStations() {
        ResultSet result = null;
        String select = "SELECT * FROM " + dbName + "." + ConstStations.STATION_TABLE; // SQL запрос на изъятие из БД

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            result = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ResultSet selectAllTrains() {
        ResultSet result = null;
        String select = "SELECT * FROM " + dbName + "." + ConstTrains.TRAINS_TABLE; // SQL запрос на изъятие из БД

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            result = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ResultSet selectAllUsers() {
        ResultSet result = null;
        String select = "SELECT * FROM " + dbName + "." + ConstUsers.USER_TABLE + " WHERE " +
                ConstUsers.USERS_POSITION +"!=?"; // SQL запрос на изъятие из БД

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, "admin");
            result = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ResultSet selectAllOrders() {
        ResultSet result = null;
        String select = "SELECT * FROM " + dbName + "." + ConstOrders.ORDER_TABLE; // SQL запрос на изъятие из БД

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            result = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void signUpUser(String login, String password, String position, String nameStation) {
        String insert = "INSERT INTO " + ConstUsers.USER_TABLE + "(" +
                ConstUsers.USERS_LOGIN + "," + ConstUsers.USERS_PASSWORD + "," + ConstUsers.USERS_POSITION + ","
                + ConstUsers.USERS_NAMESTATION + ")" +
                "VALUES(?,?,?,?)"; // SQL запрос на добавление в БД
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, login);
            prSt.setString(2, password);
            prSt.setString(3, position);
            prSt.setString(4, nameStation);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getUser(String login, String password) {
        ResultSet result = null;
        String select = "SELECT * FROM " + ConstUsers.USER_TABLE + " WHERE " +
                ConstUsers.USERS_LOGIN + "=? AND " + ConstUsers.USERS_PASSWORD + "=?"; // SQL запрос на изъятие из БД

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, login);
            prSt.setString(2, password);
            result = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void deleteUser(String login, String password, String nameStation) {
        String delete = "DELETE FROM " + ConstUsers.USER_TABLE + " WHERE " + ConstUsers.USERS_LOGIN +
                "=? AND " + ConstUsers.USERS_PASSWORD + "=? AND " + ConstUsers.USERS_NAMESTATION + "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setString(1, login);
            prSt.setString(2, password);
            prSt.setString(3, nameStation);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addOrder(String name, String startStation, String finishStation, Integer numberTrain) {
        String insert = "INSERT INTO " + ConstOrders.ORDER_TABLE+ "(" +
                ConstOrders.ORDER_NAME + ","+ ConstOrders.ORDER_START_STATION + ","+ ConstOrders.ORDER_FINISH_STATION +
                ","+ ConstOrders.ORDER_TRAIN +
                ")" + "VALUES(?,?,?,?)"; // SQL запрос на добавление в БД
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, name);
            prSt.setString(2, startStation);
            prSt.setString(3, finishStation);
            prSt.setInt(4, numberTrain);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getOrder(Integer id) {
        ResultSet result = null;
        String select = "SELECT * FROM " + ConstOrders.ORDER_TABLE + " WHERE " + ConstOrders.ORDER_ID +
                "=? "; // SQL запрос на изъятие из БД

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, id);
            result = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void deleteOrder(Integer id) {
        String delete = "DELETE FROM " + ConstOrders.ORDER_TABLE + " WHERE " + ConstOrders.ORDER_ID +
                "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setInt(1, id);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addStation(String name) {
        String insert = "INSERT INTO " + ConstStations.STATION_TABLE + "(" +
                ConstStations.STATION_NAME + ")" + "VALUES(?)"; // SQL запрос на добавление в БД
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, name);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getStation(String name) {
        ResultSet result = null;
        String select = "SELECT * FROM " + ConstStations.STATION_TABLE + " WHERE " + ConstStations.STATION_NAME +
                "=? "; // SQL запрос на изъятие из БД

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, name);
            result = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void deleteStation(String name) {
        String delete = "DELETE FROM " + ConstStations.STATION_TABLE + " WHERE " + ConstStations.STATION_NAME +
                "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setString(1, name);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addTrain(String startStation, String finishStation) {
        String insert = "INSERT INTO " + ConstTrains.TRAINS_TABLE + "(" +
                ConstTrains.TRAINS_START + "," +
                ConstTrains.TRAINS_FINISH + ")" + "VALUES(?,?)"; // SQL запрос на добавление в БД
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, startStation);
            prSt.setString(2, finishStation);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public ResultSet getTrain(Integer id) {
        ResultSet result = null;
        String select = "SELECT * FROM " + ConstTrains.TRAINS_TABLE + " WHERE " + ConstTrains.TRAINS_ID +
                "=? "; // SQL запрос на изъятие из БД

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, id);
            result = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void deleteTrain(Integer id) {
        String delete = "DELETE FROM " + ConstTrains.TRAINS_TABLE + " WHERE " + ConstTrains.TRAINS_ID +
                "=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setInt(1, id);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
