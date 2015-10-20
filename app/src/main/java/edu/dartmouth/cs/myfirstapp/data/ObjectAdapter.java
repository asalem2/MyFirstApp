package edu.dartmouth.cs.myfirstapp.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.dartmouth.cs.myfirstapp.R;

public class ObjectAdapter extends ArrayAdapter {
    private List<objectsmodel> objectsmodelList;
    private int resource;
    private LayoutInflater inflater;
    public ObjectAdapter(Context context, int resource, List<objectsmodel> objects) {
        super(context, resource, objects);
        objectsmodelList = objects;
        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            convertView = inflater.inflate(resource, null);
        }

        TextView publisher;
        TextView f2f_url;
        TextView title;
        TextView source_url;
        TextView recipe_id;
        TextView image_url;
        TextView social_rank;
        TextView publisher_url;



        publisher = (TextView)convertView.findViewById(R.id.publisher);
        f2f_url = (TextView)convertView.findViewById(R.id.f2f_url);
        title = (TextView)convertView.findViewById(R.id.title);
        source_url = (TextView)convertView.findViewById(R.id.source_url);
        recipe_id = (TextView)convertView.findViewById(R.id.recipe_id);
        image_url = (TextView)convertView.findViewById(R.id.image_url);
        social_rank = (TextView)convertView.findViewById(R.id.social_rank);
        publisher_url = (TextView)convertView.findViewById(R.id.publisher_url);


        publisher.setText("LOcu_id :" + objectsmodelList.get(position).getPublisher());
        f2f_url.setText(objectsmodelList.get(position).getF2f_url());
        title.setText(objectsmodelList.get(position).getTitle());
        source_url.setText(objectsmodelList.get(position).getSource_url());
        recipe_id.setText("Address: \n "+objectsmodelList.get(position).getRecipe_id());
        image_url.setText(objectsmodelList.get(position).getImage_url());
        social_rank.setText(objectsmodelList.get(position).getSocial_rank());
        publisher_url.setText(objectsmodelList.get(position).getPublisher_url());



        return convertView;
    }
}

