package com.example.juan.proyecto;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by juan on 8/11/17.
 */

public class ListViewAdapter extends ArrayAdapter <String>{

    int groupid;
    String[] item_list;
    ArrayList<String> desc;
    Context context;

    //public ListViewAdapter(Context context, int vg, int id, String[] item_list){
    //    super(context, vg, id, item_list);
    //    this.context = context;
    //    groupid = vg;
    //    this.item_list = item_list;
    //}

    public ListViewAdapter(ActivityLista context, int list_item, int txtItemOptions, ArrayList<String> listaDatos) {
        super(context, list_item, txtItemOptions, listaDatos);
        this.context = context;
        groupid = list_item;
        this.desc = listaDatos;
        Log.e("-contexto: "+context,"  -groupid: "+list_item);
        Log.e("-desc:  "+listaDatos.toArray().toString(), "---------OK");
    }
/*
    //Contenedor de la vista de un item de la lista
    static class ViewHolder {
        public TextView txtView;
        public Button btnEditar;
        public Button btnBorrar;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;

        // Infla el item list del fichero list_item.xml convertView es null
        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtView = (TextView) rowView.findViewById(R.id.txtItemOptions);
            viewHolder.btnEditar = (Button) rowView.findViewById(R.id.btnEditar);
            viewHolder.btnBorrar = (Button) rowView.findViewById(R.id.btnBorrar);
            rowView.setTag(viewHolder);
        }

        // Pone texto a cada TextView de un item de la lista
        ViewHolder holder = (ViewHolder) rowView.getTag();
        //holder.txtView.setText(item_list[position]);
        holder.txtView.setText(desc.get(position));
        holder.btnEditar.setText("editar");
        holder.btnBorrar.setText("borrar");
        return rowView;
        //return super.getView(position, convertView, parent);
    }*/
}
