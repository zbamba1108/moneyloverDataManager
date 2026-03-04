package dev.boog.moneyloverdatamanager.services.impl;

import dev.boog.moneyloverdatamanager.dtos.request.RequestWalletDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseDto;
import dev.boog.moneyloverdatamanager.dtos.response.ResponseWalletDto;
import dev.boog.moneyloverdatamanager.entities.Wallet;
import dev.boog.moneyloverdatamanager.utils.mappers.PageMapper;
import dev.boog.moneyloverdatamanager.utils.mappers.WalletMapper;
import dev.boog.moneyloverdatamanager.repositories.WalletRepository;
import dev.boog.moneyloverdatamanager.services.WalletService;
import dev.boog.moneyloverdatamanager.utils.*;
import java.util.logging.*;

import dev.boog.moneyloverdatamanager.utils.mappers.models.QueryResult;
import org.springframework.dao.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class WalletServiceImpl implements WalletService<RequestWalletDto, ResponseWalletDto> {

    private static final Logger LOGGER = Logger.getLogger(WalletServiceImpl.class.getName());

    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
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

    public ResponseEntity<ResponseDto<ResponseWalletDto>> get(String userId, RequestWalletDto req) {
        try {
            final QueryResult<Wallet> queryResult = walletRepository
                    .search(ServiceHelper
                            .getQueryRequest(Wallet.class, req, userId, false, false));

            final ResponseDto<ResponseWalletDto> responseDto = ResponseDto
                    .<ResponseWalletDto>builder()
                    .data(queryResult
                            .getResults()
                            .stream()
                            .map(WalletMapper.INSTANCE::toResponseDto)
                            .toList())
                    .page(PageMapper.INSTANCE.toDto(queryResult.getPage()))
                    .build();

            return new ResponseEntity<>(responseDto, HttpStatus.OK);
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
        try {
            ResponseWalletDto responseDto = WalletMapper.INSTANCE
                    .toResponseDto(walletRepository
                            .save(WalletMapper.INSTANCE
                                    .toEntity(req)));
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> delete(String userId, RequestWalletDto req) {
        try {
            walletRepository.deleteByIds(
                    Wallet.class,
                    Long.parseLong(userId),
                    req.getIds()
                            .stream()
                            .map(Long::parseLong)
                            .toList());
            return new ResponseEntity<>("Wallet(s) deleted successfully", HttpStatus.OK);
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseDto<ResponseWalletDto>> details(String userId, RequestWalletDto req) {
        try {
            final QueryResult<Wallet> queryResult = walletRepository
                    .search(ServiceHelper
                            .getQueryRequest(Wallet.class, req, userId, true, true));

            final ResponseDto<ResponseWalletDto> responseDto = ResponseDto
                    .<ResponseWalletDto>builder()
                    .data(queryResult
                            .getResults()
                            .stream()
                            .map(WalletMapper.INSTANCE::toResponseDtoDetails)
                            .toList())
                    .page(PageMapper.INSTANCE.toDto(queryResult.getPage()))
                    .build();

            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (InvalidDataAccessApiUsageException e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
