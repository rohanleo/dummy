package com.example.dummy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BlogSecondCommentAdapter extends RecyclerView.Adapter<BlogSecondCommentAdapter.ViewHolder> {

    Context context;
    List<String> comments;
    List<String> usernames;

    public BlogSecondCommentAdapter(Context context, List<String> comments, List<String> usernames) {
        this.context = context;
        this.comments = comments;
        this.usernames = usernames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.second_level_single_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userName.setText(usernames.get(position));
        holder.comment.setText(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return usernames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView userName;
        TextView comment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            comment = itemView.findViewById(R.id.user_comment);
        }
    }
}
