package com.example.dummy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BlogCommentAdapter extends RecyclerView.Adapter<BlogCommentAdapter.ViewHolder> {

    private Context context;
    private FragmentManager manager;
    private List<String> userName;
    private List<String> comments;

    public BlogCommentAdapter(Context context,FragmentManager manager, List<String> userName, List<String> comments) {
        this.context = context;
        this.manager = manager;
        this.userName = userName;
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fb_comments_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userName.setText(userName.get(position));
        holder.comment.setText(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return userName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userName;
        TextView comment;
        TextView reply;
        LinearLayout viewReplies;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            comment = itemView.findViewById(R.id.user_comment);
            reply = itemView.findViewById(R.id.reply_comment);
            viewReplies = itemView.findViewById(R.id.view_replies);
            view = itemView;
            reply.setOnClickListener(this);
            viewReplies.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.reply_comment:
                    viewSecondLevelReply();
                    break;

                case R.id.view_replies:
                    viewSecondLevelReply();
                    break;
            }
        }

        public void viewSecondLevelReply(){
            BottomSheetDialogSecond bottomSheetDialogSecond = new BottomSheetDialogSecond(context,userName.getText().toString(),comment.getText().toString());
            bottomSheetDialogSecond.show(manager,"SecondComment");
        }
    }
}
