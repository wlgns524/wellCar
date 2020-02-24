package com.rightcode.wellcar.Component;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.Log;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.functions.Action2;

public class GalleryView extends RecyclerView {

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    private GalleryAdapater adapter;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public GalleryView(Context context) {
        this(context, null);
    }

    public GalleryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ButterKnife.bind(this);
        initialize(context, attrs);
    }

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public GalleryAdapater getAdapter() {
        return adapter;
    }

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------
    private void initialize(Context context, AttributeSet attrs) {
        setLayoutManager(new GridLayoutManager(context, 3));
        setAdapter(adapter = new GalleryAdapater());
        loadGalley();
    }


    private void loadGalley() {
        String[] proj = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME
        };

        int[] idx = new int[proj.length];
        ContentResolver resolver = getContext().getContentResolver();
        Cursor cursor = MediaStore.Images.Media.query(
                resolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, null, MediaStore.Images.ImageColumns.DATE_MODIFIED + " desc");
        if (cursor != null && cursor.moveToFirst()) {
            idx[0] = cursor.getColumnIndex(proj[0]);
            idx[1] = cursor.getColumnIndex(proj[1]);
            idx[2] = cursor.getColumnIndex(proj[2]);
            do {

                int id = cursor.getInt(idx[0]);
                String path = cursor.getString(idx[1]);
                String displayName = cursor.getString(idx[2]);
                if (displayName != null) {
                    adapter.addItem(path);
                }
            } while (cursor.moveToNext());
        }

    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
    public class GalleryAdapater extends Adapter<GalleryAdapater.GalleryHolder> {
        //------------------------------------------------------------------------------------------
        // fields
        //------------------------------------------------------------------------------------------
        private ArrayList<String> items;
        private ArrayList<String> selectedItems;
        private Action1<String> actionSingle;
        private Action2<String, Integer> actionMultiple;
        private boolean isOnlyNumber;

        private int maxCount;
        private Action1<Void> actionMaxCount;
        private int totalMaxSize;
        private Action1<Void> actionTotalMaxSize;
        private int maxSize;
        private Action1<Void> actionMaxSize;

        private int selectedTotalSize;

        //------------------------------------------------------------------------------------------
        // constructor
        //------------------------------------------------------------------------------------------
        public GalleryAdapater() {
            this.items = new ArrayList<String>();
            this.selectedItems = new ArrayList<String>();
        }

        //------------------------------------------------------------------------------------------
        // public
        //------------------------------------------------------------------------------------------
        public void setOnlyNumber(boolean isOnlyNumber) {
            this.isOnlyNumber = isOnlyNumber;
        }

        public void setSingleAction(Action1<String> action) {
            actionSingle = action;
        }

        public void setMultipleAction(Action2<String, Integer> action) {
            actionMultiple = action;
        }

        public ArrayList<String> getSelectedPhotos() {
            return selectedItems;
        }

        public void setMaxCount(Action1<Void> action, int maxCount) {
            this.actionMaxCount = action;
            this.maxCount = maxCount;
        }

        public void setTotalMaxSize(Action1<Void> action, int totalMaxSize) {
            this.actionTotalMaxSize = action;
            this.totalMaxSize = totalMaxSize;
        }

        public void setMaxSize(Action1<Void> action, int maxSize) {
            this.actionMaxSize = action;
            this.maxSize = maxSize;
        }

        public void addItem(String data) {
            items.add(data);
            notifyDataSetChanged();
        }

        public ArrayList<String> getSelectedItems() {
            return selectedItems;
        }

        public void addSelectedItem(String data) {
            Log.d();

            if (totalMaxSize > 0) {
                File f = new File(data);
                selectedTotalSize += f.length();

                Log.d("++ addSelectedItem( length : %d, totalMaxSize : %d, selectedTotalSize : %d )", f.length(), totalMaxSize, selectedTotalSize);
            }

            selectedItems.add(data);
            notifyDataSetChanged();
        }

        @Override
        public GalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.view_image_select_item, null);
            v.setLayoutParams(new LayoutParams(parent.getWidth() / 3, parent.getWidth() / 3));
            return new GalleryHolder(v);
        }

        @Override
        public void onBindViewHolder(GalleryHolder holder, int position) {
            String data = items.get(position);

            Glide.with(holder.context).load(new File(data))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(holder.ivImage);

            int index = findIndex(data);
            if (index > 0) {
                holder.tvNumber.setVisibility(View.VISIBLE);
                String num = String.valueOf(index);
                holder.tvNumber.setText(num);
                holder.tvNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                holder.view.setSelected(true);
            } else {
                holder.tvNumber.setVisibility(View.GONE);
                holder.view.setSelected(false);
            }

            holder.view.setOnClickListener(v -> {
                if (actionSingle != null) {
                    if (!performPhotoCheck(data)) {
                        return;
                    }
                    selectedItems.add(data);
                    actionSingle.call(data);
                    return;
                }

                if (index > 0) {
                    for (String selected : selectedItems) {
                        if (selected != null && selected.equals(data)) {
                            File f = new File(selected);
                            selectedTotalSize -= f.length();
                            Log.d("++ length : %d, selectedTotalSize : %d", f.length(), selectedTotalSize);
                            selectedItems.remove(selected);
                            break;
                        }
                    }
                } else {
                    if (!performPhotoCheck(data)) {
                        return;
                    }
                    selectedItems.add(data);
                }

                if (actionMultiple != null) {
                    actionMultiple.call(data, selectedItems.size());
                }
                notifyItemChanged(position);
//                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public void setHasStableIds(boolean hasStableIds) {
            super.setHasStableIds(true);
        }

        //------------------------------------------------------------------------------------------
        // private
        //------------------------------------------------------------------------------------------
        private int findIndex(String data) {
            for (String selected : selectedItems) {
                if (selected != null && selected.equals(data)) {
                    return selectedItems.indexOf(selected) + 1;
                }
            }
            return 0;
        }

        private boolean performPhotoCheck(String path) {
            // 최대 장수
            if (maxCount > 0 && selectedItems.size() >= maxCount) {
                actionMaxCount.call(null);
                return false;
            }

            File f = new File(path);

            // 장 당 사이즈
            Log.d("++ length : %d, maxSize : %d", f.length(), maxSize);
            if (maxSize > 0 && f.length() >= maxSize) {
                actionMaxSize.call(null);
                return false;
            }

            // 총 사이즈
            Log.d("++ length : %d, totalMaxSize : %d, selectedTotalSize : %d", f.length(), totalMaxSize, selectedTotalSize);
            if (totalMaxSize > 0 && selectedTotalSize + f.length() >= totalMaxSize) {
                actionTotalMaxSize.call(null);
                return false;
            }

            selectedTotalSize += f.length();

            return true;
        }

        class GalleryHolder extends ViewHolder {

            @BindView(R.id.image_select_iv_image)
            ImageView ivImage;
            @BindView(R.id.image_select_tv_number)
            TextView tvNumber;

            public Context context;
            public View view;

            public GalleryHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                context = itemView.getContext();
                view = itemView;
            }
        }
    }
}
