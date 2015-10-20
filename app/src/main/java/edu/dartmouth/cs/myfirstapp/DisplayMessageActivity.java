package edu.dartmouth.cs.myfirstapp;

import android.app.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.dartmouth.cs.myfirstapp.data.ObjectAdapter;
import edu.dartmouth.cs.myfirstapp.data.objectsmodel;

public class DisplayMessageActivity extends Activity {

	private ListView lvobjects;
	private TextView textmessage;
	private static final String TAG = "MyFirstApp";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the message from the intent
		Intent intent = getIntent();
		String urlstring = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		Log.d(TAG, "Got Intent");

		lvobjects = new ListView(this);
		textmessage = new TextView(this);
		new CallAPI().execute(urlstring);


	}

	private class CallAPI extends AsyncTask<String, String, List<objectsmodel>> {

			HttpURLConnection connection = null;
			BufferedReader reader = null;
			String resultstring = null;

			@Override
			protected List<objectsmodel> doInBackground(String... params) {

				List<objectsmodel> objectsmodelList = new ArrayList<>();
				URL url = null;

				try {
					//reading API data and storing it in buffer

					url = new URL(params[0]);
					connection = (HttpURLConnection) url.openConnection();
					connection.connect();

					InputStream stream = connection.getInputStream();
					reader = new BufferedReader(new InputStreamReader(stream));
					StringBuffer buffer = new StringBuffer();

					String line = "";
					while ((line = reader.readLine()) != null) {
						buffer.append(line);
					}

					resultstring = buffer.toString();

					//Json result parsing starts here

					JSONObject parentobject = new JSONObject(resultstring);
					JSONArray parentarray = parentobject.getJSONArray("recipes");


					for (int i = 0; i < parentarray.length(); i++) {
						JSONObject finalobject = parentarray.getJSONObject(i);
						objectsmodel objectmodel = new objectsmodel();
						objectmodel.setPublisher(finalobject.getString("publisher"));
						objectmodel.setF2f_url(finalobject.getString("f2f_url"));
						objectmodel.setTitle(finalobject.getString("title"));
						objectmodel.setSource_url(finalobject.getString("source_url"));
						objectmodel.setRecipe_id(finalobject.getString("recipe_id"));
						objectmodel.setImage_url(finalobject.getString("image_url"));
						objectmodel.setSocial_rank(finalobject.getInt("social_rank"));
						objectmodel.setPublisher_url(finalobject.getString("publisher_url"));



						objectsmodelList.add(objectmodel);
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
					try {
						if (reader != null) {
							reader.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				// returns list of objects in Jsoncode
				return objectsmodelList;
			}

			@Override
			protected void onPostExecute(List<objectsmodel> result) {
				// if no data available
				if(result.size() == 0) {
					textmessage.setText("Record Not found!!!!");
					setContentView(textmessage);
				}
				// display parsed data
				else {
					ObjectAdapter adapter = new ObjectAdapter(getApplicationContext(), R.layout.row, result);

					lvobjects.setAdapter(adapter);

					setContentView(lvobjects);
				}


			}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}