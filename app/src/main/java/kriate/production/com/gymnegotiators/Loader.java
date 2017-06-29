package kriate.production.com.gymnegotiators;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import kriate.production.com.gymnegotiators.Model.Theme;

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

    public static ArrayList<Theme> getThemies() {
        ArrayList<Theme> themes = new ArrayList<>();

        themes.add(new Theme(App.getContext().getString(R.string.seller_head), App.getContext().getString(R.string.seller_desk), R.drawable.seller, R.raw.seller));

        return themes;
    }

}
