package com.pragyan.todolist;




import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;


import androidx.recyclerview.widget.RecyclerView;


import android.app.Dialog;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<TaskListStructure> mArrayList=new ArrayList<>();
    RecyclerView.LayoutManager mLayoutManager= new GridLayoutManager(this,1);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(mLayoutManager);
        TaskListRecyclerAdapter adapter= new TaskListRecyclerAdapter(this,mArrayList);
        recyclerView.setAdapter(adapter);
        initialize();
    }


    public void initialize(){
        mArrayList.add(new TaskListStructure("Learn Recycler View"));
        mArrayList.add(new TaskListStructure("Implement Recycler View"));
        mArrayList.add(new TaskListStructure("Make To-do app"));

    }


    public void addTask(View view) {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog);

        Button addBtn=dialog.findViewById(R.id.btn_add);
        Button cancelBtn=dialog.findViewById(R.id.btn_cancel);
        EditText task=dialog.findViewById(R.id.et_taskDialog);
        addBtn.setOnClickListener(view1 -> {
            int size=mArrayList.size();
            String taskString ;
            if(!task.getText().toString().isEmpty()){
                taskString=task.getText().toString();
                mArrayList.add(new TaskListStructure(taskString));
                recyclerView.getAdapter().notifyItemInserted(size);
                recyclerView.smoothScrollToPosition(size);
                dialog.dismiss();
            }
            else{
                Toast.makeText(MainActivity.this, "Please enter task", Toast.LENGTH_SHORT).show();
            }

        });
        cancelBtn.setOnClickListener(view12 -> dialog.dismiss());
        dialog.show();

    }


}


