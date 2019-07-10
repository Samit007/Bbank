package Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodbankmanagementsystem.AdminDetailsActivity;
import com.example.bloodbankmanagementsystem.DetailsActivity;
import com.example.bloodbankmanagementsystem.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import Model.User;
import Url.Url;

public class AdminDetailsAdapter extends RecyclerView.Adapter<AdminDetailsAdapter.DetailsViewHolder> {
    Context Context;
    List<User> userList;

    public AdminDetailsAdapter(Context Context, List<User> userList) {
        this.Context = Context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public AdminDetailsAdapter.DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user, viewGroup, false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder detailsViewHolder, int i) {
        final User user = userList.get(i);
        final String imgPath = Url.BASE_URL + "uploads/" + user.getImagename();
        StrictMode();
        try {
            URL url = new URL(imgPath);
            detailsViewHolder.imgItem.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        detailsViewHolder.tvName.setText(user.getFirstname());
        detailsViewHolder.tvBlood.setText(user.getBlood_group());

        detailsViewHolder.tvName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Context, AdminDetailsActivity.class);
                intent.putExtra("firstname", user.getFirstname());
                intent.putExtra("lastname", user.getLastname());
                intent.putExtra("phone", user.getPhone());
                intent.putExtra("email", user.getEmail());
                intent.putExtra("address", user.getAddress());
                intent.putExtra("gender", user.getGender());
                intent.putExtra("blood_group", user.getBlood_group());
                intent.putExtra("dob", user.getDate_of_birth());
                intent.putExtra("userid", user.getUserid());
                intent.putExtra("imagename", imgPath);
                Context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView tvName, tvBlood;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem=itemView.findViewById(R.id.imgProfile);
            tvName = itemView.findViewById(R.id.tvName);
            tvBlood = itemView.findViewById(R.id.tvBlood);
        }

    }
    private void StrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}


