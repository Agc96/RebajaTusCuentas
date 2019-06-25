package pe.edu.pucp.a20190000.rebajatuscuentas.features.inmovable.list;

import java.util.List;

import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.InmovableMainData;
import pe.edu.pucp.a20190000.rebajatuscuentas.features.base.IView;

public interface IInmovableListView extends IView {
    void initializeList(List<InmovableMainData> inmovables);
}
