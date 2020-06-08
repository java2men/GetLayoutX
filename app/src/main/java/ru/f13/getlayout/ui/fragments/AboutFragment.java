package ru.f13.getlayout.ui.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.f13.getlayout.BuildConfig;
import ru.f13.getlayout.R;
import ru.f13.getlayout.databinding.FragmentAboutBinding;

public class AboutFragment extends Fragment {

    private FragmentAboutBinding mBinding;

    public AboutFragment() {
        // Required empty public constructor
    }

    /**
     * Новый инстанс фрагмента
     * @return объект {@link AboutFragment}
     */
    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false);

        return mBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String versionName = BuildConfig.VERSION_NAME;
        String aboutDialogSummaryWithVersionName = String.format(getString(R.string.about_summary), versionName);
        Spanned spanned = Html.fromHtml(aboutDialogSummaryWithVersionName);
        mBinding.setAboutText(spanned);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        requireActivity().setTitle(R.string.about);
    }
}
