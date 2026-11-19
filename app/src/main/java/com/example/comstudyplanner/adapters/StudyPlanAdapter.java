package com.example.comstudyplanner.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comstudyplanner.R;
import com.example.comstudyplanner.models.StudyPlan;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class StudyPlanAdapter extends RecyclerView.Adapter<StudyPlanAdapter.ViewHolder> {

    public interface OnStudyClickListener{

        void onStudyClicked(StudyPlan plan);

    }

    private final List<StudyPlan> studyPlans;

    private final OnStudyClickListener listener;

    public StudyPlanAdapter(
            List<StudyPlan> studyPlans,
            OnStudyClickListener listener){

        this.studyPlans = studyPlans;

        this.listener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.study_plan_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        StudyPlan plan = studyPlans.get(position);

        holder.start.setOnClickListener(v -> {

            listener.onStudyClicked(plan);

        });

        holder.subject.setText(plan.getSubject_name());

        holder.minutes.setText(
                "Allocated Time : "
                        + plan.getAllocated_minutes()
                        + " Minutes"
        );

        if (plan.getCompleted() == 1) {

            holder.start.setEnabled(false);

            holder.start.setText("Completed");

        } else {

            holder.start.setEnabled(true);

            holder.start.setText("Start Study Session");

        }

    }

    @Override
    public int getItemCount() {
        return studyPlans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView subject;
        TextView minutes;
        MaterialButton start;

        ViewHolder(View itemView) {
            super(itemView);

            subject = itemView.findViewById(R.id.txtSubject);
            minutes = itemView.findViewById(R.id.txtMinutes);
            start = itemView.findViewById(R.id.btnStart);
        }
    }
}