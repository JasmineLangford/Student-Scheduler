package com.example.student_scheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student_scheduler.R;
import com.example.student_scheduler.entities.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseListHolder> {
    class CourseListHolder extends RecyclerView.ViewHolder {
        private final TextView courseTitleView;
        private final TextView courseStartView;
        private final TextView courseEndView;

        private CourseListHolder(View courseItem) {
            super(courseItem);
            courseTitleView = courseItem.findViewById(R.id.course_title);
            courseStartView = courseItem.findViewById(R.id.course_start);
            courseEndView = courseItem.findViewById(R.id.course_end);

            courseItem.setOnClickListener(view -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("course_id", current.getCourseID());
                    intent.putExtra("term_id", current.getTermID());
                    intent.putExtra("course_title", current.getCourseTitle());
                    intent.putExtra("course_start", current.getCourseStartDate());
                    intent.putExtra("course_end", current.getCourseEndDate());
                    intent.putExtra("course_status", current.getCourseStatus());
                    intent.putExtra("instructor_name", current.getInstructorName());
                    intent.putExtra("instructor_email", current.getInstructorEmail());
                    intent.putExtra("instructor_phone", current.getInstructorPhone());
                    intent.putExtra("course_notes",current.getCourseNote());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseListHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseListHolder holder, int position) {
        if (mCourses != null) {
            Course current = mCourses.get(position);
            String title = current.getCourseTitle();
            String start = current.getCourseStartDate();
            String end = current.getCourseEndDate();

            holder.courseTitleView.setText(title);
            holder.courseStartView.setText(start);
            holder.courseEndView.setText(end);
        }
    }

    @Override
    public int getItemCount() {
        if(mCourses == null) {
            return 0;
        }
        else {
            return mCourses.size();
        }
    }

    public void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

}
