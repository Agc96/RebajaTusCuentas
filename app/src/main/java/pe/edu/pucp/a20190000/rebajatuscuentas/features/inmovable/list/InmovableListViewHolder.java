package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.list;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.utils.Utilities;

public class InmovableListViewHolder extends RecyclerView.ViewHolder {
    private final static String TAG = "RTC_INM_LIST_HOLDER";
    private ImageView mPhoto;
    private TextView mName;
    private TextView mPrice;
    private TextView mLocation;

    public InmovableListViewHolder(View view) {
        super(view);
        mName = view.findViewById(R.id.inm_item_txt_name);
        mPrice = view.findViewById(R.id.inm_item_txt_price);
        mLocation = view.findViewById(R.id.inm_item_txt_location);
        mPhoto = view.findViewById(R.id.inm_item_img_main);
    }

    public void setDetails(String name, Double price, String location) {
        mName.setText(name);
        mPrice.setText(Utilities.formatMoney(price));
        mLocation.setText(location);
    }
}
