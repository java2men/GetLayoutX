/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.f13.getlayout.ui.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import eu.davidea.flexibleadapter.common.FlexibleItemDecoration;
import ru.f13.getlayout.R;
import ru.f13.getlayout.data.model.Conversion;
import ru.f13.getlayout.databinding.ItemConversionBinding;
import ru.f13.getlayout.util.GLUtils;

/**
 * Адаптер конвертаций
 */
public class ConversionsAdapter extends RecyclerView.Adapter<ConversionsAdapter.ConvertingViewHolder> {

    private static final int FIRST_ITEM = 0;
    private static final int ITEM = 1;
    private static final int LAST_ITEM = 2;

    private List<? extends Conversion> mConversions;

    private RecyclerView mRecyclerView;

    private ClipboardManager clipboard;
    private GLUtils glUtils;

    private OnCopyResultListener mOnCopyResultListener;
    private OnDeleteConversionListener mOnDeleteConversionListener;

    /**
     * Конструктор
     * @param context контекст
     */
    public ConversionsAdapter(Context context) {

        setHasStableIds(true);
        clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        glUtils = GLUtils.getInstance(context);
    }

    /**
     * Уставноить список конвертаций
     * @param conversions список конвертаций
     */
    public void setConversions(final List<? extends Conversion> conversions) {

        decorateRecyclerView();

        if (mConversions == null) {
            mConversions = conversions;
            notifyItemRangeInserted(0, conversions.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mConversions.size();
                }

                @Override
                public int getNewListSize() {
                    return conversions.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mConversions.get(oldItemPosition).getId().equals(conversions.get(newItemPosition).getId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Conversion newConversion = conversions.get(newItemPosition);
                    Conversion oldConversion = mConversions.get(oldItemPosition);
                    return Objects.equals(newConversion.getId(), oldConversion.getId())
                            && Objects.equals(newConversion.getDate(), oldConversion.getDate())
                            && Objects.equals(newConversion.getResultText(), oldConversion.getResultText());
                }
            });

            mConversions = conversions;
            result.dispatchUpdatesTo(this);

        }

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    /**
     * Декорировать {@link RecyclerView}
     */
    private void decorateRecyclerView() {

        //очистить от предыдущих сепараторов
        for (int i = 0; i < mRecyclerView.getItemDecorationCount(); i++) {
            mRecyclerView.removeItemDecorationAt(i);
            i--;
        }

        mRecyclerView.addItemDecoration(
                new FlexibleItemDecoration(mRecyclerView.getContext()).
                        addItemViewType(FIRST_ITEM, 0, 16, 0, 16).
                        addItemViewType(ITEM, 0, 0, 0, 16).
                        addItemViewType(LAST_ITEM, 0, 0, 0, 16).
                        withEdge(true)

        );

    }

    @NonNull
    @Override
    public ConvertingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemConversionBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_conversion,
                        parent, false);

        return new ConvertingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ConvertingViewHolder holder, int position) {
        holder.binding.setConversion(mConversions.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return FIRST_ITEM;
        } else if (position == getItemCount() - 1) {
            return LAST_ITEM;
        }

        return ITEM;
    }

    @Override
    public int getItemCount() {
        return mConversions == null ? 0 : mConversions.size();
    }

    @Override
    public long getItemId(int position) {
        return mConversions.get(position).getId();
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    /**
     * Холдер item конвертации
     */
    class ConvertingViewHolder extends RecyclerView.ViewHolder {

        final ItemConversionBinding binding;

        ConvertingViewHolder(final ItemConversionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            this.binding.ivCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (binding.tvResultText.getText() != null) {
                        String text = binding.getConversion().getResultText();
                        Context context = binding.getRoot().getContext();
                        GLUtils.getInstance(context).hideKeyboard(binding.ivCopy);
                        copyTextAndNotify(text);
                    }
                }
            });

            this.binding.ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (binding.tvResultText.getText() != null) {
                        String text = binding.getConversion().getResultText();
                        Context context = binding.getRoot().getContext();
                        GLUtils.getInstance(context).hideKeyboard(binding.getRoot());
                        shareText(context, text);
                    }
                }
            });

            this.binding.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mOnDeleteConversionListener == null) {
                        return;
                    }

                    int id = binding.getConversion().getId();
                    String dateText = binding.getConversion().getDateText();
                    Context context = binding.getRoot().getContext();
                    GLUtils.getInstance(context).hideKeyboard(binding.getRoot());

                    mOnDeleteConversionListener.onDelete(id, dateText);
                }
            });

        }
    }

    /**
     * Копировать текст и уведомить через {@link OnCopyResultListener}
     * @param text текст для копирования
     */
    private void copyTextAndNotify(String text) {
        ClipData clip = ClipData.newPlainText("get-layout-result-conversion", text);
        clipboard.setPrimaryClip(clip);

        if (mOnCopyResultListener != null) {
            mOnCopyResultListener.onCopy();
        }

    }

    /**
     * Расшарить текст
     * @param context контекст
     * @param text текст для расшаривания
     */
    private void shareText(Context context, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, context.getResources().getText(R.string.send_to)));

    }

    /**
     * Получить слушатель копирования результата
     * @return слушатель копирования результата
     */
    public OnCopyResultListener getOnCopyResultListener() {
        return mOnCopyResultListener;
    }

    /**
     * Установить слушатель копирования результата
     * @param onCopyResultListener слущатель копирования результата
     */
    public void setOnCopyResultListener(OnCopyResultListener onCopyResultListener) {
        mOnCopyResultListener = onCopyResultListener;
    }

    /**
     * Получить слушатель удаления конвертации из базы данных
     * @return слушатель удаления конвертации из базы данных
     */
    public OnDeleteConversionListener getOnDeleteConversionListener() {
        return mOnDeleteConversionListener;
    }

    /**
     * Установить слушатель удаления конвертации из базы данных
     * @param onDeleteConversionListener слушатель удаления конвертации из базы данных
     */
    public void setOnDeleteConversionListener(OnDeleteConversionListener onDeleteConversionListener) {
        mOnDeleteConversionListener = onDeleteConversionListener;
    }

}
