package ru.f13.getlayout;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.f13.getlayout.util.convert.ConvertLayout;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ConvertLayoutInstrumentedTest {

//    @Test
//    public void useAppContext() {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//
//        assertEquals("ru.f13.getlayout", appContext.getPackageName());
//    }

    @Test
    public void conversionIsCorrectRuToEn() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        ConvertLayout convertLayout = new ConvertLayout(appContext);

        convertLayout.
                unionKeyboard(
                        convertLayout.getKeyboard(ConvertLayout.CODE_RU),
                        convertLayout.getKeyboard(ConvertLayout.CODE_EN)
                );

        String realInputText = "ё1234567890-=йцукенгшщзхъ\\фывапролджэячсмитьбю.";
        String realResultText = "`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./";
        String resultText = convertLayout.getResultText(realInputText, false);

        Assert.assertEquals(resultText, realResultText);

    }

    @Test
    public void conversionIsCorrectRuToEnShift() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        ConvertLayout convertLayout = new ConvertLayout(appContext);

        convertLayout.
                unionKeyboard(
                        convertLayout.getKeyboard(ConvertLayout.CODE_RU),
                        convertLayout.getKeyboard(ConvertLayout.CODE_EN)
                );

        String realInputText = "Ё!\"№;%:?*()_+ЙЦУКЕНГШЩЗХЪ/ФЫВАПРОЛДЖЭЯЧСМИТЬБЮ,";
        String realResultText = "~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:\"ZXCVBNM<>?";
        String resultText = convertLayout.getResultText(realInputText, false);

        Assert.assertEquals(resultText, realResultText);

    }

    @Test
    public void conversionIsCorrectRuToEnCaps() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        ConvertLayout convertLayout = new ConvertLayout(appContext);

        convertLayout.
                unionKeyboard(
                        convertLayout.getKeyboard(ConvertLayout.CODE_RU),
                        convertLayout.getKeyboard(ConvertLayout.CODE_EN)
                );

        String realInputText = "Ё1234567890-=ЙЦУКЕНГШЩЗХЪ\\ФЫВАПРОЛДЖЭЯЧСМИТЬБЮ.";
        String realResultText = "`1234567890-=QWERTYUIOP[]\\ASDFGHJKL;'ZXCVBNM,./";
        String resultText = convertLayout.getResultText(realInputText, true);

        Assert.assertEquals(resultText, realResultText);

    }

    @Test
    public void conversionIsCorrectRuToEnCapsShift() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        ConvertLayout convertLayout = new ConvertLayout(appContext);

        convertLayout.
                unionKeyboard(
                        convertLayout.getKeyboard(ConvertLayout.CODE_RU),
                        convertLayout.getKeyboard(ConvertLayout.CODE_EN)
                );

        String realInputText = "ё!\"№;%:?*()_+йцукенгшщзхъ/фывапролджэячсмитьбю,";
        String realResultText = "~!@#$%^&*()_+qwertyuiop{}|asdfghjkl:\"zxcvbnm<>?";
        String resultText = convertLayout.getResultText(realInputText, false);

        Assert.assertEquals(resultText, realResultText);

    }

}
