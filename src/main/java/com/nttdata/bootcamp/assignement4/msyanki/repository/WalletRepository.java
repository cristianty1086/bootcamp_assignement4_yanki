package com.nttdata.bootcamp.assignement4.msyanki.repository;

import com.nttdata.bootcamp.assignement4.msyanki.domain.Wallet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface WalletRepository extends ReactiveMongoRepository<Wallet, String> {
}