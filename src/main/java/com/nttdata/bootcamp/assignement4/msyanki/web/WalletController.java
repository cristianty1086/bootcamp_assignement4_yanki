package com.nttdata.bootcamp.assignement4.msyanki.web;

import com.nttdata.bootcamp.assignement4.msyanki.producer.WalletPaymentProducer;
import com.nttdata.bootcamp.assignement4.msyanki.producer.WalletProducer;
import com.nttdata.bootcamp.assignement4.msyanki.service.WalletPaymentService;
import com.nttdata.bootcamp.assignement4.msyanki.service.WalletService;
import com.nttdata.bootcamp.assignement4.msyanki.web.mapper.WalletMapper;
import com.nttdata.bootcamp.assignement4.msyanki.web.mapper.WalletPaymentMapper;
import com.nttdata.bootcamp.assignement4.msyanki.web.model.WalletModel;
import com.nttdata.bootcamp.assignement4.msyanki.web.model.WalletPaymentModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/wallet")
@RefreshScope
public class WalletController {

	@Value("${spring.application.name}")
	String name;

	@Value("${server.port}")
	String port;

	@Autowired
	private WalletService walletService;

	@Autowired
	private WalletPaymentService walletPaymentService;

	@Autowired
	private WalletProducer walletProducer;

	@Autowired
	private WalletPaymentProducer walletPaymentProducer;

	@Autowired
	private WalletMapper walletMapper;

	@Autowired
	private WalletPaymentMapper walletPaymentMapper;

	@GetMapping
	public Mono<ResponseEntity<Flux<WalletModel>>> getAll() {
		log.info("getAll executed");
		return Mono.just(ResponseEntity.ok()
				.body(walletService.findAll().map(transaction -> walletMapper.entityToModel(transaction))));
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<WalletModel>> getById(@PathVariable String id) {
		log.info("getById executed {}", id);
		return walletService.findById(id).map(transaction -> walletMapper.entityToModel(transaction))
				.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Mono<ResponseEntity<WalletModel>> create(@Valid @RequestBody WalletModel request) {
		log.info("create executed {}", request);
		return walletService.create(walletMapper.modelToEntity(request))
				.map(transaction -> walletMapper.entityToModel(transaction))
				.map(this::sendWallet)
				.flatMap(c -> Mono.just(ResponseEntity
						.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "transaction", c.getId())))
						.body(c)))
				.defaultIfEmpty(ResponseEntity.notFound().build());

	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<WalletModel>> updateById(@PathVariable String id,
			@Valid @RequestBody WalletModel request) {
		log.info("updateById executed {}:{}", id, request);
		return walletService.update(id, walletMapper.modelToEntity(request))
				.map(transaction -> walletMapper.entityToModel(transaction))
				.flatMap(c -> Mono.just(ResponseEntity
						.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "transaction", c.getId())))
						.body(c)))
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id) {
		log.info("deleteById executed {}", id);
		return walletService.delete(id)
				.doOnSuccess(transaction -> walletPaymentService.findByWalletId(id)
						.map(movement -> walletPaymentService.delete(movement.getId())))
				.map(r -> ResponseEntity.ok().<Void>build()).defaultIfEmpty(ResponseEntity.notFound().build());
	}


	private WalletModel sendWallet(WalletModel walletModel) {
		log.debug("sendWallet executed {}", walletModel);
		if (walletModel != null) {
			walletProducer.sendMessage(walletModel);
		}
		return walletModel;
	}


}
