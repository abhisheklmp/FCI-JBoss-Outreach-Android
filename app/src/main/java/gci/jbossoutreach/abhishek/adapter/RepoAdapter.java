package gci.jbossoutreach.abhishek.adapter;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import gci.jbossoutreach.abhishek.AboutActivity;
import gci.jbossoutreach.abhishek.Collaborator;
import gci.jbossoutreach.abhishek.MainActivity;
import gci.jbossoutreach.abhishek.R;
import gci.jbossoutreach.abhishek.model.ListItem;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

	private List<ListItem> listItems;
	private Context context;

	public RepoAdapter(List<ListItem> listItems, Context context) {
		this.listItems = listItems;
		this.context = context;
	}


	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.repo_item, viewGroup, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
		final ListItem listItem = listItems.get(i);
		viewHolder.repo_name.setText(listItem.getName());
		viewHolder.repo_desc.setText(listItem.getDesc());

		viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent launchURL = new Intent(context, Collaborator.class);
				launchURL.putExtra("repo_name", listItem.getName());
				context.startActivity(launchURL);
			}
		});

	}

	@Override
	public int getItemCount() {
		return listItems.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		public TextView repo_name;
		public TextView repo_desc;
		public LinearLayout linearLayout;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			repo_name = itemView.findViewById(R.id.repo_name);
			repo_desc = itemView.findViewById(R.id.repo_desc);
			linearLayout = itemView.findViewById(R.id.repo_item_linear_layout);
		}
	}

}
