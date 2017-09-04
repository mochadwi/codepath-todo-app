package io.mochadwi.todo_go.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mochadwi on 9/3/17.
 */

public class Todo extends RealmObject {

    public static final String FIELD_ID = "id";
    private static AtomicInteger INTEGER_COUNTER = new AtomicInteger(0);

    @SerializedName("id") @PrimaryKey private int id;

    @SerializedName("title") private String title;
    @SerializedName("description") private String description;
    @SerializedName("due") private String due;

    public Todo() {
    }

    public int getId() {
        return id;
    }

    public String getIdString() {
        return Integer.toString(id);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDue() {
        return due;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDue(String due) {
        this.due = due;
    }

    //  create() & delete() needs to be called inside a transaction.
    static void create(Realm realm) {
        create(realm, false);
    }

    static void create(Realm realm, boolean randomlyInsert) {
        Parent parent = realm.where(Parent.class).findFirst();
        RealmList<Todo> todos = parent.getTodoList();
        Todo todo = realm.createObject(Todo.class, increment());
        if (randomlyInsert && todos.size() > 0) {
            Random rand = new Random();
            todos.listIterator(rand.nextInt(todos.size())).add(todo);
        } else {
            todos.add(todo);
        }
    }

    static void create(Realm realm, Todo item) {
        Parent parent = realm.where(Parent.class).findFirst();
        RealmList<Todo> todos = parent.getTodoList();

        Todo todo = realm.createObject(Todo.class, autoIncrement(realm));
        todo.setTitle(item.getTitle());
        todo.setDescription(item.getDescription());
        todo.setDue(item.getDue());

        todos.add(todo);
    }

    static void update(Realm realm, Todo item) {
//        todo = realm.copyFromRealm(item);
    }

    static void delete(Realm realm, long id) {
        Todo todo = realm.where(Todo.class).equalTo(FIELD_ID, id).findFirst();
        // Otherwise it has been deleted already.
        if (todo != null) {
            todo.deleteFromRealm();
        }
    }

    private static int increment() {
        return INTEGER_COUNTER.getAndIncrement();
    }

    private static int autoIncrement(Realm realm) {
        Number currentId = realm.where(Todo.class).max(FIELD_ID);
        int nextId;
        if (currentId == null) {
            nextId = increment();
        } else {
            nextId = currentId.intValue() + 1;
        }

        return nextId;
    }

    public String toString(Realm realm) {
        return new Gson().toJson(realm.copyFromRealm(this));
    }
}
