package io.mochadwi.todo_go.todo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.mochadwi.todo_go.R;
import io.mochadwi.todo_go.RealmBaseActivity;
import io.mochadwi.todo_go.model.todo.TodoItem;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ToDoActivity extends RealmBaseActivity {

    // UI
    @BindView(R.id.realm_rv_item) RealmRecyclerView rvItems;
    @BindView(R.id.fab) FloatingActionButton fab;

    // DATA
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Realm.init(this);

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    private void initView() {
        resetRealm();
        realm = Realm.getInstance(getRealmConfig());
        RealmResults<TodoItem> toDoItems = realm
                .where(TodoItem.class)
                .findAllSorted("id", Sort.ASCENDING);
        ToDoRealmAdapter toDoRealmAdapter = new ToDoRealmAdapter(this, toDoItems, realm, true, true);
        rvItems.setAdapter(toDoRealmAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildAndShowInputDialog();
            }
        });
    }

    private void buildAndShowInputDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ToDoActivity.this);
        builder.setTitle("Create A Task");

        LayoutInflater li = LayoutInflater.from(this);
        View dialogView = li.inflate(R.layout.to_do_dialog_view, null);
        final EditText inputTitle = (EditText) dialogView.findViewById(R.id.input_title);
        final EditText inputDescription = (EditText) dialogView.findViewById(R.id.input_description);
        final CalendarView inputDate = (CalendarView) dialogView.findViewById(R.id.input_date);

        builder.setView(dialogView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addToDoItem(inputTitle.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = builder.show();
        inputTitle.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE ||
                                (event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                            dialog.dismiss();
                            addToDoItem(inputTitle.getText().toString());
                            return true;
                        }
                        return false;
                    }
                });
    }

    private void addToDoItem(String toDoItemText) {
        if (toDoItemText == null || toDoItemText.length() == 0) {
            Toast
                    .makeText(this, "Empty ToDos don't get stuff done!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        realm.beginTransaction();
        TodoItem todoItem = realm.createObject(TodoItem.class, System.currentTimeMillis());
        todoItem.setTitle(toDoItemText);
        todoItem.setDescription(toDoItemText);
        todoItem.setDate(toDoItemText);
        realm.commitTransaction();
    }
}
