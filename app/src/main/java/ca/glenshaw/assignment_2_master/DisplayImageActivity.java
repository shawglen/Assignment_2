package ca.glenshaw.assignment_2_master;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * DisplayImageActivity
 * Activity that displays the larger image with title and description along with the
 * ability to play the selected birds call.
 * All data is passed in intents extra bundle
 *
 * @author Glen Shaw
 * @version 1.0
 */
public class DisplayImageActivity extends AppCompatActivity
{
    //variables
    String TAG = "ASS2";  // tag for Logging
    MediaPlayer mp = new MediaPlayer();
    String audioPath;
    ImageView ivPlayImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        //get elements handle
        final ImageView ivImage = (ImageView) findViewById(R.id.itemDI_IV);
        final TextView tvTextTitle = (TextView) findViewById(R.id.itemDI_TV);
        ivPlayImage = (ImageView) findViewById(R.id.itemDI_Play_IV);
        final TextView tvTextFact = (TextView) findViewById(R.id.itemDI_FACT_TV);

        //create an intent & set index
        Intent myIntent = getIntent();

        //add html formatting & extract data from intents bundle.
        tvTextTitle.setText(myIntent.getStringExtra("imageName"));                  //set Name text
        ivPlayImage.setImageResource(R.drawable.player_play);                       //set Player image
        tvTextFact.setText(Html.fromHtml(myIntent.getStringExtra("imageFact")));    //set Fact
        ivImage.setImageResource(myIntent.getIntExtra("indexID",1));                //set image
        String audiofilename = myIntent.getStringExtra("audioFile");                //set audio file

        //switch filename and call to the mp3 resource file
        switch(audiofilename)
        {
            case "a50a.mp3" :
                mp = MediaPlayer.create(this,R.raw.a50a);
                break;
            case "a210a.mp3" :
                mp = MediaPlayer.create(this,R.raw.a210a);
                break;
            case "a132a.mp3" :
                mp = MediaPlayer.create(this,R.raw.a132a);
                break;
            case "a1861a.mp3" :
                mp = MediaPlayer.create(this,R.raw.a1861a);
                break;
            case "a4303a.mp3" :
                mp = MediaPlayer.create(this,R.raw.a4303a);
                break;
            case "cubangreenwoodpecker.mp3" :
                mp = MediaPlayer.create(this,R.raw.cubangreenwoodpecker);
                break;
            case "a542a.mp3" :
                mp = MediaPlayer.create(this,R.raw.a542a);
                break;
            case "a5068a.mp3" :
                mp = MediaPlayer.create(this,R.raw.a5068a);
                break;
            case "a1165a.mp3" :
                mp = MediaPlayer.create(this,R.raw.a1165a);
                break;
            case "a286a.mp3" :
                mp = MediaPlayer.create(this,R.raw.a286a);
                break;

        }
        Log.d(TAG, "DisplayImageActivity->onCreate()");
    }

    /**
     * playMp3
     * function that plays an Mp3 file of the selected bird calls
     * Stackoverflow used as a reference to see samples of how to play mp3's
     *
     * Check if player exists
     * depending on player state change the icon to show play or pause image
     * start playing audio file.
     *
     * @param v view
     */
    public void playMp3(View v)
    {
        if(! (mp == null))
        {
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
            {
                @Override
                public void onCompletion(MediaPlayer mp)
                {
                    ivPlayImage.setImageResource(R.drawable.player_play);
                    Log.d(TAG, "DisplayImageActivity->playMp3() set image play");
                }
            });
            mp.start();
            Log.d(TAG, "DisplayImageActivity->playMp3() MediaPlayer Started");
            if (mp.isPlaying())
            {
                ivPlayImage.setImageResource(R.drawable.player_pause);
                Log.d(TAG, "DisplayImageActivity->playMp3() set image pause");
            } else {
                ivPlayImage.setImageResource(R.drawable.player_play);
                Log.d(TAG, "DisplayImageActivity->playMp3() set image play");
            }
        } else
        {
            Log.d(TAG, "DisplayImageActivity->playMp3() MediaPlayer NULL");
        }
    }

}
