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
        private final TextView courseItemView;
        private CourseListHolder(View itemview) {
            super(itemview);
            courseItemView = itemview.findViewById(R.id.textView2);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context,CourseDetails.class);
                    intent.putExtra("id",current.getCourseID());
                    intent.putExtra("title",current.getCourseTitle());
                    intent.putExtra("courseStartDate",current.getCourseStartDate());
                    intent.putExtra("courseEndDate",current.getCourseEndDate());
                    intent.putExtra("course_status",current.getCourseStatus());
                    intent.putExtra("instructor_name",current.getInstructorName());
                    intent.putExtra("instructor_email",current.getInstructorEmail());
                    intent.putExtra("instructor_phone",current.getInstructorPhone());

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
        View itemView = mInflater.inflate(R.layout.course_list_item,parent,false);
        return new CourseListHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseListHolder holder, int position) {
        if(mCourses != null) {
            Course current = mCourses.get(position);
            String title = current.getCourseTitle();
            holder.courseItemView.setText(title);
        }
        else{
            holder.courseItemView.setText("No Available Courses.");
        }
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

}
