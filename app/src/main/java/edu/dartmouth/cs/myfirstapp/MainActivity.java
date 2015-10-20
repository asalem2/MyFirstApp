package edu.dartmouth.cs.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {

	private static final String TAG = "MyFirstApp";
	public final static String apiURL = "http://food2fork.com/api/search?key=acef32e0041cd5466e8ea8b34fa1e560&q=";
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	/** Called when the user clicks the Send button */

	public void sendMessage(View view) {

		EditText editTextname = (EditText) findViewById(R.id.edit_restroname);


		String messagename = editTextname.getText().toString();


		if( messagename != null && !messagename.isEmpty())
		{

				//replace all spaces with parsable string
				messagename  = messagename.replaceAll("\\s", "%20");


				//get url
				String urlString = apiURL +messagename;

				// pass url to display activity
				Intent intent = new Intent(getApplicationContext(), DisplayMessageActivity.class);
				intent.putExtra(EXTRA_MESSAGE, urlString);
				startActivity(intent);



		}
		Log.d(TAG, "sendMessage");
	}



}

