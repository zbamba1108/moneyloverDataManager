package dev.boog.moneyloverdatamanager.services;

import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseWalletDto;
import dev.boog.moneyloverdatamanager.entities.Wallet;
import dev.boog.moneyloverdatamanager.mappers.WalletMapper;
import dev.boog.moneyloverdatamanager.repositories.BaseRepository;
import dev.boog.moneyloverdatamanager.repositories.WalletRepository;
import dev.boog.moneyloverdatamanager.utils.*;
import java.util.logging.*;
import org.springframework.dao.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class WalletService implements Service<RequestWalletDto, ResponseWalletDto, Long> {

    private static final Logger LOGGER = Logger.getLogger(WalletService.class.getName());

    private WalletRepository walletRepository;

    @Override
    public void setRepository(BaseRepository<?, Long> repository) {
        this.walletRepository = (WalletRepository) repository;
    }

    @Override
    public ResponseEntity<String> create(String userId, RequestWalletDto req) {
        try {
            Wallet entity = WalletMapper.INSTANCE
                    .toEntity(req)
                    .userId(userId);
            walletRepository.save(entity);
            return new ResponseEntity<>("Wallet created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ResponseWalletDto>> get(String userId, RequestWalletDto req) {
        try {
            List<ResponseWalletDto> responseDtoList = walletRepository
                    .searchByUserIdAndIds(
                            Wallet.class,
                            userId,
                            req != null ? req.getIds() : null,
                            ServiceHelper.filter(req)
                    )
                    .stream()
                    .map(WalletMapper.INSTANCE::toResponseDto)
                    .toList();
            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseWalletDto> update(String userId, RequestWalletDto req) {
        return null;
    }

    @Override
    public ResponseEntity<String> delete(String userId, RequestWalletDto dto) {
        return null;
    }
}
