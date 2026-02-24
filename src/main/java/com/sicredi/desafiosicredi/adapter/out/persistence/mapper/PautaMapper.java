package com.sicredi.desafiosicredi.adapter.out.persistence.mapper;

import com.sicredi.desafiosicredi.adapter.out.persistence.entity.PautaEntity;
import com.sicredi.desafiosicredi.domain.model.Pauta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PautaMapper {
    PautaEntity toEntity(Pauta pauta);

    default Pauta toDomain(PautaEntity pautaEntity) {
        if (pautaEntity == null) {
            return null;
        }
        return new Pauta(pautaEntity.getId(), pautaEntity.getTitulo());
    }
}
