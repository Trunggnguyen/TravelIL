package com.example.travelil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelil.Model.Users;
import com.example.travelil.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsergrItem_Adapter extends RecyclerView.Adapter<UsergrItem_Adapter.ViewHolder>{
    private Context context;
    private ArrayList<Users> users;


    public UsergrItem_Adapter(Context context, ArrayList<Users> users) {
        this.context = context;
        this.users = users;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_usergr, parent,false);
        return new UsergrItem_Adapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Users user = users.get(position);
        if (position==0){
            Glide.with(context).load(user.getImageURI()).into(holder.profile_image);
        }else
        if (position==1){
            Glide.with(context).load(user.getImageURI()).into(holder.profile_image1);
        }else
        if (position==2){
            Glide.with(context).load(user.getImageURI()).into(holder.profile_image2);
        }else {
            holder.profile_image2.setImageResource(R.drawable.plus);

        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView profile_image,profile_image1,profile_image2,profile_image3;
        private ImageView img_on;



        public ViewHolder(View itemView) {
            super(itemView);


            profile_image = itemView.findViewById(R.id.profile_image);
            profile_image1 = itemView.findViewById(R.id.profile_image2);
            profile_image2 = itemView.findViewById(R.id.profile_image3);
            profile_image3 = itemView.findViewById(R.id.profile_image4);

            //img_on = itemView.findViewById(R.id.img_on);


        }

    }
}
