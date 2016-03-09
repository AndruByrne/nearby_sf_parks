package com.sfparks.activity;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sfparks.BR;
import com.sfparks.R;
import com.sfparks.model.Park;

import java.util.List;

/*
 * Created by Andrew Brin on 3/8/2016.
 */
public class BindableRecyclerAdapter extends RecyclerView.Adapter<BindableRecyclerAdapter.BindingHolder> {

    private List<Park> parks;

    public BindableRecyclerAdapter(ObservableArrayList<Park> parks){ this.parks = parks; }

    @BindingAdapter({"entries"})
    public static void setEntries(RecyclerView view, final ObservableArrayList<Park> parks){
        if(parks.size() != 0){
            throw new RuntimeException();
        }
        Log.d("sfparks adapter", "setting parks");
        final BindableRecyclerAdapter adapter = new BindableRecyclerAdapter(parks);
        Log.d("sfparks adapter", "created the adapter");
        parks.addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {
                Log.d("sfparks adapter", "dataset has changed");
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
        });
        view.setAdapter(adapter);
        Log.d("sfparks adapter", "set the adapter");
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
