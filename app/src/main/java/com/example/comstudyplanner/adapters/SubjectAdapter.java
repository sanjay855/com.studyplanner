package com.example.comstudyplanner.adapters;

import android.app.DatePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comstudyplanner.R;
import com.example.comstudyplanner.models.Subject;

import java.util.Calendar;
import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private final List<Subject> subjects;

    public SubjectAdapter(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_item, parent, false);

        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {

        Subject subject = subjects.get(position);

        holder.txtName.setText(subject.getSubject_name());
        holder.txtCode.setText(subject.getSubject_code());
        holder.txtCredits.setText("Credits : " + subject.getCredits());

        // Restore previous values
        holder.examDate.setText(subject.getExam_date());

        String[] difficulties = {
                "Easy",
                "Medium",
                "Hard"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                holder.itemView.getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                difficulties
        );

        holder.spinner.setAdapter(adapter);

        // Restore selected difficulty
        int selectedIndex = 0;

        switch (subject.getDifficulty()) {
            case "Medium":
                selectedIndex = 1;
                break;

            case "Hard":
                selectedIndex = 2;
                break;
        }

        holder.spinner.setSelection(selectedIndex);

        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view,
                                       int position,
                                       long id) {

                subject.setDifficulty(
                        parent.getItemAtPosition(position).toString()
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.examDate.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            DatePickerDialog dialog = new DatePickerDialog(
                    holder.itemView.getContext(),
                    (view, year, month, dayOfMonth) -> {

                        month++;

                        String date =
                                year + "-" +
                                        String.format("%02d", month) + "-" +
                                        String.format("%02d", dayOfMonth);

                        holder.examDate.setText(date);

                        subject.setExam_date(date);

                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            dialog.show();

        });

    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    static class SubjectViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtCode;
        TextView txtCredits;

        Spinner spinner;

        EditText examDate;

        SubjectViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtSubjectName);
            txtCode = itemView.findViewById(R.id.txtSubjectCode);
            txtCredits = itemView.findViewById(R.id.txtCredits);

            spinner = itemView.findViewById(R.id.spDifficulty);

            examDate = itemView.findViewById(R.id.etExamDate);
        }
    }
}