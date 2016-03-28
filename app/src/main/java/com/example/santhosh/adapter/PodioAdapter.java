package com.example.santhosh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.santhosh.data.Item;
import com.example.santhosh.data.OrganisationItem;
import com.example.santhosh.podio.R;
import com.example.santhosh.data.WorkItem;

import java.util.ArrayList;

public class PodioAdapter extends ArrayAdapter<Item> {

	private Context context;
	private ArrayList<Item> items;
	private LayoutInflater mInflater;

	public PodioAdapter(Context context, ArrayList<Item> items) {
		super(context,0, items);
		this.context = context;
		this.items = items;
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		final Item item = items.get(position);
		if (item != null) {
			if(item.isSection()){
				OrganisationItem organisationItem = (OrganisationItem)item;
				view = mInflater.inflate(R.layout.activity_list_organization, null);
				
				final TextView sectionView = (TextView) view.findViewById(R.id.list_item_section_text);
				sectionView.setText(organisationItem.getTitle());
			}else{
				WorkItem workItem = (WorkItem)item;
				view = mInflater.inflate(R.layout.activity_list_workspace, null);
				final TextView title = (TextView)view.findViewById(R.id.list_workspace);
				
				if (title != null) 
					title.setText(workItem.title);
			}
		}
		return view;
	}

}
