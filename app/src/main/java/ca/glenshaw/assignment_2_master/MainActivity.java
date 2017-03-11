package ca.glenshaw.assignment_2_master;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * MainActivity
 * Core of application borrowed from P.Campbell's class material
 * https://github.com/AndroidConu-2017/06-adapters-adapterviews
 * https://github.com/AndroidConu-2017/menus-options-popups
 *
 * Application that uses a ListView and an adapter to display 10 images.
 * ListItem shows small images along with it's name.
 * When clicked DisplayImageActivity is called.
 *
 * Options menu provides user with ability to access source web site for data
 * provided in item descriptions along with copyright info for the images.
 *
 * Thanks to AllAboutBirds.org of TheCornellLabs for the data.
 * All images are my own and are copyrighted 2017 - GlenShaw.ca
 *
 * @author Glen Shaw
 * @version 1.0
 *
 */
public class MainActivity extends AppCompatActivity
{
    private String TAG = "ASS2";  // tag for Logging
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "MainActivity->onCreate()");

        // Storing data array's for information image names -small and large, names, descriptions, mp3 sound file.
        // all info stored in value, drawable, raw resource folders
        String[] names = getResources().getStringArray(R.array.image_names);
        String[] descriptions = getResources().getStringArray(R.array.image_facts);
        int[] image_Small = {   R.drawable.birds_1_sm,  R.drawable.birds_2_sm,  R.drawable.birds_3_sm,
                        R.drawable.birds_4_sm,  R.drawable.birds_5_sm,  R.drawable.birds_6_sm,
                        R.drawable.birds_7_sm,  R.drawable.birds_8_sm,  R.drawable.birds_9_sm,
                        R.drawable.birds_10_sm
                    };
        int[] image_large = {     R.drawable.birds_1, R.drawable.birds_2, R.drawable.birds_3,
                                R.drawable.birds_4, R.drawable.birds_5, R.drawable.birds_6,
                                R.drawable.birds_7, R.drawable.birds_8, R.drawable.birds_9,
                                R.drawable.birds_10
                          };
        String[] audioFileNames = getResources().getStringArray(R.array.image_sound);

        //listview & adapter assignments
        ListView lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(new LVIAdapter(this, names, image_Small, descriptions, image_large, audioFileNames));
    }

    /**
     * Options menu
     * Option menu offers user with 2 choices
     * WWW - open website where the descriptions were borrowed from
     * About US - open alertbox with copyright info
     *
     * @param menu
     * @return boolean
     */
    public boolean onCreateOptionsMenu(Menu menu)
    {   // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        Log.d(TAG, "MainActivity->onCreateOptionsMenu");
        return true;
    }

    /**
     * option Menu display's menu in title bar
     * provide user ability to select WWW which return the source's web site link in an
     * new web browser(implicit intent) Source for the data used.
     *
     * Provide an alertbox that displays the copyright information
     * concerning the data and images used in this app.
     * Android.com used as a reference
     * https://developer.android.com/guide/topics/ui/dialogs.html#CustomLayout
     *
     * Add my name & photo
     * @param item menuitem
     *
     * @return boolean
     */
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.d(TAG, "MainActivity->onOptionsItemSelected()");
        Intent intent = null;
        switch (item.getItemId())
        {
            case R.id.www_id:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.allaboutbirds.org/"));
                if (intent != null)
                {
                    try
                    {
                        startActivity(intent);
                    } catch (Exception e)
                    {
                        Toast.makeText(this, "No Activity For this intent", Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            case R.id.about_us_id:
                AlertDialog.Builder builder = new AlertDialog.Builder(this); //getActivity());
                //
                // Get the layout inflater
                LayoutInflater inflater = getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(inflater.inflate(R.layout.alert_layout, null));

                //
                builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
                // Add a button
                builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {   // User clicked OK button
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            default:
                return false;
        }

    }

    /**
     * ListViewImageAdapter extends BaseAdapter
     * adpater is the go-between the data and the views
     *
     * @author Glen Shaw
     * @version 1.0
     *
     */
    public class LVIAdapter extends BaseAdapter
    {
        //arrays for storing data
        private Context context;
        String [] listImages;
        int [] listIdImages;
        String [] listDescription;
        int [] listIdLargeImages;
        String [] listAudioFileNames;
        LayoutInflater inflater;

        //constructor
        public LVIAdapter(Context c, String[] list, int[] imageId, String[] listDesc, int[] imageIDlarge, String[] listAudio)
        {
            context = c;
            listImages = list;
            listIdImages = imageId;
            listDescription = listDesc;
            listIdLargeImages = imageIDlarge;
            listAudioFileNames = listAudio;
            inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        //return length of data array
        public int getCount() {
            return listImages.length;
        }

        //return data array position
        public Object getItem(int position) {
            return position;
        }

        //return item id position
        public long getItemId(int position) { return position; }

        //class used to contain listview row items
        public class ViewHolder
        {
            TextView tv; ImageView iv; ImageView ivPlay;
        }

        // create a new layout for each item referenced by the Adapter
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            ViewHolder vh = new ViewHolder();
            View row = convertView;

            if (convertView == null)
            {
                row = inflater.inflate(R.layout.custom_item, null);
                // Removed image loading here to eliminate error
                // de-synchronised images in listview when scrolling
                row.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            //load images & text here instead of in previous if
            //which created error's when scrolling
            vh.tv = (TextView) row.findViewById(R.id.itemTV);
            vh.iv = (ImageView) row.findViewById(R.id.itemIV);
            vh.tv.setText(listImages[position]);
            vh.iv.setImageResource(listIdImages[position]);

            //create listener and store extra data in intent then start DisplayImageActivity
            //extra data will be used when DisplayImageActivity is called
            row.setOnClickListener(  new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(v.getContext(), DisplayImageActivity.class);
                    myIntent.putExtra("index",listImages[position]);
                    myIntent.putExtra("imageName",listImages[position]);
                    myIntent.putExtra("indexID",listIdLargeImages[position]);
                    myIntent.putExtra("imageFact",listDescription[position]);
                    myIntent.putExtra("audioFile",listAudioFileNames[position]);
                    Log.d(TAG, "LVIAdapter->getView->onClickListener()");

                    startActivity(myIntent);
                }
            });
            Log.d(TAG, "MainActivity->LVIAdapter->getView()");
            return row;
        }

    }  // ListViewImageAdapter

}
