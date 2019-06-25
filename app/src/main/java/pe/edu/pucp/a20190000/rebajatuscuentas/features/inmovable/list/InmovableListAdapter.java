package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pe.edu.pucp.a20190000.rebajatuscuentas.R;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.InmovableMainData;

public class InmovableListAdapter extends RecyclerView.Adapter<InmovableListViewHolder> {
    private static final String TAG = "RTC_INM_LIST_ADAPTER";
    private List<InmovableMainData> inmovables;

    public InmovableListAdapter(List<InmovableMainData> inmovables) {
        this.inmovables = inmovables;
    }

    @NonNull
    @Override
    public InmovableListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_inmovable_list_item,
                parent, false);
        return new InmovableListViewHolder(view);
    }

    /**
     * Coloca los datos del inmueble en el item que lo representa visualmente en el layout.
     * @param viewHolder Instancia del ViewHolder.
     * @param position El índice del item que representa al inmueble.
     */
    @Override
    public void onBindViewHolder(@NonNull InmovableListViewHolder viewHolder, int position) {
        InmovableMainData inmovable = inmovables.get(position);
        // Colocar los detalles principales del inmueble en el item
        viewHolder.setDetails(inmovable.getName(), inmovable.getPrice(), inmovable.getLocation());
        // TODO: Colocar la foto, si es que hubiera
    }

    @Override
    public int getItemCount() {
        return inmovables.size();
    }
}
