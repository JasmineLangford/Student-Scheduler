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
import com.example.student_scheduler.entities.Term;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermListHolder> {

    class TermListHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;

        private TermListHolder(@NonNull View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.term_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetails.class);
                    intent.putExtra("term_id", current.getTermID());
                    intent.putExtra("term_title", current.getTermTitle());
                    intent.putExtra("term_start", current.getTermStartDate());
                    intent.putExtra("term_end", current.getTermEndDate());
                }
            });
        }
    }

    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermAdapter.TermListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermListHolder((itemView));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermListHolder holder, int position) {
        if (mTerms != null) {
            Term current = mTerms.get(position);
            String termTitle = current.getTermTitle();
            holder.termItemView.setText(termTitle);
            holder.termItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TermDetails.class);
                    context.startActivity(intent);
                }
            });
        } else {
            holder.termItemView.setText("No available terms.");
        }
    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    public void setTerms(List<Term> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }
}
