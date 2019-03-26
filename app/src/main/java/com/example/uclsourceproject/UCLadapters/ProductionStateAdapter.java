package com.example.uclsourceproject.UCLadapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uclsourceproject.R;
import com.example.uclsourceproject.UCLclasses.ProductionState;

import java.util.List;

public class ProductionStateAdapter
        extends RecyclerView.Adapter<ProductionStateAdapter.ViewHolder> {

    private List<ProductionState> mProductionList;

    private OnRecycleViewItemClickListener onRecycleViewItemClickListener;

    public ProductionStateAdapter(List<ProductionState> mProductionList) {
        this.mProductionList = mProductionList;
    }

    @NonNull
    @Override
    public ProductionStateAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.production_state_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onRecycleViewItemClickListener.onItemClick(v);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onRecycleViewItemClickListener.onItemLongClick(v);
                return true;
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(
            @NonNull ProductionStateAdapter.ViewHolder viewHolder, int i) {
        //i--position
        ProductionState _ps = mProductionList.get(i);
        viewHolder.tx_producitonId.setText(String.valueOf(_ps.getProductionId()));
        viewHolder.tx_healthState.setText(_ps.getHealthState());
        viewHolder.tx_daySteps.setText(String.valueOf(_ps.getDaySteps()));
        viewHolder.tx_dayTempareture.setText((String.valueOf(_ps.getDayTemperature()) + "℃"));

        //在tag中设置索引信息
        viewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return mProductionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tx_producitonId;
        TextView tx_healthState;
        TextView tx_daySteps;
        TextView tx_dayTempareture;

        public ViewHolder(@NonNull View view) {
            super(view);
            tx_producitonId = view.findViewById(R.id.text_productionId);
            tx_healthState = view.findViewById(R.id.text_healthState);
            tx_daySteps = view.findViewById(R.id.text_daySteps);
            tx_dayTempareture = view.findViewById(R.id.text_dayTemperature);
        }
    }


    //点击事件回调接口
    public interface OnRecycleViewItemClickListener {
        void onItemClick(View view);

        void onItemLongClick(View view);

//        void onItemClick(int position);
//
//        void onItemLongClick(int position);
    }

    //提供设置点击事件监听器
    public void setOnRecycleViewItemClickListener(
            OnRecycleViewItemClickListener itemClickListener) {
        onRecycleViewItemClickListener = itemClickListener;
    }
}
