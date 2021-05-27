package lists;

public class Order {
    private Integer id;
    private String name;
    private String startStation;
    private String finishStation;
    private Integer numberTrain;

    public Order(Integer id, String name, String startStation, String finishStation, Integer numberTrain) {
        this.id = id;
        this.name = name;
        this.startStation = startStation;
        this.finishStation = finishStation;
        this.numberTrain = numberTrain;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getFinishStation() {
        return finishStation;
    }

    public void setFinishStation(String finishStation) {
        this.finishStation = finishStation;
    }

    public Integer getNumberTrain() {
        return numberTrain;
    }

    public void setNumberTrain(Integer numberTrain) {
        this.numberTrain = numberTrain;
    }
}
