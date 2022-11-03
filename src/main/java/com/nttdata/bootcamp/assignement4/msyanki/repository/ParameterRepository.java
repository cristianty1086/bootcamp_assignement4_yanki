package com.nttdata.bootcamp.assignement4.msyanki.repository;

import com.nttdata.bootcamp.assignement4.msyanki.domain.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ParameterRepository {

	private final ReactiveRedisOperations<String, Parameter> reactiveRedisOperations;

	public Flux<Parameter> findAll() {
		return this.reactiveRedisOperations.<String, Parameter>opsForHash().values("parameters");
	}

	public Mono<Parameter> findById(String id) {
		return this.reactiveRedisOperations.<String, Parameter>opsForHash().get("parameters", id);
	}

	public Flux<Parameter> findByParameter(String parameter) {
		return this.findAll().filter(p -> p.getParameter().equals(parameter));
	}

	public Mono<Parameter> save(Parameter parameter) {
		if (parameter.getId() == null) {
			String id = UUID.randomUUID().toString();
			parameter.setId(id);
		}
		return this.reactiveRedisOperations.<String, Parameter>opsForHash()
				.put("parameters", parameter.getId(), parameter).log().map(p -> parameter);
	}

	public Mono<Boolean> deleteAll() {
		return this.reactiveRedisOperations.<String, Parameter>opsForHash().delete("parameters");
	}

	public Mono<Parameter> delete(Parameter parameter) {
		return this.reactiveRedisOperations.<String, Parameter>opsForHash().remove("parameters", parameter.getId())
				.flatMap(p -> Mono.just(parameter));
	}
}