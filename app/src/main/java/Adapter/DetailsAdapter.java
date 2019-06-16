package Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bloodbankmanagementsystem.DetailsActivity;
import com.example.bloodbankmanagementsystem.R;
import Model.User;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder> {
    Context mContext;
    List<User> userList;

    public DetailsAdapter(Context mContext, List<User> userList) {
        this.mContext = mContext;
        this.userList = userList;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user, viewGroup, false);
            return new DetailsViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder detailsViewHolder, int i) {
        final User user=userList.get(i);
        detailsViewHolder.tvName.setText(user.getFirstname());
        detailsViewHolder.tvBlood.setText(user.getBlood_group());

        detailsViewHolder.tvName.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DetailsActivity.class);
                intent.putExtra("firstname",user.getFirstname());
                intent.putExtra("lasttname",user.getLastname());
                intent.putExtra("phoneNo",user.getPhoneNo());
                intent.putExtra("email",user.getEmail());
                intent.putExtra("address",user.getAddress());
                intent.putExtra("gender",user.getGender());
                intent.putExtra("blood",user.getBlood_group());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        //CircleImageView imgProfile;
        TextView tvName,tvPhoneNo,tvEmail,tvAddress,tvGender,tvBlood;
        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
          //  imgProfile=itemView.findViewById(R.id.imgProfile);
            tvName=itemView.findViewById(R.id.tvName);
//            tvPhoneNo=itemView.findViewById(R.id.tvPhoneNo);
//            tvEmail=itemView.findViewById(R.id.tvEmail);
//            tvAddress=itemView.findViewById(R.id.tvAddress);
//            tvGender=itemView.findViewById(R.id.tvGender);
            tvBlood=itemView.findViewById(R.id.tvBlood);
        }

        }
    }


