package io.mochadwi.todo_go.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
//    private Realm mRealm;

    public class ViewHolder extends RealmViewHolder {

        @BindView(R.id.todo_title_text_view) TextView todoTitleTextView;
        @BindView(R.id.todo_description_text_view) TextView todoDescriptionTextView;
        @BindView(R.id.todo_deadline_text_view) TextView todoDeadlineTextView;
        @BindView(R.id.todo_image_view) ImageView todoImageView;

        public ViewHolder(View container) {
            super(container);
            ButterKnife.bind(this, container);

            container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    buildAndShowInputDialog();
                    return true;
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
//        this.mRealm = realm;
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
        viewHolder.todoTitleTextView.setText(toDoItem.getDescription());
        viewHolder.todoDescriptionTextView.setText(toDoItem.getDescription());
        viewHolder.todoDeadlineTextView.setText(toDoItem.getDescription());
    }

//    private void buildAndShowInputDialog() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
//        builder.setTitle("Update A Task");
//
//        LayoutInflater li = LayoutInflater.from(mCtx);
//        View dialogView = li.inflate(R.layout.to_do_dialog_view, null);
//        ButterKnife.bind(dialogView);
//        final EditText inputTitle = (EditText) dialogView.findViewById(R.id.input_title);
//        final EditText inputDescription = (EditText) dialogView.findViewById(R.id.input_description);
//        final CalendarView inputDate = (CalendarView) dialogView.findViewById(R.id.input_date);
//
//        builder.setView(dialogView);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                editToDoItem(inputTitle.getText().toString());
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        final AlertDialog dialog = builder.show();
//        inputTitle.setOnEditorActionListener(
//                new EditText.OnEditorActionListener() {
//                    @Override
//                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                        if (actionId == EditorInfo.IME_ACTION_DONE ||
//                                (event.getAction() == KeyEvent.ACTION_DOWN &&
//                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
//                            dialog.dismiss();
//                            editToDoItem(inputTitle.getText().toString());
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//    }

//    private void editToDoItem(String toDoItemText) {
//        if (toDoItemText == null || toDoItemText.length() == 0) {
//            Toast
//                    .makeText(mCtx, "Empty ToDos don't get stuff done!", Toast.LENGTH_SHORT)
//                    .show();
//            return;
//        }
//
//        mRealm.beginTransaction();
//        TodoItem todoItem = mRealm.createObject(TodoItem.class, System.currentTimeMillis());
//        todoItem.setDescription(toDoItemText);
//        mRealm.commitTransaction();
//    }
}
