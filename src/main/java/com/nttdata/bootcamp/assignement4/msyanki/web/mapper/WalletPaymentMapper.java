package com.nttdata.bootcamp.assignement4.msyanki.web.mapper;

import com.nttdata.bootcamp.assignement4.msyanki.domain.Wallet;
import com.nttdata.bootcamp.assignement4.msyanki.domain.WalletPayment;
import com.nttdata.bootcamp.assignement4.msyanki.web.model.WalletModel;
import com.nttdata.bootcamp.assignement4.msyanki.web.model.WalletPaymentModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import reactor.core.publisher.Mono;


@Mapper(componentModel = "spring")
public interface WalletPaymentMapper {

	 WalletPayment modelToEntity (WalletPaymentModel model);

	 WalletPaymentModel entityToModel (WalletPayment event);
	 
	 @Mapping(target = "id", ignore = true)
	 void update(@MappingTarget WalletPayment entity, WalletPayment updateEntity);

}
