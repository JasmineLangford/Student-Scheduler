package com.example.student_scheduler.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student_scheduler.R;
import com.example.student_scheduler.entities.Assessment;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentTitleView;
        private final TextView assessmentTypeView;

        private AssessmentViewHolder(View assessmentItem) {
            super(assessmentItem);
            assessmentTitleView = assessmentItem.findViewById(R.id.assessment_title);
            assessmentTypeView = assessmentItem.findViewById(R.id.assessment_type);

            assessmentItem.setOnClickListener(view -> {
                int position = getBindingAdapterPosition();
                final Assessment current = mAssessments.get(position);
                Intent intent = new Intent(context, AssessmentDetails.class);
                intent.putExtra("assessment_id",current.getAssessmentID());
                intent.putExtra("course_id",current.getCourseID());
                intent.putExtra("assessment_title",current.getAssessmentTitle());
                intent.putExtra("assessment_type",current.getAssessmentType());
                intent.putExtra("assessment_end",current.getAssessmentEndDate());
                context.startActivity(intent);
            });
        }
    }

    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item,parent,false);
        return new AssessmentViewHolder((itemView));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if(mAssessments != null) {
            Assessment current = mAssessments.get(position);
            String assessmentTitle = current.getAssessmentTitle();
            String assessmentType = current.getAssessmentType();

            holder.assessmentTitleView.setText(assessmentTitle);
            holder.assessmentTypeView.setText(assessmentType);
        }
    }

    @Override
    public int getItemCount() {
        if(mAssessments == null) {
            return 0;
        }
        else {
            return mAssessments.size();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setAssessments(List<Assessment> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }

}
