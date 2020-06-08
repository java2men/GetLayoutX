package ru.f13.getlayout.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import ru.f13.getlayout.R;

/**
 * Класс утилита
 */
public class GLUtils {

    /**
     * Отображается ли в данный момент клавиатура
     * @param context контекст
     * @return true - да, false - нет
     */
    public static boolean isShowKeyboard(Context context) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean active = false;

        if (imm != null && imm.isActive() && imm.isAcceptingText()) {
            active = true;
        }

        return active;
    }

    /**
     * Отобразить клавиатуру для view
     * @param activityContext контекст активности
     * @param view view, где треубуется отобразить клавиатуру
     */
    public static void showKeyboard(Context activityContext, final View view){

        final InputMethodManager imm = (InputMethodManager)
                view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (!view.hasFocus()) {
            view.requestFocus();
        }

        view.post(new Runnable() {
            @Override
            public void run() {
                if (imm != null) {
                    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                }
            }
        });
    }

    /**
     * Скрыть клавиатуру для view
     * @param view ссылка на view для которого требуется скрыть клавиатуру
     */
    public static void hideKeyboardFrom(Context activityContext, View view) {
        if (view == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) activityContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Скрыть клавиатуру в активности
     * @param activity ссылка на активность
     */
    public static void hideKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }

        InputMethodManager imm =
                (InputMethodManager) activity.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * Скрыть клавиатуру для view
     * @param view вьюв
     */
    public static void hideKeyboard(View view) {

        if (view == null) {return;}

        InputMethodManager imm =
                (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Конвертировать количество точек на дюйм в количество пикселей
     * @param dp количество точек на дюйм
     * @return количество пикселей
     */
    public static float dpToPx(Context context, int dp) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * Конвертировать количество пикселей в количество точек на дюйм
     * @param px количество пикселей
     * @return количество точек на дюйм
     */
    public static float pxToDp(Context context, int px){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }

    /**
     * Создать кастомный {@link Toast}
     * @param context контекст
     * @param resIdText id ресурса текста
     * @param duration продолжительность
     * @param gravity позиция
     * @param xOffsetPx смещение в пикселях по X
     * @param yOffsetPx смещение в пикселях по Y
     * @return {@link Toast}
     */
    public static Toast createToastCustom(
            Context context,
            int resIdText,
            int duration,
            int gravity,
            int xOffsetPx,
            int yOffsetPx
    ) {

        Toast toast = new Toast(context);
        toast.setGravity(gravity, xOffsetPx, yOffsetPx);
        toast.setDuration(duration);

        @SuppressLint("InflateParams") View layout =  LayoutInflater.from(context)
                .inflate(R.layout.layout_custom_toast, null, false);

        TextView tv = layout.findViewById(R.id.tvText);
        tv.setText(resIdText);

        toast.setView(layout);

        return toast;

    }

}
