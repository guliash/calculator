package com.guliash.calculator.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guliash.calculator.R;
import com.guliash.parser.StringVariable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VariablesAdapter extends RecyclerView.Adapter<VariablesAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.variable)
        TextView variable;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface Callbacks {
        void onActivated(int pos);

        void onDeactivated(int pos);

        void onChosen(int pos);
    }

    private static final int NO_ACTIVATED = -1;

    private List<? extends StringVariable> variables;
    private Callbacks callbacks;
    private int activatedPosition = NO_ACTIVATED;

    public VariablesAdapter(List<? extends StringVariable> variables, Callbacks callbacks) {
        this.variables = variables;
        this.callbacks = callbacks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.variable_holder, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.variable.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                VariablesAdapter.this.onLongClick(holder);
                return true;
            }
        });
        holder.variable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VariablesAdapter.this.onClick(holder);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StringVariable variable = variables.get(position);
        holder.variable.setText(String.format("%s = %s", variable.getName(), variable.getValue()));
        holder.variable.setActivated(position == activatedPosition);
    }

    @Override
    public int getItemCount() {
        return variables.size();
    }

    private void onClick(ViewHolder holder) {
        int pos = holder.getAdapterPosition();
        if(pos != RecyclerView.NO_POSITION) {
            callbacks.onChosen(pos);
        }
    }

    private void onLongClick(ViewHolder holder) {
        int pos = holder.getAdapterPosition();
        if(pos == RecyclerView.NO_POSITION) {
            return;
        }

        holder.variable.setActivated(!holder.variable.isActivated());
        int prevPos = activatedPosition;
        if(holder.variable.isActivated()) {
            activatedPosition = pos;
            callbacks.onActivated(pos);
        } else {
            activatedPosition = NO_ACTIVATED;
            callbacks.onDeactivated(pos);
        }

        if(prevPos != NO_ACTIVATED) {
            VariablesAdapter.this.notifyItemChanged(prevPos);
        }
    }

}