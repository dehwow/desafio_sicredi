package com.sicredi.desafiosicredi.adapter.out.persistence.mapper;

import com.sicredi.desafiosicredi.adapter.out.persistence.entity.SessaoVotacaoEntity;
import com.sicredi.desafiosicredi.domain.model.SessaoVotacao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SessaoVotacaoMapper {
    SessaoVotacaoEntity toEntity(SessaoVotacao sessaoVotacao);

    default SessaoVotacao toDomain(SessaoVotacaoEntity entity) {
        if (entity == null) {
            return null;
        }
        return new SessaoVotacao(
                entity.getId(),
                entity.getPautaId(),
                entity.getDataHoraInicio(),
                entity.getDataHoraFim(),
                entity.getStatus()
        );
    }
}
