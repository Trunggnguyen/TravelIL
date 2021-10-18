package com.example.travelil.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Activity.NoteDetails;
import com.example.travelil.Model.Note;
import com.example.travelil.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Note_Adapter extends RecyclerView.Adapter<Note_Adapter.ViewHolder> {
    List<Note> noteList;
    Context context;

    public Note_Adapter(List<Note> noteList, Context context) {
        this.noteList = noteList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
          Note note = noteList.get(position);
          int code = getRandomColor();
        holder.noteContent.setBackgroundResource(code);
        holder.noteTitle.setText(note.getTitle());
        holder.noteContent.setText(note.getContent());
//            holder.mCardView.setCardBackgroundColor(holder.view.getResources().getColor(code,null));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), NoteDetails.class);
                    i.putExtra("title",note.getTitle());
                    i.putExtra("content",note.getContent());
                    i.putExtra("code",code);
                    v.getContext().startActivity(i);
                }
            });
    }

    private int getRandomColor() {

        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.blue);
        colorCode.add(R.color.yellow);
        colorCode.add(R.color.skyblue);
        colorCode.add(R.color.lightPurple);
        colorCode.add(R.color.lightGreen);
        colorCode.add(R.color.gray);
        colorCode.add(R.color.pink);
        colorCode.add(R.color.red);
        colorCode.add(R.color.greenlight);
        colorCode.add(R.color.notgreen);

        Random randomColor = new Random();
        int number = randomColor.nextInt(colorCode.size());
        return colorCode.get(number);

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle,noteContent;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.titles);
            noteContent = itemView.findViewById(R.id.content);

        }
    }
}
