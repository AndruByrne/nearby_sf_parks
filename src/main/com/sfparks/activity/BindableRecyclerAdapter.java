package com.sfparks.activity;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sfparks.BR;
import com.sfparks.R;
import com.sfparks.model.Park;

import java.util.List;

import javax.inject.Inject;

/*
 * Created by Andrew Brin on 3/8/2016.
 */
public class BindableRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Inject
    PopupWindow popupWindow;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private static ObservableList.OnListChangedCallback onListChangedCallback;
    private List<Park> parks;

    public BindableRecyclerAdapter(ObservableArrayList<Park> parks) {
        this.parks = parks;
    }

    @BindingAdapter({"entries"})
    public static void setEntries(RecyclerView view, final ObservableArrayList<Park> parks) {
        final BindableRecyclerAdapter adapter = new BindableRecyclerAdapter(parks);
        // making field to avoid gc
        onListChangedCallback = new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                adapter.notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                adapter.notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                // no corresponding method in adapter, so derping
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                adapter.notifyItemRangeRemoved(positionStart, itemCount);

            }
        };
        parks.addOnListChangedCallback(onListChangedCallback);
        view.setAdapter(adapter);
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding dataBinding;

        public BindingHolder(View rowView) {
            super(rowView);
            dataBinding = DataBindingUtil.bind(rowView);
        }

        public ViewDataBinding getDataBinding() {
            return dataBinding;
        }
    }

    public static class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header, parent, false);
            return new HeaderHolder(view);
        } else if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
            return new BindingHolder(view);
        }
        throw new RuntimeException("There is no type to match " + viewType);
    }

    @Override
    public int getItemViewType(int position) {
        return isPositionZero(position) ? TYPE_HEADER : TYPE_ITEM;
    }

    private boolean isPositionZero(int position) {
        return position == 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BindingHolder) {
            BindingHolder bindingHolder = (BindingHolder) holder;
            final Park park = parks.get(position);
            final ParkListHandlers parkListHandlers = new ParkListHandlers(park);
            bindingHolder.getDataBinding().setVariable(BR.park, park);
            bindingHolder.getDataBinding().setVariable(BR.park_list_handlers, parkListHandlers);
            bindingHolder.getDataBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return parks.size() + 1;
    }

}
