package kr.ac.ajou.media.project_team_m;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter{
    private ArrayList<ListItem> listdata;
    private LayoutInflater layoutInflater;


    public ListAdapter(Context context, ArrayList<ListItem> listdata) {
        this.listdata = listdata;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listdata.size();
    }

    @Override
    public Object getItem(int position) {
        return listdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listrowlayout, null);
            holder = new ViewHolder();
            holder.viewTitle = (TextView) convertView.findViewById(R.id.title);
            holder.viewContent = (TextView) convertView.findViewById(R.id.content);
            holder.viewKeys = (TextView) convertView.findViewById(R.id.keywords);
            holder.viewDate = (TextView) convertView.findViewById(R.id.date);
            holder.viewWriter = (TextView) convertView.findViewById(R.id.writername);
            holder.viewLike = (TextView) convertView.findViewById(R.id.liketext);
            holder.viewReply = (TextView) convertView.findViewById(R.id.replytext);
            holder.buttonLike = (Button) convertView.findViewById(R.id.likebutton);
            holder.buttonReply = (Button) convertView.findViewById(R.id.replybutton);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.viewTitle.setText(listdata.get(position).getTitle());
        holder.viewContent.setText(listdata.get(position).getContent());
        //holder.viewKeys.setText((CharSequence) listdata.get(position).getKeys());
        holder.viewDate.setText(listdata.get(position).getDate());
        holder.viewWriter.setText("Written by "+listdata.get(position).getWritername());
        holder.viewLike.setText(""+listdata.get(position).getLike());
        holder.viewReply.setText("" + listdata.get(position).getReply());
        return convertView;
    }

    static class ViewHolder {
        TextView viewTitle, viewContent, viewKeys, viewDate, viewWriter, viewLike, viewReply;
        Button buttonLike, buttonReply;
    }
}
