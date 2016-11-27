package cavsschedule;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sergius on 27.11.2016.
 */
public class CavsScheduleModel {
    private static Logger log = LoggerFactory.getLogger(CavsScheduleModel.class);
    private static CavsScheduleModel instance = null;
    private String url;
    private Pattern TEAMNAME = Pattern.compile("(home|away) game Game between the (.+?) and the (.+?) (played on|on)");
    private Pattern GAMERESULT = Pattern.compile("(W|L) \\d+-\\d+");

    private CavsScheduleModel () {}

    public void setUrl(String url) {
        this.url = url;
    }

    public static CavsScheduleModel getInstance() {
        if (instance == null) {
            instance = new CavsScheduleModel();
        }
        return instance;
    }

    private Elements getElements() {
        Elements el = null;
        boolean connect = false;
        while (!connect) {
            try {
                Document document = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) " +
                                "Chrome/54.0.2840.99 Safari/537.36")
                        .get();
                el = document.getElementsByTag("li");
            } catch (IOException e) {
                //e.printStackTrace();
                log.error("Не могу получить Документ!!!");
            }
            if (el != null)
                connect = true;
        }
        return el;
    }

    public void getTable() {
        for (Element l : getElements()) {
            if (l.text().matches("home (.*)") || l.text().matches("away (.*)")) {
                Matcher teamName = TEAMNAME.matcher(l.text());
                Matcher gameResult = GAMERESULT.matcher(l.getElementsByClass("event_time game-status__past")
                        .text());
                if (teamName.find())
                    for (int i = 1 ; i < 4 ; i++) {
                        if (i == 2 )
                            System.out.print(teamName.group(i) +" vs ");
                        else
                            System.out.print(teamName.group(i) + " ");
                    }
                    if (gameResult.find())
                        System.out.println(gameResult.group());
                    else
                        System.out.println();
            }
        }
    }
}
