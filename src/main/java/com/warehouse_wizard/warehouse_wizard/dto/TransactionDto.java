package com.warehouse_wizard.warehouse_wizard.dto;

import com.warehouse_wizard.warehouse_wizard.utils.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private ProductDto product;
    private int quantity;
    private TransactionType transactionType;
    private Date transactionDate;
}
