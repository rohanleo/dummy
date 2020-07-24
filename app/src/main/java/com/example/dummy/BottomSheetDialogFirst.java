package com.example.dummy;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BottomSheetDialogFirst extends BottomSheetDialogFragment {

    private Context context;
    private FragmentManager mManager;

    public BottomSheetDialogFirst(Context context, FragmentManager manager) {
        this.context = context;
        this.mManager = manager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fb_popup_layout,container,false);

        final EditText commentField = view.findViewById(R.id.writeComment);
        ImageView send = view.findViewById(R.id.comment_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentField.getText().toString();
                Toast.makeText(context,comment, Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        BlogCommentAdapter adapter = new BlogCommentAdapter(context,mManager,getUserName(),getComments());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<String> getUserName() {
        List<String> username = new ArrayList<>();
        for (int i = 0 ; i < 10 ; i++){
            username.add("User_" + (i+1));
        }
        return  username;
    }

    private List<String> getComments() {
        List<String> comments = new ArrayList<>();
        for (int i = 0 ; i < 10 ; i++){
            comments.add("I am at comment " + (i + 1) + " today " + Calendar.getInstance().getTime().toString());
        }
        return  comments;
    }
}
