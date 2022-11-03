package com.nttdata.bootcamp.assignement4.msyanki.web;

import com.nttdata.bootcamp.assignement4.msyanki.service.ParameterService;
import com.nttdata.bootcamp.assignement4.msyanki.web.mapper.ParameterMapper;
import com.nttdata.bootcamp.assignement4.msyanki.web.model.ParameterModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/parameter")
public class ParameterController {

	@Value("${spring.application.name}")
	String name;
	
	@Value("${server.port}")
	String port;

	  
	@Autowired
    private ParameterService parameterService;
	
	@Autowired
    private ParameterMapper parameterMapper;


    @GetMapping
    public Mono<ResponseEntity<Flux<ParameterModel>>>  getAll(){
    	log.info("getAll executed");
  
        return Mono.just(ResponseEntity.ok()
        					.body(parameterService.findAll()
                        	.map(parameter -> parameterMapper.entityToModel(parameter))));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ParameterModel>> getById(@PathVariable String id){
    	log.info("getById executed {}", id);
    	return parameterService.findById(id)
        	   .map(parameter -> parameterMapper.entityToModel(parameter))
        	   .map(ResponseEntity::ok)
               .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/name/{name}")
    public Mono<ResponseEntity<Flux<ParameterModel>>> getByName(@PathVariable String name){
    	log.info("getByName executed {}", name);
    	return Mono.just(ResponseEntity.ok()
        					.body(parameterService.findByName(name)
                        	.map(parameter -> parameterMapper.entityToModel(parameter))));       
    }
    
    @PostMapping    
    public Mono<ResponseEntity<ParameterModel>> create(@Valid @RequestBody ParameterModel request){
      	log.info("create executed {}", request);
      	return parameterService.create(parameterMapper.modelToEntity(request))
        	   .map(parameter -> parameterMapper.entityToModel(parameter))
        	   .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "Parameter", c.getId())))
                        .body(c)))
        	   .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ParameterModel>> updateById(@PathVariable String id, @Valid @RequestBody ParameterModel request){
       	log.info("updateById executed {}:{}", id, request);
    	return parameterService.update(id, parameterMapper.modelToEntity(request))
        	    .map(parameter -> parameterMapper.entityToModel(parameter))
        	    .flatMap(c -> Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "Parameter", c.getId())))
                        .body(c)))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
    	log.info("deleteById executed {}", id);
        return parameterService.delete(id)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}