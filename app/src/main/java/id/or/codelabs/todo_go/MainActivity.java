package id.or.codelabs.todo_go;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvCaption;
    private ImageView ivCaption;
    private int color;
    private int[] photos = {R.drawable.google, R.drawable.android, R.drawable.browser, R.drawable.flappy_bird
            , R.drawable.futurestudio, R.drawable.gmail, R.drawable.google_play, R.drawable.granblue
            , R.drawable.magnetometer_icon, R.drawable.okko, R.drawable.play_games, R.drawable.quora
            , R.drawable.sega, R.drawable.tumblr, R.drawable.whatsapp, R.drawable.youtube_icon};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);

        tvCaption = (TextView) findViewById(R.id.tv_caption);
        ivCaption = (ImageView) findViewById(R.id.iv_caption);
    }

    public void changeColor(View view) {
        Random rnd = new Random();
        color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        tvCaption.setTextColor(color);
    }

    public void changeImage(View view) {
        Random rand = new Random();
        int i = rand.nextInt(photos.length);
        ivCaption.setImageResource(photos[i]);
    }
}
