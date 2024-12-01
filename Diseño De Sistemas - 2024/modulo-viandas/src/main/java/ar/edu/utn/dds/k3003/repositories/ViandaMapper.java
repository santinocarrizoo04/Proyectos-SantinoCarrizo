package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.facades.dtos.ViandaDTO;
import ar.edu.utn.dds.k3003.model.Vianda;

public class ViandaMapper {

    public ViandaDTO map(Vianda vianda) {
        ViandaDTO viandaDTO = new ViandaDTO(vianda.getCodigoQR(),
                vianda.getFechaElaboracion(),
                vianda.getEstado(),
                vianda.getColaboradorId(),
                vianda.getHeladeraId());
        viandaDTO.setId(vianda.getId());
        return viandaDTO;
    }
}
