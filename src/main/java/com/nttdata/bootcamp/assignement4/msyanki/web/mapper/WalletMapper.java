package com.nttdata.bootcamp.assignement4.msyanki.web.mapper;

import com.nttdata.bootcamp.assignement4.msyanki.domain.Wallet;
import com.nttdata.bootcamp.assignement4.msyanki.web.model.WalletModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface WalletMapper {

	 Wallet modelToEntity (WalletModel model);
	 
	 WalletModel entityToModel (Wallet event);
	 
	 @Mapping(target = "id", ignore = true)
	 void update(@MappingTarget Wallet entity, Wallet updateEntity);
}
