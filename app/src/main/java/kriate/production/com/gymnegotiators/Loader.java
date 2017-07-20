package kriate.production.com.gymnegotiators;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import kriate.production.com.gymnegotiators.Model.Theme;
import kriate.production.com.gymnegotiators.fit.Content;
import kriate.production.com.gymnegotiators.fit.Phrases;

import static android.R.attr.key;

/**
 * Created by dima on 29.06.2017.
 */

public final class Loader {

    private static TreeMap<String, Theme> mapTheme = new TreeMap<>();
    public static TreeMap<String, Theme> getMapTheme()
    {
        return mapTheme;
    }
    public static void setMapTheme(List<Content> contentList)
    {
        mapTheme.clear();

        for (Content item : contentList) {
            mapTheme.put(item.getId(), new Theme(){{
                setId(item.getId());
                setName(item.getName());
                setDesk(item.getDesk());
                setPotoBit(item.getPhoto());
            }});
            //mapTheme.put(item.getId(), new Theme(item.getName(), item.getDesk(), R.drawable.list, R.raw.list));
        }
    }

    private static  String mPhrases;
    public static String getPhrases() {
        return mPhrases;
   }
    public static void setPhrases(List<Phrases> phrases) {
        ArrayList<String> t = new ArrayList<>();

        for (Phrases item : phrases) {
            t.add(item.getPhrase());
        }
        mPhrases = Arrays.toString(t.toArray());
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
