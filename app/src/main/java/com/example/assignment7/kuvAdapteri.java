package com.example.assignment7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class kuvAdapteri extends ArrayAdapter<urlOwnerLicense> {
    private Context mContext;
    int mReSource;

    public kuvAdapteri(@NonNull Context context, int resource, List<urlOwnerLicense> objects) {
        super(context, resource, objects);
        mContext = context;
        mReSource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String file = getItem(position).getFile();
        String owner = getItem(position).getOwner();
        String license = getItem(position).getLicense();

        urlOwnerLicense kuva = new urlOwnerLicense(file, license, owner);

        LayoutInflater inflater =LayoutInflater.from(mContext);
        convertView = inflater.inflate(mReSource, parent, false);

        TextView tvowner = (TextView) convertView.findViewById(R.id.ownerView);
        TextView tvlicense = (TextView) convertView.findViewById(R.id.licenseView);
        ImageView ivKuva = (ImageView) convertView.findViewById(R.id.kuvaview);
        //Picasso.with(mContext).load(file).into(ivKuva);

        return convertView;
    }
}
