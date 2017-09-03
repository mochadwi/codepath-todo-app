package io.mochadwi.todo_go.todo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mochadwi.todo_go.R;
import io.mochadwi.todo_go.Todo;
import io.mochadwi.todo_go.model.todo.TodoItem;
import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

import static io.mochadwi.todo_go.utils.CustomColor.COLORS;

/**
 * Created by mochadwi on 9/1/17.
 */

public class ToDoRealmAdapter
        extends RealmBasedRecyclerViewAdapter<TodoItem, ToDoRealmAdapter.ViewHolder> {

    private Context mCtx;
    private Realm mRealm;
    private String selectedDate;

    public class ViewHolder extends RealmViewHolder {

        @BindView(R.id.todo_title_text_view) TextView todoTitleTextView;
        @BindView(R.id.todo_description_text_view) TextView todoDescriptionTextView;
        @BindView(R.id.todo_deadline_text_view) TextView todoDeadlineTextView;
        @BindView(R.id.todo_image_view) ImageView todoImageView;

        public ViewHolder(View container) {
            super(container);
            ButterKnife.bind(this, container);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public ToDoRealmAdapter(
            Context context,
            RealmResults<TodoItem> realmResults,
            Realm realm,
            boolean automaticUpdate,
            boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
        this.mCtx = context;
        this.mRealm = realm;
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
        View v = inflater.inflate(R.layout.to_do_item_view, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
        final TodoItem toDoItem = realmResults.get(position);
        viewHolder.todoImageView.setBackgroundColor(COLORS[(int) (toDoItem.getId() % COLORS.length)]);
        viewHolder.todoTitleTextView.setText(toDoItem.getTitle());
        viewHolder.todoDescriptionTextView.setText(toDoItem.getDescription());
        viewHolder.todoDeadlineTextView.setText(toDoItem.getDate());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildAndShowInputDialog(toDoItem);
//                Toast.makeText(mCtx, "This is dialog: " + toDoItem.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buildAndShowInputDialog(final TodoItem item) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setTitle("Update A Task");

        LayoutInflater li = LayoutInflater.from(mCtx);
        View dialogView = li.inflate(R.layout.to_do_dialog_view, null);
        final EditText inputTitle = (EditText) dialogView.findViewById(R.id.input_title);
        final EditText inputDescription = (EditText) dialogView.findViewById(R.id.input_description);
        final CalendarView inputDate = (CalendarView) dialogView.findViewById(R.id.input_date);

        inputTitle.setText(item.getTitle());
        inputDescription.setText(item.getDescription());

        inputDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = dayOfMonth + "/" + month + "/" + year;
            }
        });

        builder.setView(dialogView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editToDoItem(item);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void editToDoItem(final TodoItem item) {
        if (isEmpty(item.getTitle())) {
            Toast
                    .makeText(mCtx, "Empty ToDos don't get stuff done!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }


        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    TodoItem todo = bgRealm.where(TodoItem.class).equalTo("id", item.getId()).findFirst();
                    todo.setTitle(item.getTitle());
                    todo.setDescription(item.getDescription());
                    todo.setDate(item.getDate());
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    // Original queries and Realm objects are automatically updated.
                    Toast.makeText(mCtx, "Task has been updated!", Toast.LENGTH_SHORT).show();
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    private boolean isEmpty(String word) {
        if (word == null || word.length() == 0) {
            return true;
        }

        return false;
    }
}