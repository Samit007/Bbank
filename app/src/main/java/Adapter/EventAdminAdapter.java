package Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bloodbankmanagementsystem.EventAdminActivity;
import com.example.bloodbankmanagementsystem.R;

import java.util.List;

import Model.Event;

public class EventAdminAdapter extends RecyclerView.Adapter<EventAdminAdapter.EventViewHolder> {
    Context mContext;
    List<Event> eventList;

    public EventAdminAdapter(Context mContext, List<Event> eventList) {
        this.mContext = mContext;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventAdminAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event, viewGroup, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdminAdapter.EventViewHolder eventViewHolder, int i) {
        final Event event = eventList.get(i);
        eventViewHolder.tvEname.setText(event.getEventname());
        eventViewHolder.tvEaddress.setText(event.getEventaddress());
        eventViewHolder.tvEstartname.setText(event.getEventstarttime());
        eventViewHolder.tvEendtime.setText(event.getEventendtime());
        eventViewHolder.tvEdate.setText(event.getEventdate());

        eventViewHolder.tvEname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EventAdminActivity.class);
                intent.putExtra("eventname", event.getEventname());
                intent.putExtra("eventaddress", event.getEventaddress());
                intent.putExtra("eventstarttime", event.getEventstarttime());
                intent.putExtra("eventendtime", event.getEventendtime());
                intent.putExtra("eventdate", event.getEventdate());
                intent.putExtra("eventid",event.getEventid());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        TextView tvEname, tvEaddress, tvEstartname, tvEendtime, tvEdate;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEname = itemView.findViewById(R.id.tvEname);
            tvEaddress = itemView.findViewById(R.id.tvEaddress);
            tvEstartname = itemView.findViewById(R.id.tvEstarttime);
            tvEendtime = itemView.findViewById(R.id.tvEendtime);
            tvEdate = itemView.findViewById(R.id.tvEdate);
        }
    }
}

