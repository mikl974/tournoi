package com.bretibad.tournoibretibad;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.bretibad.tournoibretibad.model.Joueur;
import com.bretibad.tournoibretibad.model.Tournoi;

public class TournoiService {

	public static TournoiService instance = null;
	private String baseUrl = "http://bretibad.pacmik.com/";

	DefaultHttpClient client = new DefaultHttpClient();

	public List<Tournoi> getTournois() {
		List<Tournoi> results = new ArrayList<Tournoi>();
		try {
			HttpGet get = new HttpGet(baseUrl + "rest/listTournoi");
			HttpResponse resp = client.execute(get);
			String content = EntityUtils.toString(resp.getEntity());

			results = Tournoi.fromJsonArray(content);
		} catch (Exception e) {
			Log.e("Error", "Error" + e.getMessage());
		}
		return results;
	}

	public ArrayList<Joueur> getJoueursInscrits(String tournoi) {
		ArrayList<Joueur> results = new ArrayList<Joueur>();
		try {
			HttpGet get = new HttpGet(baseUrl + "rest/listInscrits/" + tournoi);
			HttpResponse resp = client.execute(get);
			String content = EntityUtils.toString(resp.getEntity());

			results = Joueur.fromJsonArray(content);
		} catch (Exception e) {
			Log.e("Error", "Error" + e.getMessage());
		}
		return results;
	}

	public void inscrireJoueurs(String tournoi, Joueur joueur) {
		try {
			HttpPost post = new HttpPost(baseUrl + "rest/inscrireJoueur");

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("licence", joueur
					.getLicence()));
			nameValuePairs.add(new BasicNameValuePair("tournoi", tournoi));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			client.execute(post);
		} catch (Exception e) {
			Log.e("Error", "Error" + e.getMessage());
		}
	}

	public static TournoiService getInstance() {
		if (instance == null) {
			instance = new TournoiService();
		}
		return instance;
	}
}