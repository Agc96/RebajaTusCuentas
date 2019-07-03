package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.list;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Constants;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Image;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Utilities;

public class InmovableListViewHolder extends RecyclerView.ViewHolder {
    private final static String TAG = "RTC_INM_LIST_HOLDER";
    private ImageView mPhoto;
    private TextView mName;
    private TextView mPrice;
    private TextView mDirection;

    public InmovableListViewHolder(View view) {
        super(view);
        mName = view.findViewById(R.id.inm_item_txt_name);
        mPrice = view.findViewById(R.id.inm_item_txt_price);
        mDirection = view.findViewById(R.id.inm_item_txt_direction);
        mPhoto = view.findViewById(R.id.inm_item_img_main);
    }

    public void setDetails(int id, String name, Double price, String direction) {
        // Colocar datos principales
        mName.setText(name);
        mPrice.setText(Utilities.formatMoney(price));
        mDirection.setText(direction);
        // Colocar foto del inmueble, si es que existe
        String filename = String.format(Locale.US, Constants.IMAGE_INMOVABLE_FORMAT, id);
        Bitmap image = Image.loadFromExternalStorage(filename);
        if (image != null) {
            mPhoto.setImageBitmap(image);
        }
    }
}
