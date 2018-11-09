package mullen.liftnotes;

public class DietObjects {

    private String cal;
    private String pro;
    private String fat;
    private String carb;

    public DietObjects(String cal, String pro, String fat, String carb){
        this.cal = cal;
        this.pro = pro;
        this.fat = fat;
        this.carb = carb;
    }

    public String getCal(){
        return cal;
    }

    public String getPro(){
        return pro;
    }

    public String getFat(){
        return fat;
    }

    public String getCarb(){
        return carb;
    }
}
