package gci.jbossoutreach.abhishek;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gci.jbossoutreach.abhishek.adapter.RepoAdapter;
import gci.jbossoutreach.abhishek.model.ListItem;

public class MainActivity extends AppCompatActivity {


	private static final String API_URL = "https://api.github.com/users/JBossOutreach/repos";
	private RecyclerView recyclerView;
	private RecyclerView.Adapter adapter;
	private List<ListItem> listItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		recyclerView = findViewById(R.id.repo_recycler_view);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		listItems = new ArrayList<>();
		loadRecyclerViewData();

	}

	private void loadRecyclerViewData() {
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Loading Data...");
		progressDialog.show();

		StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				progressDialog.dismiss();
				setTitle("JBoss OutReach GitHub Repos");

				try {
					JSONArray jsonArray = new JSONArray(response);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						ListItem item = new ListItem(
								jsonObject.getString("name"),
								jsonObject.getString("description")
						);
						listItems.add(item);
					}

					adapter = new RepoAdapter(listItems , getApplicationContext());
					recyclerView.setAdapter(adapter);


				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

				Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();

			}
		});

		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(stringRequest);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			Intent launchURL = new Intent(MainActivity.this, AboutActivity.class);
			startActivity(launchURL);
		}

		return super.onOptionsItemSelected(item);
	}
}
