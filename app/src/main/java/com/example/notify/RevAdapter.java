package com.example.notify;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notify.models.Noty;

import java.util.List;

public class RevAdapter extends RecyclerView.Adapter<RevAdapter.CardView> {

    private Activity activity;
    List<Noty> mList;

    public RevAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setData(List<Noty> list){
        this.mList = list;
    }

    @NonNull
    @Override
    public CardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = activity.getLayoutInflater().inflate(R.layout.item_card,parent,false);
        return new CardView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RevAdapter.CardView holder, int position) {
        Noty n = mList.get(position);
        System.out.println("alllll"+n.toString());
        holder.title.setText(n.getTitle());
        holder.content.setText(n.getContent());
        holder.date.setText(n.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,DetailActivity.class);
                intent.putExtra("note",n);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mList!=null)return mList.size();
        return 0;
    }

    public class CardView extends RecyclerView.ViewHolder {
        TextView title,content,date;
        public CardView(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.iTitle);
            content = v.findViewById(R.id.iContent);
            date = v.findViewById(R.id.iTime);
        }
    }
}
