package com.example.wordlistapp.notebook;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wordlistapp.R;
import com.example.wordlistapp.include.FontManager;

import java.util.List;

public class NotebookListAdapter extends BaseAdapter {

    private List<NoteInfo> noteList;
    private LayoutInflater layoutInflater;
    private Context context;
    private NotebookViewHolder holder = null;

    public NotebookListAdapter(Context context, List<NoteInfo> noteList) {
        this.noteList = noteList;
        this.context = context;

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position).getTitle();
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(noteList.get(position).getIdStr());
    }

    public void remove(int index){
        noteList.remove(index);
    }

    public void refreshDataSet(){
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.notebook_item,null);
            holder = new NotebookViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (NotebookViewHolder)convertView.getTag();
        }
        holder.itemNoteTitle.setText(noteList.get(position).getTitle());
        holder.itemNoteDate.setText(noteList.get(position).getDate());
        holder.itemNoteTitle.setTypeface(FontManager.getTypeface(FontManager.PRODUCT_SANS_R));
        holder.itemNoteDate.setTypeface(FontManager.getTypeface(FontManager.PRODUCT_SANS_L));
        return convertView;
    }

    public static class NotebookViewHolder {
        public TextView itemNoteTitle;
        public TextView itemNoteDate;

        View itemView;

        public NotebookViewHolder(View itemView) {
            if (itemView == null){
                throw new IllegalArgumentException("item View can not be null!");
            }
            this.itemView = itemView;
            itemNoteTitle = itemView.findViewById(R.id.item_note_title);
            itemNoteDate = itemView.findViewById(R.id.item_note_date);
        }
    }

}
