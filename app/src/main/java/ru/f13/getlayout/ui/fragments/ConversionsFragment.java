package ru.f13.getlayout.ui.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.transition.TransitionManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import ru.f13.getlayout.R;
import ru.f13.getlayout.databinding.FragmentConversionsBinding;
import ru.f13.getlayout.data.db.entity.ConversionEntity;
import ru.f13.getlayout.data.model.ConversionOptions;
import ru.f13.getlayout.ui.adapters.ConversionsAdapter;
import ru.f13.getlayout.ui.adapters.OnDeleteConversionListener;
import ru.f13.getlayout.util.GLUtils;
import ru.f13.getlayout.util.convert.ConvertLayout;
import ru.f13.getlayout.viewmodel.ConversionsViewModel;

public class ConversionsFragment extends Fragment {

    private ConversionsAdapter mConversionsAdapter;
    private FragmentConversionsBinding mBinding;

    private ConversionsViewModel mViewModel;
    /**
     * Код исходной раскладки
     */
    private String inputCode = ConvertLayout.CODE_RU;
    /**
     * Код результирующей раскладки
     */
    private String resultCode = ConvertLayout.CODE_EN;

    /**
     * Возможность сохранения данных
     */
    private boolean isSaveData = true;

    private GLUtils glUtils;

