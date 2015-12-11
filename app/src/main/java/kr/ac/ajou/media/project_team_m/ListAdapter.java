package kr.ac.ajou.media.project_team_m;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter{
    private Context context;
    public ArrayList<ListItem> listdata = new ArrayList<ListItem>();
    private String email, nick;

    public ListAdapter(Context context) {
        super();
        this.context = context;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;

        ListActivity listActivity = new ListActivity();
        email = listActivity.email;
        nick = listActivity.nick;

        if (convertView == null) {
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.form_listlayout, null);

            holder.ttitle = (TextView) convertView.findViewById(R.id.title);
            holder.tcontent = (TextView) convertView.findViewById(R.id.content);
            holder.twriter = (TextView) convertView.findViewById(R.id.writername);
            holder.tdate = (TextView) convertView.findViewById(R.id.date);
            holder.treply = (TextView) convertView.findViewById(R.id.replytext);
            holder.tlike = (TextView) convertView.findViewById(R.id.liketext);
            holder.tkeys = (TextView) convertView.findViewById(R.id.keywords);

            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();

        ListItem listItem = listdata.get(position);

        holder.ttitle.setText(listItem.getTitle());
        holder.tcontent.setText(listItem.getContent());
        holder.twriter.setText(listItem.getWriter());
        holder.tdate.setText(listItem.getDate());
        holder.treply.setText(listItem.getReply());
        holder.tlike.setText(listItem.getLike());
        holder.tkeys.setText(listItem.getKeys());

        ImageButton likebutton = (ImageButton) convertView.findViewById(R.id.likebutton);
        ImageButton replybutton = (ImageButton) convertView.findViewById(R.id.replybutton);
        TextView title = (TextView) convertView.findViewById(R.id.title);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zoom = new Intent(context, ArticleDetailActivity.class);
                int temp = listdata.get(position).getNo();
                zoom.putExtra("no", Integer.toString(temp));
                zoom.putExtra("email", email);
                zoom.putExtra("nick", nick);
                parent.getContext().startActivity(zoom);
            }
        });

        likebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* like 수 증가시키기 */
            }
        });
        replybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zoom = new Intent(context, ArticleCommentActivity.class);
                zoom.putExtra("no", listdata.get(position).getNo());
                parent.getContext().startActivity(zoom);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        public TextView ttitle, tcontent, twriter, tdate, treply, tlike, tkeys;
    }

    public void addItem(int no, String title, String cont, String writer, String date, String reply, String like, String keys) {
        ListItem add = new ListItem();
        add.setNo(no);
        add.setTitle(title);
        add.setContent(cont);
        add.setWriter(writer);
        add.setDate(date);
        add.setReply(reply);
        add.setLike(like);
        add.setKeys(keys);
        listdata.add(add);
    }
}
