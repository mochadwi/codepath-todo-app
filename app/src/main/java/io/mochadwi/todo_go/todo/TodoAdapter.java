/*
 * Copyright 2016 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.mochadwi.todo_go.todo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import io.mochadwi.todo_go.R;
import io.mochadwi.todo_go.models.Todo;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


class TodoAdapter extends RealmRecyclerViewAdapter<Todo, TodoAdapter.MyViewHolder> {

    private boolean inDeletionMode = false;
    private Set<Integer> todosToDelete = new HashSet<Integer>();

    TodoAdapter(OrderedRealmCollection<Todo> data) {
        super(data, true);
        setHasStableIds(true);
    }

    void enableDeletionMode(boolean enabled) {
        inDeletionMode = enabled;
        if (!enabled) {
            todosToDelete.clear();
        }
        notifyDataSetChanged();
    }

    Set<Integer> getTodosToDelete() {
        return todosToDelete;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Todo obj = getItem(position);
        holder.data = obj;
        //noinspection ConstantConditions
        holder.title.setText(obj.getTitle());
        holder.description.setText(obj.getDescription());
        holder.due.setText(obj.getDue());
        holder.deletedCheckBox.setChecked(todosToDelete.contains(obj.getId()));
        if (inDeletionMode) {
            holder.deletedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        todosToDelete.add(obj.getId());
                    } else {
                        todosToDelete.remove(obj.getId());
                    }
                }
            });
        } else {
            holder.deletedCheckBox.setOnCheckedChangeListener(null);
        }
        holder.deletedCheckBox.setVisibility(inDeletionMode ? View.VISIBLE : View.GONE);
    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).getId();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        TextView due;
        CheckBox deletedCheckBox;
        public Todo data;

        MyViewHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
            title = (TextView) view.findViewById(R.id.todo_title_text_view);
            description = (TextView) view.findViewById(R.id.todo_description_text_view);
            due = (TextView) view.findViewById(R.id.todo_due_text_view);
            deletedCheckBox = (CheckBox) view.findViewById(R.id.checkBox);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
