package dev.boog.moneyloverdatamanager.service;

import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseWalletDto;
import dev.boog.moneyloverdatamanager.entities.Wallet;
import dev.boog.moneyloverdatamanager.mappers.WalletMapper;
import dev.boog.moneyloverdatamanager.repositories.BaseRepository;
import dev.boog.moneyloverdatamanager.repositories.WalletRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class WalletService implements Service<RequestWalletDto, ResponseWalletDto, Long> {

    private WalletRepository walletRepository;

    @Override
    public void setRepository(BaseRepository<?, Long> repository) {
        this.walletRepository = (WalletRepository) repository;
    }

    @Override
    public ResponseEntity<String> create(String userId, RequestWalletDto req) {
        try {
            Wallet entity = WalletMapper.INSTANCE.toEntity(req);
            walletRepository.save(entity);
            return new ResponseEntity<>("Wallet created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<ResponseWalletDto>> get(String userId, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<ResponseWalletDto>> getAll(String userId) {
        try {
            List<Wallet> walletList = walletRepository.getAllByUserId(Long.parseLong(userId));
            List<ResponseWalletDto> responseDtoList = walletRepository
                    .getAllByUserId(Long.parseLong(userId))
                    .stream()
                    .map(WalletMapper.INSTANCE::toResponseDto)
                    .toList();

            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
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
