package com.nttdata.bootcamp.assignement4.msyanki.service;

import com.nttdata.bootcamp.assignement4.msyanki.domain.WalletPayment;
import com.nttdata.bootcamp.assignement4.msyanki.repository.WalletPaymentRepository;
import com.nttdata.bootcamp.assignement4.msyanki.web.mapper.WalletPaymentMapper;
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
public class WalletPaymentService {

    @Autowired
    private WalletPaymentRepository walletPaymentRepository;

    @Autowired
    private WalletPaymentMapper walletPaymentMapper;

    public Flux<WalletPayment> findAll(){
        log.debug("findAll executed");
        return walletPaymentRepository.findAll();
    }

    public Mono<WalletPayment> findById(String transactionId){
        log.debug("findById executed {}", transactionId);
        return walletPaymentRepository.findById(transactionId);
    }

    public Mono<WalletPayment> create(WalletPayment transaction){
        log.debug("create executed {}", transaction);

        return walletPaymentRepository.save(transaction);
    }

    public Mono<WalletPayment> update(String transactionId,  WalletPayment transaction){
        log.debug("update executed {}:{}", transactionId, transaction);
        return walletPaymentRepository.findById(transactionId)
                .flatMap(dbTransaction -> {
                    walletPaymentMapper.update(dbTransaction, transaction);
                    return walletPaymentRepository.save(dbTransaction);
                });
    }

    public Mono<WalletPayment> delete(String transactionId){
        log.debug("delete executed {}", transactionId);
        return walletPaymentRepository.findById(transactionId)
                .flatMap(existingTransaction -> walletPaymentRepository.delete(existingTransaction)
                        .then(Mono.just(existingTransaction)));
    }

    public Mono<WalletPayment> findByWalletId(String walletId){
        log.debug("findByWalletId executed {}", walletId);
        return walletPaymentRepository.findByWalletId(walletId).last();
    }
}
