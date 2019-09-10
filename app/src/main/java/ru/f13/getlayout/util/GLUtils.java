package ru.f13.getlayout.util;

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

    private static GLUtils sInstance;

    private Context mContext;

    /**
     * Конкструктор
     * @param context контекст
     */
    private GLUtils(Context context) {

        mContext = context;

    }

    /**
     * Получить инстанс
     * @param context контекст
     * @return объект {@link GLUtils}
     */
    public static GLUtils getInstance(Context context) {

        if (sInstance == null) {
            synchronized (GLUtils.class) {
                if (sInstance == null) {
                    sInstance = new GLUtils(context);
                }
            }
        }
        return sInstance;
    }


    /**
     * Отображается ли в данный момент клавиатура
     * @param context контекст
     * @return true - да, false - нет
     */
    public boolean isShowKeyboard(Context context) {

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
    public void showKeyboard(Context activityContext, final View view){

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
    public void hideKeyboardFrom(Context activityContext, View view) {
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
    public void hideKeyboard(Activity activity) {
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
     * @param view
     */
    public void hideKeyboard(View view) {

        InputMethodManager imm =
                (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Конвертировать количество точек на дюйм в количество пикселей
     * @param dp количество точек на дюйм
     * @return количество пикселей
     */
    public float dpToPx(int dp) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,
                mContext.getResources().getDisplayMetrics());
    }

    /**
     * Конвертировать количество пикселей в количество точек на дюйм
     * @param px количество пикселей
     * @return количество точек на дюйм
     */
    public float pxToDp(int px){

        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }

    /**
     * Создать кастомный {@link Toast}
     * @param resIdText id ресурса текста
     * @param duration продолжительность
     * @param gravity позиция
     * @param xOffsetPx смещение в пикселях по X
     * @param yOffsetPx смещение в пикселях по Y
     * @return {@link Toast}
     */
    public Toast createToastCustom(int resIdText, int duration, int gravity, int xOffsetPx, int yOffsetPx) {

        Toast toast = new Toast(mContext);
        toast.setGravity(gravity, xOffsetPx, yOffsetPx);
        toast.setDuration(duration);

        View layout =  LayoutInflater.from(mContext)
                .inflate(R.layout.layout_custom_toast, null, false);

        TextView tv = layout.findViewById(R.id.tvText);
        tv.setText(resIdText);

        toast.setView(layout);

        return toast;

    }

}
