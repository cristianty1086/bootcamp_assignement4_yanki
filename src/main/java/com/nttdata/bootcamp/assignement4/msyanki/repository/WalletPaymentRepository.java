package com.nttdata.bootcamp.assignement4.msyanki.repository;

import com.nttdata.bootcamp.assignement4.msyanki.domain.WalletPayment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface WalletPaymentRepository extends ReactiveMongoRepository<WalletPayment, String> {
    Flux<WalletPayment> findByWalletId(String transactionId);
}