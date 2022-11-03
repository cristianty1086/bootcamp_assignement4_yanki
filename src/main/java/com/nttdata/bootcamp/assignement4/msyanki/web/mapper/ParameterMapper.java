package com.nttdata.bootcamp.assignement4.msyanki.web.mapper;

import com.nttdata.bootcamp.assignement4.msyanki.domain.Parameter;
import com.nttdata.bootcamp.assignement4.msyanki.web.model.ParameterModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface ParameterMapper {

	 Parameter modelToEntity (ParameterModel model);
	 
	 ParameterModel entityToModel (Parameter event);
	 
	 @Mapping(target = "id", ignore = true)
	 void update(@MappingTarget Parameter entity, Parameter updateEntity);
}
