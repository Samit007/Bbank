package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bloodbankmanagementsystem.R;

import java.util.List;

import Model.Event;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    Context mContext;
    List<Event> eventList;

    public EventAdapter(Context mContext, List<Event> eventList) {
        this.mContext = mContext;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event, viewGroup, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventViewHolder eventViewHolder, int i) {
        final Event event=eventList.get(i);
        eventViewHolder.tvEname.setText(event.getEventname());
        eventViewHolder.tvEaddress.setText(event.getEventaddress());
        eventViewHolder.tvEstartname.setText(event.getEventstarttime());
        eventViewHolder.tvEendtime.setText(event.getEventendtime());
        eventViewHolder.tvEdate.setText(event.getEventdate());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        TextView tvEname, tvEaddress, tvEstartname,tvEendtime,tvEdate;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEname=itemView.findViewById(R.id.tvEname);
            tvEaddress=itemView.findViewById(R.id.tvEaddress);
            tvEstartname=itemView.findViewById(R.id.tvEstarttime);
            tvEendtime=itemView.findViewById(R.id.tvEendtime);
            tvEdate=itemView.findViewById(R.id.tvEdate);
        }
    }
}

