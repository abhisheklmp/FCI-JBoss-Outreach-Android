package gci.jbossoutreach.abhishek.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gci.jbossoutreach.abhishek.R;
import gci.jbossoutreach.abhishek.model.CollaboratorItem;

public class CollaboratorAdapter extends RecyclerView.Adapter<CollaboratorAdapter.ViewHolder> {

	private List<CollaboratorItem> collaboratorItemItems;
	private Context context;

	public CollaboratorAdapter(List<CollaboratorItem> listItems, Context context) {
		this.collaboratorItemItems = listItems;
		this.context = context;
	}


	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.collaborator_item, viewGroup, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
		CollaboratorItem listItem = collaboratorItemItems.get(i);
		viewHolder.collaborator_name.setText(listItem.getName());

	}

	@Override
	public int getItemCount() {
		return collaboratorItemItems.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		public TextView collaborator_name;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			collaborator_name = itemView.findViewById(R.id.collaborator_name);
		}
	}

}
