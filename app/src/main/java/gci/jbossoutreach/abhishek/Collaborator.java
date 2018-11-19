package gci.jbossoutreach.abhishek;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
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

import gci.jbossoutreach.abhishek.adapter.CollaboratorAdapter;
import gci.jbossoutreach.abhishek.model.CollaboratorItem;

public class Collaborator extends AppCompatActivity {

	private static String API_URL = "https://api.github.com/repos/JBossOutreach/";
	private static String repo_name = "lead-management-android";
	private RecyclerView recyclerView;
	private RecyclerView.Adapter adapter;
	TextView textView;
	private List<CollaboratorItem> listItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contributors);


		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}

		Intent i = getIntent();
		Bundle b = i.getExtras();

		if (b != null) {
			repo_name = (String) b.get("repo_name");
			setTitle("Collaborators");
			textView = findViewById(R.id.viewed_repo_name);
			textView.setText(repo_name);

		}

		recyclerView = findViewById(R.id.collaborator_recycler_view);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		listItems = new ArrayList<>();
		loadRecyclerViewData();
	}



	private void loadRecyclerViewData() {
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Loading Data...");
		progressDialog.show();

		StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL.concat(repo_name).concat("/contributors"), new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				progressDialog.dismiss();

				try {
					JSONArray jsonArray = new JSONArray(response);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						String value = jsonObject.getString("login");
						CollaboratorItem item = new CollaboratorItem(value);
						listItems.add(item);
					}

					adapter = new CollaboratorAdapter(listItems , getApplicationContext());
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
	public boolean onOptionsItemSelected(MenuItem item) {
		onBackPressed();
		return true;
	}


}
