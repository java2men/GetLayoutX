package ru.f13.getlayout;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.f13.getlayout.util.convert.ConvertLayout;
import ru.f13.getlayout.util.convert.sequence.ModifierSequenceBuilder;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ConvertLayoutInstrumentedTest {

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

        boolean isShift = false;
        boolean isCapsLock = false;
        ModifierSequenceBuilder builderInputText = new ModifierSequenceBuilder();
        builderInputText.add(realInputText, isShift, isCapsLock);
        String resultText = convertLayout.getResultText(builderInputText);

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

        boolean isShift = true;
        boolean isCapsLock = false;
        ModifierSequenceBuilder builderInputText = new ModifierSequenceBuilder();
        builderInputText.add(realInputText, isShift, isCapsLock);
        String resultText = convertLayout.getResultText(builderInputText);

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

        boolean isShift = false;
        boolean isCapsLock = true;
        ModifierSequenceBuilder builderInputText = new ModifierSequenceBuilder();
        builderInputText.add(realInputText, isShift, isCapsLock);
        String resultText = convertLayout.getResultText(builderInputText);

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

        boolean isShift = true;
        boolean isCapsLock = true;
        ModifierSequenceBuilder builderInputText = new ModifierSequenceBuilder();
        builderInputText.add(realInputText, isShift, isCapsLock);
        String resultText = convertLayout.getResultText(builderInputText);

        Assert.assertEquals(resultText, realResultText);

    }


    @Test
    public void conversionIsCorrectEnToRu() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        ConvertLayout convertLayout = new ConvertLayout(appContext);

        convertLayout.
                unionKeyboard(
                        convertLayout.getKeyboard(ConvertLayout.CODE_EN),
                        convertLayout.getKeyboard(ConvertLayout.CODE_RU)
                );

        String realInputText = "`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./";
        String realResultText = "ё1234567890-=йцукенгшщзхъ\\фывапролджэячсмитьбю.";

        boolean isShift = false;
        boolean isCapsLock = false;
        ModifierSequenceBuilder builderInputText = new ModifierSequenceBuilder();
        builderInputText.add(realInputText, isShift, isCapsLock);
        String resultText = convertLayout.getResultText(builderInputText);

        Assert.assertEquals(resultText, realResultText);

    }

    @Test
    public void conversionIsCorrectEnToRuShift() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        ConvertLayout convertLayout = new ConvertLayout(appContext);

        convertLayout.
                unionKeyboard(
                        convertLayout.getKeyboard(ConvertLayout.CODE_EN),
                        convertLayout.getKeyboard(ConvertLayout.CODE_RU)
                );

        String realInputText = "~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:\"ZXCVBNM<>?";
        String realResultText = "Ё!\"№;%:?*()_+ЙЦУКЕНГШЩЗХЪ/ФЫВАПРОЛДЖЭЯЧСМИТЬБЮ,";

        boolean isShift = true;
        boolean isCapsLock = false;
        ModifierSequenceBuilder builderInputText = new ModifierSequenceBuilder();
        builderInputText.add(realInputText, isShift, isCapsLock);
        String resultText = convertLayout.getResultText(builderInputText);

        Assert.assertEquals(resultText, realResultText);

    }

    @Test
    public void conversionIsCorrectEnToRuCaps() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        ConvertLayout convertLayout = new ConvertLayout(appContext);

        convertLayout.
                unionKeyboard(
                        convertLayout.getKeyboard(ConvertLayout.CODE_EN),
                        convertLayout.getKeyboard(ConvertLayout.CODE_RU)
                );

        String realInputText = "`1234567890-=QWERTYUIOP[]\\ASDFGHJKL;'ZXCVBNM,./";
        String realResultText = "Ё1234567890-=ЙЦУКЕНГШЩЗХЪ\\ФЫВАПРОЛДЖЭЯЧСМИТЬБЮ.";

        boolean isShift = false;
        boolean isCapsLock = true;
        ModifierSequenceBuilder builderInputText = new ModifierSequenceBuilder();
        builderInputText.add(realInputText, isShift, isCapsLock);
        String resultText = convertLayout.getResultText(builderInputText);

        Assert.assertEquals(resultText, realResultText);

    }

    @Test
    public void conversionIsCorrectEnToRuCapsShift() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        ConvertLayout convertLayout = new ConvertLayout(appContext);

        convertLayout.
                unionKeyboard(
                        convertLayout.getKeyboard(ConvertLayout.CODE_EN),
                        convertLayout.getKeyboard(ConvertLayout.CODE_RU)
                );

        String realInputText = "~!@#$%^&*()_+qwertyuiop{}|asdfghjkl:\"zxcvbnm<>?";
        String realResultText = "ё!\"№;%:?*()_+йцукенгшщзхъ/фывапролджэячсмитьбю,";

        boolean isShift = true;
        boolean isCapsLock = true;
        ModifierSequenceBuilder builderInputText = new ModifierSequenceBuilder();
        builderInputText.add(realInputText, isShift, isCapsLock);
        String resultText = convertLayout.getResultText(builderInputText);

        Assert.assertEquals(resultText, realResultText);

    }

}
