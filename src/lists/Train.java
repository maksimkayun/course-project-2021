package lists;

public class Train {
    private Integer id;
    private String startStation;
    private String finishStation;

    public Train(Integer id, String startStation, String finishStation) {
        this.id = id;
        this.startStation = startStation;
        this.finishStation = finishStation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
