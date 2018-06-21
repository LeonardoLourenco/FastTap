package leonardolourenco.fasttap;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        TextView textViewTwitter = (TextView) findViewById(R.id.textViewTwitter);
        TextView textViewTumblr = (TextView) findViewById(R.id.textViewTumblr);
        TextView textViewInstagram = (TextView) findViewById(R.id.textViewInstagram);
        TextView textViewDiscord = (TextView) findViewById(R.id.textViewDiscord);
        textViewTwitter.setMovementMethod(LinkMovementMethod.getInstance());
        textViewTumblr.setMovementMethod(LinkMovementMethod.getInstance());
        textViewInstagram.setMovementMethod(LinkMovementMethod.getInstance());
        textViewDiscord.setMovementMethod(LinkMovementMethod.getInstance());


    }

    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
