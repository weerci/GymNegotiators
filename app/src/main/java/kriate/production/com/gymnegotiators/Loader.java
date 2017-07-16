package kriate.production.com.gymnegotiators;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import kriate.production.com.gymnegotiators.Model.Theme;
import kriate.production.com.gymnegotiators.fit.Content;

import static android.R.attr.key;

/**
 * Created by dima on 29.06.2017.
 */

public final class Loader {

    private static final String[] phrases = {
            "Наставник: - Забудьте все, чему вас учили в университете!",
            "Сотрудник внутренней службы безопасности: - Ну и сколько получаете откат от тендера?",
            "Сотрудник руководителю после новой задачи: - Мне что, пятый раз все переделывать?!",
            "Руководитель: - Как Вы в таком юном возрасте можете рассуждать на столь серьезную тему?",
            "Руководитель сотруднику: - Какой мозговой барьер помешал Вам выполнить эту задачу?"
    };


    private static TreeMap<String, Theme> mapTheme = new TreeMap<>();
    /* {{
        put("kriate.production.com.gymnegotiators.list_inapp", new Theme(App.getContext().getString(R.string.list_head), App.getContext().getString(R.string.list_desk), R.drawable.list, R.raw.list));
        put("kriate.production.com.gymnegotiators.buyer_inapp", new Theme(App.getContext().getString(R.string.buyer_head), App.getContext().getString(R.string.buyer_desk), R.drawable.buyer, R.raw.buyer));
        put("kriate.production.com.gymnegotiators.seller_inapp", new Theme(App.getContext().getString(R.string.seller_head), App.getContext().getString(R.string.seller_desk), R.drawable.seller, R.raw.seller));
        put("kriate.production.com.gymnegotiators.employer_inapp", new Theme(App.getContext().getString(R.string.employer_head), App.getContext().getString(R.string.employer_desk), R.drawable.employee, R.raw.employee));
        put("kriate.production.com.gymnegotiators.boss_inapp", new Theme(App.getContext().getString(R.string.boss_head), App.getContext().getString(R.string.boss_desk), R.drawable.boss, R.raw.boss));
    }};*/

    public static TreeMap<String, Theme> getMapTheme()
    {
        return mapTheme;
    }


    public static void setMapTheme(List<Content> contentList)
    {
        mapTheme.clear();

        for (Content item : contentList) {
            mapTheme.put(item.getId(), new Theme(){{
                setName(item.getName());
                setDesk(item.getDesk());
                setPotoBit(item.getPhoto());
            }});
            //mapTheme.put(item.getId(), new Theme(item.getName(), item.getDesk(), R.drawable.list, R.raw.list));
        }
    }

    public static ArrayList<Theme> getArrayTheme()
    {
        return new ArrayList<>(mapTheme.values());
    }

    public static ArrayList<String> getSkus()
    {
        return new ArrayList<>(mapTheme.keySet());
    }
}
