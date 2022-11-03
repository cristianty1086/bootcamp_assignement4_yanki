package com.nttdata.bootcamp.assignement4.msyanki.service;

import com.nttdata.bootcamp.assignement4.msyanki.domain.Wallet;
import com.nttdata.bootcamp.assignement4.msyanki.repository.WalletRepository;
import com.nttdata.bootcamp.assignement4.msyanki.web.mapper.WalletMapper;
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
public class WalletService {

    @Autowired
    private WalletRepository transactionRepository;

    @Autowired
    private WalletMapper transactionMapper;

    public Flux<Wallet> findAll(){
        log.debug("findAll executed");
        return transactionRepository.findAll();
    }

    public Mono<Wallet> findById(String transactionId){
        log.debug("findById executed {}", transactionId);
        return transactionRepository.findById(transactionId);
    }

    public Mono<Wallet> create(Wallet transaction){
        log.debug("create executed {}", transaction);

        return transactionRepository.save(transaction);
    }

    public Mono<Wallet> update(String transactionId,  Wallet transaction){
        log.debug("update executed {}:{}", transactionId, transaction);
        return transactionRepository.findById(transactionId)
                .flatMap(dbTransaction -> {
                    transactionMapper.update(dbTransaction, transaction);
                    return transactionRepository.save(dbTransaction);
                });
    }

    public Mono<Wallet> delete(String transactionId){
        log.debug("delete executed {}", transactionId);
        return transactionRepository.findById(transactionId)
                .flatMap(existingTransaction -> transactionRepository.delete(existingTransaction)
                        .then(Mono.just(existingTransaction)));
    }

}
