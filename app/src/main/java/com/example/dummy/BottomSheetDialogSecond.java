package com.example.dummy;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetDialogSecond extends BottomSheetDialogFragment {

    private Context context;
    private String userName;
    private String comment;

    public BottomSheetDialogSecond(Context context, String userName, String comment) {
        this.context = context;
        this.userName = userName;
        this.comment = comment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.second_levelcomment_layout,container,false);

        RecyclerView recyclerView = view1.findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        BlogSecondCommentAdapter adapter = new BlogSecondCommentAdapter(context,getComments(),getUsernames());
        recyclerView.setAdapter(adapter);

        TextView username = view1.findViewById(R.id.user_name_main);
        TextView usercomment = view1.findViewById(R.id.user_comment_main);
        username.setText(userName);
        usercomment.setText(comment);

        ImageView back = view1.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view1;
    }

    private List<String> getUsernames(){
        List<String> usernames = new ArrayList<>();
        for (int i = 0 ; i < 6 ; i++){
            usernames.add("User_____" + (i + 1));
        }
        return usernames;
    }

    private List<String> getComments(){
        List<String> comments = new ArrayList<>();
        for (int i = 0 ; i < 6 ; i++){
            comments.add("I am at comment " + (i + 1));
        }
        return comments;
    }
}
