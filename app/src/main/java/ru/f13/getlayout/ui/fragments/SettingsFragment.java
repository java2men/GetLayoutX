package ru.f13.getlayout.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import ru.f13.getlayout.R;
import ru.f13.getlayout.databinding.FragmentSettingsBinding;
import ru.f13.getlayout.viewmodel.SettingsViewModel;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding mBinding;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        requireActivity().setTitle(R.string.settings);

        final SettingsViewModel viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        mBinding.clSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.cbSaveData.performClick();
            }
        });

        mBinding.clExitAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.cbNotExitAlert.performClick();
            }
        });

        mBinding.clDarkTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.cbDarkTheme.performClick();
            }
        });

        mBinding.clSuggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.cbSuggestions.performClick();
            }
        });


        mBinding.cbSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setSaveData(((CheckBox)v).isChecked());
            }
        });

        mBinding.cbNotExitAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setNotExitAlert(((CheckBox)v).isChecked());
            }
        });

        mBinding.cbDarkTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setDarkTheme(((CheckBox)v).isChecked());
            }
        });

        mBinding.cbSuggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setSuggestions(((CheckBox)v).isChecked());
            }
        });

        subscribeUi(viewModel);
    }

    /**
     * Подписать UI на изменения
     * @param viewModel объект {@link androidx.lifecycle.ViewModel}
     */
    private void subscribeUi(SettingsViewModel viewModel) {

        viewModel.getSaveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                mBinding.setIsSaveData(value);
                mBinding.executePendingBindings();
            }
        });

        viewModel.getNotExitAlert().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                mBinding.setIsNotExitAlert(value);
                mBinding.executePendingBindings();
            }
        });

        viewModel.getDarkTheme().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                mBinding.setIsDarkTheme(value);
                mBinding.executePendingBindings();
            }
        });

        viewModel.getSuggestions().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                mBinding.setIsSuggestions(value);
                mBinding.executePendingBindings();
            }
        });

    }

}
