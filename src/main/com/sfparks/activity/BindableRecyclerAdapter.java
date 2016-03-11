package com.sfparks.activity;

import android.app.Application;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sfparks.BR;
import com.sfparks.R;
import com.sfparks.model.Park;

import java.util.List;

import javax.inject.Inject;

/*
 * Created by Andrew Brin on 3/8/2016.
 */
public class BindableRecyclerAdapter extends RecyclerView.Adapter<BindableRecyclerAdapter.BindingHolder> {

    @Inject Application application;
    private List<Park> parks;
    private static ObservableList.OnListChangedCallback onListChangedCallback;

    public BindableRecyclerAdapter(ObservableArrayList<Park> parks){ this.parks = parks; }

    @BindingAdapter({"entries"})
    public static void setEntries(RecyclerView view, final ObservableArrayList<Park> parks){
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

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        return new BindingHolder(view);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final Park park = parks.get(position);
        holder.getDataBinding().setVariable(BR.park, park);
        holder.getDataBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return parks.size();
    }
}
