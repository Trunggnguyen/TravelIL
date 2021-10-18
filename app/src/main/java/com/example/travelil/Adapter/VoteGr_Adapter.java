package com.example.travelil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelil.Model.Users;
import com.example.travelil.Model.Vote;
import com.example.travelil.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VoteGr_Adapter extends RecyclerView.Adapter<VoteGr_Adapter.ViewHolder>{
    Context context;
     List<Vote> comments ;
    FirebaseUser firebaseUser;
    String key;

    public VoteGr_Adapter(Context context, List<Vote> comments) {
        this.context = context;
        this.comments = comments;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vote_item, parent, false);
        return new VoteGr_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Vote comment = comments.get(position);

        pulisherInfo(holder.circleImageView,holder.username, comment.getPulisher());
        holder.ratingBar.setRating(comment.getVote());
        if (comment.getComment().equals("null")){
            holder.comment.setText("");
        }else {
            holder.comment.setText(comment.getComment());
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.edit:
//                                editPost(post.getPostid());
                                return true;
                            case R.id.delete:
                                return true;
                            case R.id.report:
                                Toast.makeText(context, "Reported clicked!", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.inflate(R.menu.post_menu);


                    popupMenu.getMenu().findItem(R.id.edit).setVisible(false);
                    popupMenu.getMenu().findItem(R.id.delete).setVisible(false);

                popupMenu.show();
            }
        });

//        holder.comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, MainActivity2.class);
//                intent.putExtra("publisherid", comment.getPublishet());
//                context.startActivity(intent);
//            }
//        });
//        holder.circleImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, MainActivity2.class);
//                intent.putExtra("publisherid", comment.getPublishet());
//                context.startActivity(intent);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView circleImageView;
        TextView username, comment;
        ImageView imageView ;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.user_namecmt);
            comment = itemView.findViewById(R.id.comment);
            circleImageView = itemView.findViewById(R.id.profile_image_comment);
            imageView= itemView.findViewById(R.id.more);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
    private  void pulisherInfo(final  CircleImageView imageView_profile,TextView user_name,  final String userrid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user").child(userrid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                Glide.with(context).load(users.getImageURI()).into(imageView_profile);
                user_name.setText(users.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
