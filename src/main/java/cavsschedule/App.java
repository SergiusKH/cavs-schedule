package cavsschedule;

/**
 * Created by Sergius on 27.11.2016.
 */
public class App {

    public static void main(String[] args) {
        CavsScheduleModel cavsScheduleModel = CavsScheduleModel.getInstance();
        cavsScheduleModel.setUrl("http://www.nba.com/cavaliers/schedule");
        cavsScheduleModel.getTable();
    }
}
