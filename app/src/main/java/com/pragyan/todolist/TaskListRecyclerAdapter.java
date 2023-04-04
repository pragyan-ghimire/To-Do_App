package com.pragyan.todolist;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskListRecyclerAdapter extends RecyclerView.Adapter<TaskListRecyclerAdapter.TaskViewHolder> {
    ArrayList<TaskListStructure> mArrayList;
    final Context mContext;
    final LayoutInflater mLayoutInflater;


    TaskListRecyclerAdapter(Context context, ArrayList<TaskListStructure> ArrayList) {
        this.mContext = context;
        this.mArrayList = ArrayList;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.taskTitle.setText(mArrayList.get(position).getTaskTitle());
        holder.checkBox.setOnClickListener(view -> {
            if(holder.checkBox.isChecked()){
                //strike through text
                holder.taskTitle.setPaintFlags(holder.taskTitle.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                holder.completedText.setVisibility(View.VISIBLE);
            }else{
                //not strike through text
                holder.taskTitle.setPaintFlags(holder.taskTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                holder.completedText.setVisibility(View.INVISIBLE);

            }
        });

        holder.taskTitle.setOnClickListener(new View.OnClickListener() {
           final int pos=holder.getAdapterPosition();
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Rename!");
                builder.setMessage("Do you want to rename the task?");
                builder.setNegativeButton("Yes", (dialogInterface, i) -> {
                    Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.dialog);

                    TextView taskLabel=dialog.findViewById(R.id.tv_taskLabel);
                    Button actionBtn=dialog.findViewById(R.id.btn_add);
                    Button cancelBtn=dialog.findViewById(R.id.btn_cancel);
                    EditText task=dialog.findViewById(R.id.et_taskDialog);
                    taskLabel.setText("Rename Task");
                    actionBtn.setText("Rename");

                    actionBtn.setOnClickListener(view1 -> {
                        String taskString ;
                        if(!task.getText().toString().isEmpty()){
                            taskString=task.getText().toString();

                            //to rename task or to set new value
                            mArrayList.set(pos,new TaskListStructure(taskString));
                            notifyItemChanged(pos);
                            dialog.dismiss();
                        }
                        else{
                            Toast.makeText(mContext, "Please enter new task name", Toast.LENGTH_SHORT).show();
                        }

                    });
                    cancelBtn.setOnClickListener(view12 -> dialog.dismiss());
                    dialog.show();


                });
                builder.setPositiveButton("No", (dialogInterface, i) -> dialogInterface.cancel());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView taskTitle;
        CheckBox checkBox;
        TextView completedText;
        TaskListRecyclerAdapter mAdapter;

        public TaskViewHolder(@NonNull View itemView, TaskListRecyclerAdapter adapter) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.tv_taskTitle);
            checkBox = itemView.findViewById(R.id.checkBox);
            completedText=itemView.findViewById(R.id.tv_completed);
            this.mAdapter=adapter;
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Remove!");
            builder.setMessage("Do you want to remove the task?");
            builder.setNegativeButton("Yes", (dialogInterface, i) -> {
                mArrayList.remove(getAdapterPosition());
                mAdapter.notifyItemRemoved(getAdapterPosition());

            });
            builder.setPositiveButton("No", (dialogInterface, i) -> dialogInterface.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return false;
        }
    }
}
