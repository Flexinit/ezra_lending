package com.ezraloan.automation.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ezraloan.automation.entity.LoanProduct;
import com.ezraloan.automation.service.LoanProductService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {LoanProductController.class})
@ExtendWith(SpringExtension.class)
class LoanProductControllerTest {
    @Autowired
    private LoanProductController loanProductController;

    @MockBean
    private LoanProductService loanProductService;

    /**
     * Method under test: {@link LoanProductController#createNewLoanProduct(LoanProduct)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreateNewLoanProduct() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getName()" because "authentication" is null
        //       at com.ezraloan.automation.Utils.APIUtils.lambda$static$0(APIUtils.java:18)
        //       at com.ezraloan.automation.service.LoanProductService.createNewLoanProduct(LoanProductService.java:20)
        //       at com.ezraloan.automation.controller.LoanProductController.createNewLoanProduct(LoanProductController.java:16)
        //   See https://diff.blue/R013 to resolve this issue.

        LoanProductController loanProductController = new LoanProductController(new LoanProductService());

        LoanProduct loanProduct = new LoanProduct();
        loanProduct.setCode("Code");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        loanProduct.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        loanProduct.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        loanProduct.setDescription("The characteristics of someone or something");
        loanProduct.setId(123L);
        loanProduct.setInterestRate(10.0f);
        loanProduct.setMaximumLoanAgeInmonths("Maximum Loan Age Inmonths");
        loanProduct.setMinimumLoanAmount(10.0f);
        loanProduct.setName("Name");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        loanProduct.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        loanProduct.setUpdatedBy("2020-03-01");
        loanProductController.createNewLoanProduct(loanProduct);
    }

    /**
     * Method under test: {@link LoanProductController#createNewLoanProduct(LoanProduct)}
     */
    @Test
    void testCreateNewLoanProduct2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        LoanProduct loanProduct = new LoanProduct();
        loanProduct.setCode("Code");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        loanProduct.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        loanProduct.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        loanProduct.setDescription("The characteristics of someone or something");
        loanProduct.setId(123L);
        loanProduct.setInterestRate(10.0f);
        loanProduct.setMaximumLoanAgeInmonths("Maximum Loan Age Inmonths");
        loanProduct.setMinimumLoanAmount(10.0f);
        loanProduct.setName("Name");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        loanProduct.setUpdatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        loanProduct.setUpdatedBy("2020-03-01");
        Optional<LoanProduct> ofResult = Optional.of(loanProduct);
        LoanProductService loanProductService = mock(LoanProductService.class);
        when(loanProductService.createNewLoanProduct((LoanProduct) any())).thenReturn(ofResult);
        LoanProductController loanProductController = new LoanProductController(loanProductService);

        LoanProduct loanProduct1 = new LoanProduct();
        loanProduct1.setCode("Code");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        loanProduct1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        loanProduct1.setCreatedBy("Jan 1, 2020 8:00am GMT+0100");
        loanProduct1.setDescription("The characteristics of someone or something");
        loanProduct1.setId(123L);
        loanProduct1.setInterestRate(10.0f);
        loanProduct1.setMaximumLoanAgeInmonths("Maximum Loan Age Inmonths");
        loanProduct1.setMinimumLoanAmount(10.0f);
        loanProduct1.setName("Name");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        loanProduct1.setUpdatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        loanProduct1.setUpdatedBy("2020-03-01");
        Optional<LoanProduct> actualCreateNewLoanProductResult = loanProductController.createNewLoanProduct(loanProduct1);
        assertSame(ofResult, actualCreateNewLoanProductResult);
        assertTrue(actualCreateNewLoanProductResult.isPresent());
        verify(loanProductService).createNewLoanProduct((LoanProduct) any());
    }

    /**
     * Method under test: {@link LoanProductController#getLoanProducts(Long)}
     */
    @Test
    void testGetLoanProducts() throws Exception {
        when(loanProductService.getLoanProducts((Long) any())).thenReturn(Optional.of(new ArrayList<>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/loanProducts");
        MockMvcBuilders.standaloneSetup(loanProductController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

