package ru.f13.getlayout.ui.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import ru.f13.getlayout.R;
import ru.f13.getlayout.databinding.FragmentSettingsBinding;
import ru.f13.getlayout.viewmodel.SettingsViewModel;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding mBinding;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final SettingsViewModel viewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);

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

        subscribeUi(viewModel);
    }

    /**
     * Подписать UI на изменения
     * @param viewModel объект {@link androidx.lifecycle.ViewModel}
     */
    private void subscribeUi(SettingsViewModel viewModel) {

        viewModel.getSaveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                mBinding.setIsSaveData(value);
                mBinding.executePendingBindings();
            }
        });

        viewModel.getNotExitAlert().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean value) {
                mBinding.setIsNotExitAlert(value);
                mBinding.executePendingBindings();
            }
        });

    }

}
