package io.mochadwi.todo_go.todo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import io.mochadwi.todo_go.R;
import io.mochadwi.todo_go.model.todo.TodoItem;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

import static io.mochadwi.todo_go.utils.CustomColor.COLORS;

/**
 * Created by mochadwi on 9/1/17.
 */

public class ToDoRealmAdapter
        extends RealmBasedRecyclerViewAdapter<TodoItem, ToDoRealmAdapter.ViewHolder> {

    public class ViewHolder extends RealmViewHolder {

        public TextView todoTextView;
        public ViewHolder(FrameLayout container) {
            super(container);
            this.todoTextView = (TextView) container.findViewById(R.id.todo_text_view);
        }
    }

    public ToDoRealmAdapter(
            Context context,
            RealmResults<TodoItem> realmResults,
            boolean automaticUpdate,
            boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
        View v = inflater.inflate(R.layout.to_do_item_view, viewGroup, false);
        ViewHolder vh = new ViewHolder((FrameLayout) v);
        return vh;
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
        final TodoItem toDoItem = realmResults.get(position);
        viewHolder.todoTextView.setText(toDoItem.getDescription());
        viewHolder.itemView.setBackgroundColor(
                COLORS[(int) (toDoItem.getId() % COLORS.length)]
        );
    }
}
