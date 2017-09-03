package io.mochadwi.todo_go.todo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
    private String selectedDate;

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
//        resetRealm();
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
                addToDoItem(inputTitle.getText().toString(),
                        inputDescription.getText().toString(),
                        selectedDate);
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

    private void addToDoItem(String title, String desc, String due) {
        if (isEmpty(title)) {
            Toast
                    .makeText(this, "Empty ToDos Title don't get stuff done!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        realm.beginTransaction();
        TodoItem todoItem = realm.createObject(TodoItem.class, System.currentTimeMillis());
        todoItem.setTitle(title);
        todoItem.setDescription(desc);
        todoItem.setDate(due);
        realm.commitTransaction();
    }

    private boolean isEmpty(String word) {
        if (word == null || word.length() == 0) {
            return true;
        }

        return false;
    }
}
