package com.sicredi.desafiosicredi.adapter.out.persistence.mapper;

import com.sicredi.desafiosicredi.adapter.out.persistence.entity.VotoEntity;
import com.sicredi.desafiosicredi.domain.model.Voto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VotoMapper {
    VotoEntity toEntity(Voto voto);
    Voto toDomain(VotoEntity votoEntity);
}
