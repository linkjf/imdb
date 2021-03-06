package com.arandasoft.android.imdb.adapter;

import java.util.List;
import com.arandasoft.android.imdb.R;
import com.arandasoft.android.imdb.app.ImdbApp;
import com.arandasoft.android.imdb.bean.Movie;
import com.arandasoft.android.imdb.log.Logger;
import com.arandasoft.android.imdb.util.Utils;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * <p>
 * un adaptador para visualizar de manera customizada los elementos del
 * {@link ListView} que contiene nuestra UI principal
 * </p>
 * 
 * @author Felipe Calderon <felipeskabarragan@gmail.com>
 * */
public class ResultAdapter extends BaseAdapter {

	private List<Movie> result;
	private Context context;

	/**
	 * Constructor de clase
	 * 
	 * @param {@link List} result la lista de coincidencias con la busqueda
	 * @param {@link Context} context el constesto desde la cual es instanciado
	 */
	public ResultAdapter(List<Movie> result, Context context) {
		this.result = result;
		this.context = context;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return result.size();
	}

	@Override
	public Object getItem(int position) {
		return result.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void clear() {
		this.result.clear();
		notifyDataSetChanged();
	}

	/**
	 * clase que nos evita llamadas excesivas al findViewById()
	 * */
	private static class PlaceHolder {
		TextView mTextViewName;
		ImageView mImageCover;

		public static PlaceHolder generate(View convertView) {
			PlaceHolder placeHolder = new PlaceHolder();
			placeHolder.mTextViewName = (TextView) convertView
					.findViewById(R.id.MovieName);
			placeHolder.mImageCover = (ImageView) convertView
					.findViewById(R.id.screenCap);
			return placeHolder;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Movie item = (Movie) getItem(position);

		/* Con el uso de placeHolder reciclamos la vista */
		PlaceHolder placeHolder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_search, null);
			placeHolder = PlaceHolder.generate(convertView);
			convertView.setTag(placeHolder);
		} else {
			placeHolder = (PlaceHolder) convertView.getTag();
		}
		try {
			placeHolder.mTextViewName.setText(Utils.cleanTitle(item.title));
			if (item.poster != null) {
				((ImdbApp) context.getApplicationContext()).getPicasso()
						.load(Utils.cropUrl(item.poster.imdb))
						.into(placeHolder.mImageCover);
			}
		} catch (NullPointerException e) {
			Logger.e("Error al setear la imagen", e);
		}

		return convertView;
	}

}
