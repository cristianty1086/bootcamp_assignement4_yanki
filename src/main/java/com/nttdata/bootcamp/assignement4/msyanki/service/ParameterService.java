package com.nttdata.bootcamp.assignement4.msyanki.service;

import com.nttdata.bootcamp.assignement4.msyanki.domain.Parameter;
import com.nttdata.bootcamp.assignement4.msyanki.repository.ParameterRepository;
import com.nttdata.bootcamp.assignement4.msyanki.web.mapper.ParameterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ParameterService {

    
    @Autowired
    private ParameterRepository parameterRepository;
    
	@Autowired
    private ParameterMapper parameterMapper;

    
	public Flux<Parameter> findAll(){
    	log.debug("findAll executed");
        return parameterRepository.findAll();
    }

    
	public Mono<Parameter> findById(String parameterId){
    	log.debug("findById executed {}", parameterId);
        return parameterRepository.findById(parameterId);
    }

	public Flux<Parameter> findByName(String parameter){
    	log.debug("findByName executed {}", parameter);
        return parameterRepository.findByParameter(parameter);
    }

    
	public Mono<Parameter> create(Parameter parameter){
    	log.debug("create executed {}", parameter);
    	return parameterRepository.save(parameter);    	
    }

    
	public Mono<Parameter> update(String parameterId,  Parameter parameter){
    	log.debug("update executed {}:{}", parameterId, parameter);
        return parameterRepository.findById(parameterId)
                .flatMap(dbparameter -> {
                	parameterMapper.update(dbparameter, parameter);
                    return parameterRepository.save(dbparameter);
                });
    }

    
	public Mono<Parameter> delete(String parameterId){
    	log.debug("delete executed {}", parameterId);
        return parameterRepository.findById(parameterId)
                .flatMap(existingparameter -> parameterRepository.delete(existingparameter)
                .then(Mono.just(existingparameter)));
    }
  
}