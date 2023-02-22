package com.card.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FormAdapter extends ArrayAdapter<Form> {
    private List<Form> formList;
    private Context context;

    public FormAdapter(List<Form> formList, Context context) {
        super(context, R.layout.form_list_item, formList);
        this.formList = formList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listViewItem = inflater.inflate(R.layout.form_list_item, null, true);

        TextView serialNoTextView = listViewItem.findViewById(R.id.serialNoTextView);
        TextView nameTextView = listViewItem.findViewById(R.id.nameTextView);
        TextView fatherNameTextView = listViewItem.findViewById(R.id.fatherNameTextView);
        TextView addressTextView = listViewItem.findViewById(R.id.addressTextView);
        TextView WhatsAppTextView = listViewItem.findViewById(R.id.WhatsAppTextView);
        TextView CNICNumberTextView = listViewItem.findViewById(R.id.CNICNumberTextView);
        TextView dateTextView = listViewItem.findViewById(R.id.dateTextView);

        Form form = formList.get(position);
        serialNoTextView.setText(form.getSerialNo());
        nameTextView.setText(form.getName());
        fatherNameTextView.setText(form.getFatherName());
        addressTextView.setText(form.getAddress());
        dateTextView.setText((CharSequence) form.getDate());
        WhatsAppTextView.setText(form.getWhatsapp());
        CNICNumberTextView.setText(form.getCnic());

        return listViewItem;
    }
}
