package kriate.production.com.gymnegotiators;

import java.util.ArrayList;

import kriate.production.com.gymnegotiators.Model.Theme;

/**
 * Created by dima on 29.06.2017.
 */

public final class Demo {

    private static final String[] phrases = {
        "Наставник: - Забудьте все, чему вас учили в университете!",
        "Сотрудник внутренней службы безопасности: - Ну и сколько получаете откат от тендера?",
        "Сотрудник руководителю после новой задачи: - Мне что, пятый раз все переделывать?!",
        "Руководитель: - Как Вы в таком юном возрасте можете рассуждать на столь серьезную тему?",
        "Руководитель сотруднику: - Какой мозговой барьер помешал Вам выполнить эту задачу?"
    };


    public static ArrayList<Theme> getThemies()
    {
        ArrayList<Theme> themes = new ArrayList<>();

        themes.add(new Theme(1, phrases[0], R.drawable.buyer, R.raw.buyer));
        themes.add(new Theme(1, phrases[1], R.drawable.seller, R.raw.seller));
        themes.add(new Theme(1, phrases[0], R.drawable.employee, R.raw.employee));
        themes.add(new Theme(1, phrases[0], R.drawable.boss, R.raw.boss));
        themes.add(new Theme(1, phrases[0], R.drawable.list, R.raw.list));

        return themes;
    }
}
