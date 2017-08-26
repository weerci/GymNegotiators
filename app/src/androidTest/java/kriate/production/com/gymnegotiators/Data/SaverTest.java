package kriate.production.com.gymnegotiators.Data;

import org.junit.Test;

import kriate.production.com.gymnegotiators.Model.Theme;
import kriate.production.com.gymnegotiators.fit.Phrases;

import static org.junit.Assert.*;

/**
 * Created by dima on 25.08.2017.
 */
public class SaverTest {

    private Saver saver = new Saver();
    private String test64String = "dGVzdA==";

    @Test
    public void getThemeLight() throws Exception {
        Theme theme = new Theme() {{
            setId("testId");
            setName("testName");
            setDesk("testDesk");
            setPotoBit(test64String);
            getIsPurchased().set(true);
            setVersion(1);
        }};

        saver.SaveThemeLight(theme);
        Theme savedTheme = saver.GetTheme(theme.getId());
        assertTrue(savedTheme != null);
        assertEquals(theme.getId(), savedTheme.getId());
        assertEquals(theme.getName(), savedTheme.getName());
        assertEquals(theme.getDesk(), savedTheme.getDesk());
        assertEquals(theme.getPhotoBit(), savedTheme.getPhotoBit());
        assertEquals(theme.getIsPurchased().get(), savedTheme.getIsPurchased().get());
        assertNull(savedTheme.getPhrases());
        assertEquals(theme.getVersion(), savedTheme.getVersion());
    }

    //TODO Сделать тест для загрузки, получении фраз

}