    /**
     * Новый инстанс
     * @return фрагмент {@link ConversionsFragment}
     */
    public static ConversionsFragment newInstance() {
        return new ConversionsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glUtils = GLUtils.getInstance(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_conversions, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.clDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inputCode.equals(ConvertLayout.CODE_RU)) {
                    inputCode = ConvertLayout.CODE_EN;
                    resultCode = ConvertLayout.CODE_RU;
                } else if (inputCode.equals(ConvertLayout.CODE_EN)) {
                    inputCode = ConvertLayout.CODE_RU;
                    resultCode = ConvertLayout.CODE_EN;
                }

                setConversionValueUI(mBinding.clDir, inputCode, true);

                selectTvInput(mBinding.tietInput.hasFocus());

            }

        });

        mConversionsAdapter = new ConversionsAdapter(mBinding.rvConversions.getContext());
        mBinding.rvConversions.setAdapter(mConversionsAdapter);
        mConversionsAdapter.setOnDeleteConversionListener(new OnDeleteConversionListener() {
            @Override
            public void onDelete(final int id, String dateText) {
                System.out.println("idConversion = " + dateText);
                Context context = mBinding.rvConversions.getContext();
                Snackbar snackbar = Snackbar.
                        make(mBinding.getRoot(), getString(R.string.delete_for, dateText), Snackbar.LENGTH_LONG).
                        setActionTextColor(ContextCompat.getColorStateList(context, R.color.colorWhite)).
                        setAction(R.string.yes, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mViewModel.deleteConversion(id);
                            }
                        });

                snackbar.show();
            }
        });

        mBinding.tietInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                selectTvInput(hasFocus);

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(ConversionsViewModel.class);

        final boolean capsLock = mBinding.swCapsLock.isChecked();

        mBinding.ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable editable = mBinding.tietInput.getText();

                //отправлять только непустые данные
                if (editable != null && !TextUtils.isEmpty(editable.toString())) {
                    String inputText = editable.toString();
                    mViewModel.addConversionText(inputCode, resultCode, inputText, capsLock);
                    editable.clear();
                }
            }
        });

        subscribeUi(mViewModel);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (isSaveData) {
            mViewModel.setConversionOptions(new ConversionOptions(
                    isSaveData,
                    inputCode,
                    resultCode,
                    mBinding.swCapsLock.isChecked()
            ));
        } else {
            mViewModel.deleteAllConversions();
            mViewModel.deleteConversionOptions();
        }

    }

    /**
     * Подписаться UI на изменения
     * @param viewModel объект {@link androidx.lifecycle.ViewModel}
     */
    private void subscribeUi(final ConversionsViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getConversions().observe(this, new Observer<List<ConversionEntity>>() {
            @Override
            public void onChanged(@Nullable List<ConversionEntity> conversions) {
                if (conversions != null) {
                    mBinding.setIsLoading(false);
                    mConversionsAdapter.setConversions(conversions);


                    mBinding.rvConversions.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //mBinding.rvConversions.smoothScrollToPosition(mConversionsAdapter.getItemCount() - 1);
                            mBinding.rvConversions.scrollToPosition(mConversionsAdapter.getItemCount() - 1);
                        }
                    }, 10);

                } else {
                    mBinding.setIsLoading(true);
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                // sync.
                mBinding.executePendingBindings();
            }
        });

        viewModel.getSaveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                isSaveData = value;
            }
        });

        viewModel.getConversionOptions().observe(this, new Observer<ConversionOptions>() {
            @Override
            public void onChanged(ConversionOptions conversionOptions) {

                if (conversionOptions.isSaveData()) {
                    inputCode = conversionOptions.getInputCode();
                    resultCode = conversionOptions.getResultCode();
                    setConversionValueUI(mBinding.clDir, inputCode, false);

                    mBinding.setCapsLock(conversionOptions.getCapsLock());

                    mBinding.executePendingBindings();
                }
            }
        });

    }

    /**
     * Установить значение конвертации в UI
     * @param cl {@link ConstraintLayout} который содержит отображение значений конвертации
     * @param inputCode исходный код конвертации
     * @param isAnimate true - использовать анимацию, false - не использовать анимацию
     */
    private void setConversionValueUI(ConstraintLayout cl, String inputCode, boolean isAnimate) {
        ConstraintSet set = new ConstraintSet();

        set.clone(cl);

        set.clear(R.id.tvDir1, ConstraintSet.START);
        set.clear(R.id.tvDir1, ConstraintSet.END);
        set.clear(R.id.tvDir2, ConstraintSet.START);
        set.clear(R.id.tvDir2, ConstraintSet.END);

        int marginPx = (int) glUtils.dpToPx(mBinding.getRoot().getContext(), 16);

        if (inputCode.equals(ConvertLayout.CODE_RU)) {
            set.connect(R.id.tvDir1, ConstraintSet.START, R.id.clDir, ConstraintSet.START, marginPx);
            set.connect(R.id.tvDir1, ConstraintSet.END, R.id.ivChange, ConstraintSet.START, marginPx);

            set.connect(R.id.tvDir2, ConstraintSet.START, R.id.ivChange, ConstraintSet.END, marginPx);
            set.connect(R.id.tvDir2, ConstraintSet.END, R.id.clDir, ConstraintSet.END, marginPx);
        } else if (inputCode.equals(ConvertLayout.CODE_EN)) {
            set.connect(R.id.tvDir2, ConstraintSet.START, R.id.clDir, ConstraintSet.START, marginPx);
            set.connect(R.id.tvDir2, ConstraintSet.END, R.id.ivChange, ConstraintSet.START, marginPx);

            set.connect(R.id.tvDir1, ConstraintSet.START, R.id.ivChange, ConstraintSet.END, marginPx);
            set.connect(R.id.tvDir1, ConstraintSet.END, R.id.clDir, ConstraintSet.END, marginPx);
        }

        if (isAnimate) {
            TransitionManager.beginDelayedTransition(cl);
        }

        set.applyTo(cl);

    }

    /**
     * Выделить {@link TextView} который соотвествует исходному коду конвертации
     * в зависимости от фокуса редактора вводимого текста
     * @param hasFocus true - есть фокус, false - фокус отсутсвует
     */
    private void selectTvInput(boolean hasFocus) {

        //изначально Typeface нормальный
        mBinding.tvDir1.setTypeface(null, Typeface.NORMAL);
        mBinding.tvDir2.setTypeface(null, Typeface.NORMAL);

        //найти исходный текст раскладки
        String inputText = null;
        if (inputCode.equals(ConvertLayout.CODE_RU)) {
            inputText = getString(R.string.text_russian);
        } else if (inputCode.equals(ConvertLayout.CODE_EN)) {
            inputText = getString(R.string.text_english);
        }

        //найти исходный textView
        TextView tvInput = null;
        if (mBinding.tvDir1.getText().toString().equals(inputText)) {
            tvInput = mBinding.tvDir1;
        } else if (mBinding.tvDir2.getText().toString().equals(inputText)) {
            tvInput = mBinding.tvDir2;
        }

        //если найден исходный textView
        if (tvInput != null) {
            int typeFace = hasFocus ? Typeface.BOLD : Typeface.NORMAL;
            tvInput.setTypeface(null, typeFace);
        }

        mBinding.executePendingBindings();
    }

}
