package ru.f13.getlayout.util.convert;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import ru.f13.getlayout.util.convert.models.KeyMap;
import ru.f13.getlayout.util.convert.models.Keyboard;
import ru.f13.getlayout.util.convert.models.Map;
import ru.f13.getlayout.util.convert.models.Name;
import ru.f13.getlayout.util.convert.models.Names;
import ru.f13.getlayout.util.convert.models.Settings;
import ru.f13.getlayout.util.convert.models.UnionMap;
import ru.f13.getlayout.util.convert.models.Version;

/**
 * Created by IALozhnikov on 08.09.2016.
 */
public class ConvertLayout {

    /**
     * Код русской раскладки
     */
    public static final String CODE_RU = "RU";
    /**
     * Код английской раскладки
     */
    public static final String CODE_EN = "EN";

    private Context context;
    private List<UnionMap> unionMaps;

    /**
     * Конструктор
     * @param context контекст
     */
    public ConvertLayout(Context context) {
        this.context = context;
        unionMaps = new ArrayList<>();
    }

    /**
     * Получить объект клавиатуры по коду раскладки
     * @param codeLayout код раскладки
     * @return объект клавиатуры
     */
    public Keyboard getKeyboard(String codeLayout) {
        try {

            XmlPullParser xmlPullParser;

            if (codeLayout.equals(CODE_RU)) {
                //AssetManager myAssetManager = getApplicationContext().getAssets();
                xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();

                InputStream is = context.getAssets().open("keyboard_layouts/ru-t-k0-windows.xml");
                Reader reader = new InputStreamReader(is);
                xmlPullParser.setInput(reader);
                //xmlPullParser = context.getAssets().openXmlResourceParser("keyboard_layouts/ru-t-k0-windows.xml");

                return loadKeyboard(xmlPullParser, context);
            }

            if (codeLayout.equals(CODE_EN)) {
                //AssetManager myAssetManager = getApplicationContext().getAssets();

                //xmlPullParser = context.getAssets().openXmlResourceParser("keyboard_layouts/en-t-k0-windows.xml");
                xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
                InputStream is = context.getAssets().open("keyboard_layouts/en-t-k0-windows.xml");
                Reader reader = new InputStreamReader(is);
                xmlPullParser.setInput(reader);
                return loadKeyboard(xmlPullParser, context);
            }

        } catch (Exception e) {
            Log.e(e.toString(), e.getMessage());
        }

        return null;
    }

    //private Keyboard loadKeyboard(int xml, Context context) {

