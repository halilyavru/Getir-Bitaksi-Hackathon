package com.halilyavru.getirbitaksihackathon;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by halilmac on 10/02/2018.
 */

public class AdapterRecords extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Record> listRecord;

    public AdapterRecords(List<Record> listRecord){
        this.listRecord = listRecord;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record,parent,false);
        return new AdapterRecords.RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Record record = listRecord.get(holder.getAdapterPosition());
        RecordViewHolder viewHolder = (RecordViewHolder) holder;

        viewHolder.tvId.setText(record.getId());
        viewHolder.tvCount.setText(String.valueOf(record.getTotalCount()));
        viewHolder.tvKey.setText(record.getKey());
        viewHolder.tvValue.setText(record.getValue());
        viewHolder.tvDate.setText(record.getCeratedAt());

    }

    @Override
    public int getItemCount() {
        return listRecord.size();
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder{

        public TextView tvId,tvCount,tvKey,tvValue,tvDate;

        public RecordViewHolder(View itemView) {
            super(itemView);

            tvId = (TextView) itemView.findViewById(R.id.tv_record_id);
            tvCount = (TextView) itemView.findViewById(R.id.tv_record_count);
            tvKey = (TextView) itemView.findViewById(R.id.tv_record_key);
            tvValue = (TextView) itemView.findViewById(R.id.tv_record_value);
            tvDate = (TextView) itemView.findViewById(R.id.tv_record_date);

        }
    }
}
