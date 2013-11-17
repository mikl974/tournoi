package com.bretibad.tournoibretibad;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bretibad.tournoibretibad.R;
import com.bretibad.tournoibretibad.model.Joueur;
import com.bretibad.tournoibretibad.model.Tournoi;

public class TournoisAdapter extends ArrayAdapter<Tournoi> {

	List<Tournoi> dtos;
	LayoutInflater inflater;

	public TournoisAdapter(Context context, int resId, List<Tournoi> dtos) {
		super(context, resId, dtos);
		inflater = LayoutInflater.from(context);
		this.dtos = dtos;
	}

	@Override
	public int getCount() {
		return dtos.size();
	}

	@Override
	public Tournoi getItem(int position) {
		return dtos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.tournoi_item, null);

			holder.nom = (TextView) convertView.findViewById(R.id.tournoi_nom);
			holder.tarif = (TextView) convertView
					.findViewById(R.id.tournoi_tarif);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final String tournoi = dtos.get(position).getNom();
		final String tarif = dtos.get(position).getTarif();
		holder.nom.setText(Html.fromHtml(tournoi));
		holder.tarif.setText(Html.fromHtml(tarif));

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ArrayList<Joueur> joueursInscrits = TournoiService.getInstance(
						v.getContext()).getJoueursInscrits(tournoi);
				Intent i = new Intent(v.getContext(),
						JoueursInscritsActivity.class);
				i.putParcelableArrayListExtra("joueurs", joueursInscrits);
				i.putExtra("tournoi", tournoi);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				v.getContext().startActivity(i);
			}
		});

		// int listItemBackgroundPosition = position % colors.length;
		// convertView.setBackgroundResource(colors[listItemBackgroundPosition]);

		return convertView;
	}

	private class ViewHolder {
		TextView nom;
		TextView tarif;
	}
}
