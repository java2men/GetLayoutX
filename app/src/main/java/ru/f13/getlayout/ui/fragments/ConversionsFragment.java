package ru.f13.getlayout.ui.fragments;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.TransitionManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import ru.f13.getlayout.R;
import ru.f13.getlayout.data.db.entity.ConversionEntity;
import ru.f13.getlayout.data.model.ConversionOptions;
import ru.f13.getlayout.databinding.FragmentConversionsBinding;
import ru.f13.getlayout.ui.adapters.ConversionsAdapter;
import ru.f13.getlayout.ui.adapters.OnCopyResultListener;
import ru.f13.getlayout.ui.adapters.OnDeleteConversionListener;
import ru.f13.getlayout.util.GLUtils;
import ru.f13.getlayout.util.convert.ConvertLayout;
import ru.f13.getlayout.viewmodel.ConversionsViewModel;

public class ConversionsFragment extends Fragment {

    /**
     * Предлагать подсказки для ввода
     */
    private int YES_SUGGESTIONS = InputType.TYPE_TEXT_FLAG_MULTI_LINE;
    /**
     * Не предлагать подсказки для ввода
     */
    private int NO_SUGGESTIONS =
            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            | InputType.TYPE_TEXT_FLAG_MULTI_LINE;

    /**
     * Код исходной раскладки
     */
    private String inputCode = ConvertLayout.CODE_RU;
    /**
     * Код результирующей раскладки
     */
    private String resultCode = ConvertLayout.CODE_EN;

    private ConversionsAdapter mConversionsAdapter;
    private FragmentConversionsBinding mBinding;

    private ConversionsViewModel mViewModel;

    private ConvertLayout convertLayout;
    private String beforeInputText = "";
    private String afterInputText = "";

    /**
     * Возможность сохранения данных
     */
    private boolean isSaveData = true;

    private MenuItem miDeleteAll;

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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_conversions, container, false);
        return mBinding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        convertLayout = new ConvertLayout(mBinding.getRoot().getContext());

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

        mConversionsAdapter.setOnCopyResultListener(new OnCopyResultListener() {
            @Override
            public void onCopy() {
//                ((MainActivity)requireActivity()).
//                        showCustomToast(R.string.notification_copy_result, Toast.LENGTH_SHORT);

                final Snackbar snackbar = Snackbar
                        .make(mBinding.mcvInput, getString(R.string.notification_copy_result), Snackbar.LENGTH_LONG);

                snackbar.setAction(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                snackbar.dismiss();
                            }
                        });
                snackbar.show();
            }
        });

        mConversionsAdapter.setOnDeleteConversionListener(new OnDeleteConversionListener() {
            @Override
            public void onDelete(final int id, String dateText) {

                //Context context = mBinding.rvConversions.getContext();
                Snackbar snackbar = Snackbar.
                        make(mBinding.mcvInput, getString(R.string.delete_for, dateText), Snackbar.LENGTH_LONG).
                        //setActionTextColor(ContextCompat.getColorStateList(context, R.color.white_50)).
                        setAction(R.string.yes, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mViewModel.deleteConversion(id);
                                updateStateDeleteAll();
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

        mBinding.ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable editable = mBinding.tietInput.getText();

                //отправлять только непустые данные
                if (editable != null && !TextUtils.isEmpty(editable.toString())) {
                    String inputText = editable.toString();

                    convertLayout.unionKeyboard(
                            convertLayout.getKeyboard(inputCode),
                            convertLayout.getKeyboard(resultCode)
                    );

                    String resultText = convertLayout.getResultText(inputText);
                    mViewModel.addConversionText(inputText, resultText);

                    editable.clear();

                }
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        requireActivity().setTitle(R.string.get_layout);

        mViewModel = new ViewModelProvider(this).get(ConversionsViewModel.class);
        subscribeUi(mViewModel);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (isSaveData) {
            mViewModel.setConversionOptions(new ConversionOptions(
                    isSaveData,
                    inputCode,
                    resultCode
            ));
        } else {
            mViewModel.deleteAllConversions();
            mViewModel.deleteConversionOptions();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main, menu);
        miDeleteAll = menu.findItem(R.id.action_delete_all);

        updateStateDeleteAll();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_all) {

            //скрыть клавиатуру
            GLUtils.hideKeyboard(getView());

            Snackbar snackbar = Snackbar.
                    make(mBinding.getRoot(), getString(R.string.delete_history), Snackbar.LENGTH_LONG).
                    setAction(R.string.yes, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mViewModel.deleteAllConversions();
                            updateStateDeleteAll();
                        }
                    });

            snackbar.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Подписаться UI на изменения
     * @param viewModel объект {@link androidx.lifecycle.ViewModel}
     */
    private void subscribeUi(ConversionsViewModel viewModel) {
        viewModel.getConversions().observe(getViewLifecycleOwner(), new Observer<List<ConversionEntity>>() {
            @Override
            public void onChanged(@Nullable List<ConversionEntity> conversions) {
                if (conversions != null) {
                    mBinding.setIsLoading(false);

                    mConversionsAdapter.setConversions(conversions);

                    if (mConversionsAdapter.isEmpty()) {
                        showStubEmptyConversion();
                    } else {
                        hideStubEmptyConversion();
                    }

                    updateStateDeleteAll();

                    mBinding.rvConversions.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.rvConversions.scrollToPosition(mConversionsAdapter.getItemCount() - 1);
                        }
                    }, 10);

                } else {
                    mBinding.setIsLoading(true);
                }

                mBinding.executePendingBindings();
            }
        });

        viewModel.getSaveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                isSaveData = value;
            }
        });

        viewModel.getConversionOptions().observe(getViewLifecycleOwner(), new Observer<ConversionOptions>() {
            @Override
            public void onChanged(ConversionOptions conversionOptions) {
                if (conversionOptions.isSaveData()) {
                    inputCode = conversionOptions.getInputCode();
                    resultCode = conversionOptions.getResultCode();
                    setConversionValueUI(mBinding.clDir, inputCode, false);

                    mBinding.executePendingBindings();
                }
            }
        });

        viewModel.getSuggestions().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                if (value) {
                    mBinding.tietInput.setInputType(YES_SUGGESTIONS);
                } else {
                    mBinding.tietInput.setInputType(NO_SUGGESTIONS);
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

        int marginPx = (int) GLUtils.dpToPx(requireContext(), 16);

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

    /**
     * Показать заглушку "нет конвертаций"
     */
    private void showStubEmptyConversion() {
        mBinding.rvConversions.setVisibility(View.GONE);
        mBinding.clEmptyData.setVisibility(View.VISIBLE);
    }

    /**
     * Скрыть заглушку "нет конвертаций"
     */
    private void hideStubEmptyConversion() {
        mBinding.clEmptyData.setVisibility(View.GONE);
        mBinding.rvConversions.setVisibility(View.VISIBLE);
    }

    /**
     * Обновить состояние(скрыть или отобразить) кнопки удаления истории
     */
    private void updateStateDeleteAll() {
        if (miDeleteAll == null || mConversionsAdapter == null) {return;}

        miDeleteAll.setVisible(!mConversionsAdapter.isEmpty());

    }
}