    /**
     * Загрузить раскладку клавиатуру и получить объект клавиатуры
     * @param xml раскладка клавиатуры
     * @param context контекст
     * @return объект клавиатуры
     */
    private Keyboard loadKeyboard(XmlPullParser xml, Context context) {

        if (xml == null) {
            throw new Error("Xml is null");
        }

        if (context == null) {
            throw new Error("Context is null");
        }

        //XmlPullParser xmlPullParser = context.getResources().getXml(xml);
        XmlPullParser xmlPullParser = xml;

        Keyboard keyboard = new Keyboard();

        try {
            while (xmlPullParser.getEventType() != XmlPullParser.END_DOCUMENT) {

                switch (xmlPullParser.getEventType()) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        if (xmlPullParser.getName().toLowerCase().equals("keyboard")) {

                            keyboard.setKeyMaps(new ArrayList<KeyMap>());

                            for (int i=0; i<xmlPullParser.getAttributeCount(); i++){

                                if (xmlPullParser.getAttributeName(i).toLowerCase().equals("locale")) keyboard.setLocale(xmlPullParser.getAttributeValue(i));
                            }

                            break;
                        }

                        if (xmlPullParser.getName().toLowerCase().equals("version")) {

                            Version version = new Version();

                            for (int i=0; i<xmlPullParser.getAttributeCount(); i++){
                                if (xmlPullParser.getAttributeName(i).toLowerCase().equals("platform")) version.setPlatform(xmlPullParser.getAttributeValue(i));
                                if (xmlPullParser.getAttributeName(i).toLowerCase().equals("number")) version.setNumber(xmlPullParser.getAttributeValue(i));
                            }

                            keyboard.setVersion(version);
                            break;
                        }

                        if (xmlPullParser.getName().toLowerCase().equals("names")) {

                            Names names = new Names();
                            names.setName(new ArrayList<Name>());

                            keyboard.setNames(names);
                            break;
                        }

                        if (xmlPullParser.getName().toLowerCase().equals("name")) {

                            Name name = new Name();

                            for (int i=0; i<xmlPullParser.getAttributeCount(); i++){
                                if (xmlPullParser.getAttributeName(i).toLowerCase().equals("value")) name.setValue(xmlPullParser.getAttributeValue(i));
                            }

                            keyboard.getNames().getName().add(name);
                            break;
                        }

                        if (xmlPullParser.getName().toLowerCase().equals("settings")) {

                            Settings settings = new Settings();

                            for (int i=0; i<xmlPullParser.getAttributeCount(); i++){
                                if (xmlPullParser.getAttributeName(i).toLowerCase().equals("fallback")) settings.setFallback(xmlPullParser.getAttributeValue(i));
                            }

                            keyboard.setSettings(settings);
                            break;
                        }

                        if (xmlPullParser.getName().toLowerCase().equals("keymap")) {

                            KeyMap keyMap = new KeyMap();
                            keyMap.setMaps(new ArrayList<Map>());
                            for (int i=0; i<xmlPullParser.getAttributeCount(); i++){
                                if (xmlPullParser.getAttributeName(i).toLowerCase().equals("modifiers")) keyMap.setModifiers(xmlPullParser.getAttributeValue(i));
                            }

                            //keyMap.setIndex(keyboard.getKeyMap().size());
                            keyboard.getKeyMaps().add(keyMap);
                            break;
                        }

                        if (xmlPullParser.getName().toLowerCase().equals("map")) {

                            Map map = new Map();

                            for (int i=0; i<xmlPullParser.getAttributeCount(); i++){
                                if (xmlPullParser.getAttributeName(i).toLowerCase().equals("iso")) map.setIso(xmlPullParser.getAttributeValue(i));
                                if (xmlPullParser.getAttributeName(i).toLowerCase().equals("to")) map.setTo(xmlPullParser.getAttributeValue(i));
                            }

                            KeyMap lastKeyMap = keyboard.getKeyMaps().get(keyboard.getKeyMaps().size()-1);

                            map.setKeyMap(lastKeyMap);

                            //map.setIndexKeyMap(lastKeyMap.getIndex());
                            lastKeyMap.getMaps().add(map);
                            break;
                        }

                        /*
                        if (xmlPullParser.getName().toLowerCase().equals("transforms")) {

                            Transforms transforms = new Transforms();

                            for (int i=0; i<xmlPullParser.getAttributeCount(); i++){
                                if (xmlPullParser.getAttributeName(i).toLowerCase().equals("type")) transforms.setType(xmlPullParser.getAttributeValue(i));
                            }

                            keyboard.setTransforms(transforms);
                            break;
                        }

                        if (xmlPullParser.getName().toLowerCase().equals("transform")) {

                            Transform transform = new Transform();

                            for (int i=0; i<xmlPullParser.getAttributeCount(); i++){
                                if (xmlPullParser.getAttributeName(i).toLowerCase().equals("from")) transform.setFrom(xmlPullParser.getAttributeValue(i));
                                if (xmlPullParser.getAttributeName(i).toLowerCase().equals("to")) transform.setTo(xmlPullParser.getAttributeValue(i));
                            }

                            keyboard.getTransforms().getTransform().add(transform);
                            break;
                        }
                        */
                        break;

                    default:
                        break;
                }
                xmlPullParser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return keyboard;
    }

    /**
     * Объеденить клавиатуры раскладок, которые будут участвовать в конвертации
     * @param inputKeyboard объект исходной клавиатуры
     * @param resultKeyboard объект результирующей клавиатуры
     */
    public void unionKeyboard(Keyboard inputKeyboard, Keyboard resultKeyboard) {

        if (inputKeyboard == null || resultKeyboard == null) {
            return;
        }

        if (inputKeyboard.getKeyMaps() == null || resultKeyboard.getKeyMaps() == null) {
            return;
        }

        KeyMap inputKeyMap;
        KeyMap resultKeyMap;
        unionMaps = new ArrayList<>();


//        for (int i = 0; i < inputKeyboard.getKeyMaps().size(); i++) {
//            inputKeyMap = inputKeyboard.getKeyMaps().get(i);
//            resultKeyMap = resultKeyboard.getKeyMaps().get(i);
//
//            for (Map mapIKM : inputKeyMap.getMaps()) {
//                for (Map mapRKM : resultKeyMap.getMaps()) {
//                    if (mapIKM.getIso().equals(mapRKM.getIso())) {
//                        unionMaps.add(new UnionMap(mapIKM, mapRKM));
//                        continue;
//                    }
//                }
//            }
//        }

        for (int i = 0; i < inputKeyboard.getKeyMaps().size(); i++) {

            inputKeyMap = inputKeyboard.getKeyMaps().get(i);
            if (inputKeyMap == null) {
                continue;
            }

            resultKeyMap = null;
            for (int j = 0; j < resultKeyboard.getKeyMaps().size(); j++) {

                KeyMap findKeyMap = resultKeyboard.getKeyMaps().get(j);
                if (findKeyMap == null) {
                    continue;
                }

                boolean isEmptyOrNullModifiersInput = inputKeyMap.getModifiers() == null || inputKeyMap.getModifiers().equals("");
                boolean isEmptyOrNullModifiersFind = findKeyMap.getModifiers() == null || findKeyMap.getModifiers().equals("");
                if ((isEmptyOrNullModifiersInput && isEmptyOrNullModifiersFind)) {
                    resultKeyMap = findKeyMap;
                    break;
                }

                if (inputKeyMap.getModifiers().equals(findKeyMap.getModifiers())) {
                    resultKeyMap = findKeyMap;
                    break;
                }

            }

            if (resultKeyMap == null) {
                continue;
            }

            for (Map mapIKM : inputKeyMap.getMaps()) {
                for (Map mapRKM : resultKeyMap.getMaps()) {
                    if (mapIKM.getIso().equals(mapRKM.getIso())) {
                        unionMaps.add(new UnionMap(mapIKM, mapRKM));
                        continue;
                    }
                }
            }
        }

    }

    /**
     * Получить результирующий текст конвертации "на лету"
     * @param inputTextBefore исходный текст до изменений
     * @param inputTextAfter исходный текст после изменений
     * @param resultTextBefore результирующий текст до изменений
     * @param capsLock true - capsLock включен, false - capsLock выключен
     * @return результирующий текст конвертации
     */
    public String getResultText(String inputTextBefore, String inputTextAfter, String resultTextBefore, boolean capsLock) {
        StringBuilder sbInputText = new StringBuilder(inputTextAfter);
        StringBuilder sbResultText = new StringBuilder();
        for (int i = 0; i < inputTextAfter.length(); i++) {
            String looking = String.valueOf(inputTextAfter.charAt(i));//что ищем
            //String looking = "";
            if (i < inputTextBefore.length() && (inputTextAfter.charAt(i) == inputTextBefore.charAt(i))) {
                if (i < resultTextBefore.length()) {
                    sbResultText.append(resultTextBefore.charAt(i));
                    continue;
                }
            } /*else {
                looking = String.valueOf(inputTextAfter.charAt(i));//что ищем
            }*/
            String found = findInUnionMap(looking, capsLock);//найденное
            if (found.equals("")) {
                sbResultText.append(looking);
            } else {
                sbResultText.append(found);
            }
        }
        return sbResultText.toString();
    }

    /**
     * Получить результирующий текст конвертации
     * @param inputText исходный текст
     * @param capsLock true - capsLock включен, false - capsLock выключен
     * @return результирующий текст
     */
    public String getResultText(String inputText, boolean capsLock) {

        StringBuilder sbResultText = new StringBuilder();
        for (int i = 0; i < inputText.length(); i++) {
            String looking = String.valueOf(inputText.charAt(i));//что ищем
            String found = findInUnionMap(looking, capsLock);//найденное
            if (found.equals("")) {
                sbResultText.append(looking);
            } else {
                sbResultText.append(found);
            }
        }
        return sbResultText.toString();
    }

    /**
     * Найти символ в объеденной карте символов раскладок
     * @param find искомый символ
     * @param capsLock true - capsLock включен, false - capsLock выключен
     * @return найденный сконвертированный символ
     */
    private String findInUnionMap(String find, boolean capsLock) {

        for (UnionMap unionMap : unionMaps) {
            String modifiers = unionMap.getInputMap().getKeyMap().getModifiers();
            boolean isCapsLock;
            if (modifiers != null) {
                isCapsLock = modifiers.contains("caps");
            } else {
                isCapsLock = false;
            }
            String inputTo = unescapeJava(unionMap.getInputMap().getTo());
            inputTo = unescapeHtml(inputTo);
            if (inputTo.equals(find) && isCapsLock == capsLock) {
                String resultTo = unescapeJava(unionMap.getResultMap().getTo());
                resultTo = unescapeHtml(resultTo);
                //if (resultTo.indexOf("\\u") != -1) {
                    /*
                    try {
                        byte[] utf8Bytes = to.getBytes("UTF8");
                        to = new String(utf8Bytes,"UTF8");
                    }
                    catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                    try {
                        JSONObject json = new JSONObject();
                        json.put("string", to);
                        String converted = json.getString("string");
                        to = converted;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    */
                //}
                return resultTo;
            }
        }

        return "";
    }

    /**
     * Экранирование java
     * @param escaped символ подлежащий экранированию
     * @return экранированый символ
     */
    private String unescapeJava(String escaped) {
        if(escaped.indexOf("\\u") == -1)
            return escaped;
        String processed = "";
        try {

            int position = escaped.indexOf("\\u");
            while (position != -1) {
                if (position != 0)
                    processed += escaped.substring(0, position);
                String token = escaped.substring(position + 2, position + 6);
                escaped = escaped.substring(position + 6);
                processed += (char) Integer.parseInt(token, 16);
                position = escaped.indexOf("\\u");
            }
            processed += escaped;

        } catch (Exception e) {
            Log.e(e.getClass().getName(), e.getMessage(), e.getCause());
        }

        return processed;
    }

    /**
     * Экранирование html
     * @param escaped символ подлежащий экранированию
     * @return экранированый символ
     */
    private String unescapeHtml(String escaped) {
        if(escaped.indexOf("&") == -1)
            return escaped;

        String processed = "";

        switch (escaped) {
            case "&amp;":
                processed = "&";
                break;
            case "&lt;":
                processed = "<";
                break;
            case "&gt;":
                processed = ">";
                break;
            case "&quot":
                processed = "\"";
                break;
            case "&apos;":
                processed = "'";
                break;
            default:
                processed = escaped;
                break;
        }

        return processed;
    }

}
