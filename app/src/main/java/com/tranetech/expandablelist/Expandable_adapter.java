package com.tranetech.expandablelist;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Hiren Amaliyar
 */
public class Expandable_adapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listDataHeader;
	private HashMap<String, List<String>> _listDataChild;

	public Expandable_adapter(Context context, List<String> listDataHeader,
							  HashMap<String, List<String>> listDataChild) {


		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listDataChild;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
							 boolean isLastChild, View convertView, ViewGroup parent) {
		final String childText = (String) getChild(groupPosition, childPosition);
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.list_child, null);
		}
		TextView expandedListTextView = (TextView) convertView
				.findViewById(R.id.tv_listchild);

		String[] values2 = childText.split("Hajg9_9Ajy");
		String get_offer_number = String.valueOf(values2[0]);


		expandedListTextView.setText(get_offer_number);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
							 View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.list_group, null);
		}
		TextView listTitle = (TextView) convertView
				.findViewById(R.id.tv_listtitle);
		listTitle.setTypeface(null, Typeface.BOLD);
		listTitle.setText(headerTitle);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}